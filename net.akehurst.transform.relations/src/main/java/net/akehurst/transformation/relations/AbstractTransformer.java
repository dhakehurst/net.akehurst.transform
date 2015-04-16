package net.akehurst.transformation.relations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public abstract class AbstractTransformer implements Transformer {

	public AbstractTransformer() {

	}

	@Override
	public abstract List<Class<? extends Relation>> getTopRelationType();

	Relation getRelation(Class<? extends Relation> relationType, Map<DomainModelItentifier, Object> domainArgs) throws RelationNotApplicableException {
		try {
			Constructor<? extends Relation> c = relationType.getConstructor(new Class<?>[] { Transformer.class });
			// Constructor<? extends Relation> c =
			// relationType.getConstructor(types);
			Relation r = c.newInstance(new Object[] { this });
			for (Map.Entry<DomainModelItentifier, Object> v : domainArgs.entrySet()) {
				DomainModelItentifier domainId = v.getKey();
				Object arg = v.getValue();
				if (null == arg || r.getDomainType(domainId).isAssignableFrom(arg.getClass())) {
					r.setDomainVariable(v.getKey(), v.getValue());
				} else {
					throw new RelationNotApplicableException("Object of type " + v.getClass().getName() + " not applicable to domain " + domainId
							+ " in Relation " + relationType.getName());
				}
			}
			return r;
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RelationNotValidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	List<Relation> getRelations(Map<DomainModelItentifier, Object> domainArgs) {
		List<Relation> relations = new Vector<Relation>();
		for (Class<? extends Relation> relationType : getTopRelationType()) {
			try {
				relations.add((Relation) this.getRelation(relationType, domainArgs));
			} catch (RelationNotApplicableException e) {
				// relation not added to list
			}
		}
		return relations;
	}

	/*
	 * public <T1, T2> boolean check2(Iterable<? extends T1> domain1, Iterable<? extends T2> domain2) { boolean result =
	 * true;
	 * 
	 * List<Class<? extends Relation<?,?>>> relationTypes = this.getTopRelationType();
	 * 
	 * for( Class<? extends Relation<?,?>> relationType: relationTypes ) {
	 * 
	 * //for each top relation there must be a match for each object in target for(T2 d2Object: domain2) { try { T1
	 * d1Obj = this.findMatch2((Class<? extends Relation<T1,T2>>)relationType, domain1, d2Object); if (null==d1Obj) {
	 * //check failed so fail fast return false; } } catch (RelationNotApplicableException ex) { //do nothing } }
	 * 
	 * } return result; }
	 */

	/* do a cross product of all sourceDomain objects */
	Iterable<Map<DomainModelItentifier, Object>> createAlternatives(Map<DomainModelItentifier, Iterable<?>> domains) {
		Set<DomainModelItentifier> domainIds = domains.keySet();
		// sourceDomains.remove(targetDomain);

		List<Map<DomainModelItentifier, Object>> product = null;
		List<Map<DomainModelItentifier, Object>> newProduct = null;
		// create product of these lists of tuples
		for (DomainModelItentifier sourceDomain : domainIds) {
			if (null == product || product.isEmpty()) { // could be empty if first domain is empty
				// first time round loop
				product = this.createTupleList(sourceDomain, domains.get(sourceDomain));
			} else {
				newProduct = new ArrayList<Map<DomainModelItentifier, Object>>();
				for (Map<DomainModelItentifier, Object> currentTuple : product) {
					for (Object obj : domains.get(sourceDomain)) {
						HashMap<DomainModelItentifier, Object> newTuple = new HashMap<DomainModelItentifier, Object>(currentTuple);
						newTuple.put(sourceDomain, obj);
						newProduct.add(newTuple);
					}
				}
				if ( !newProduct.isEmpty()) { //may be empty if domains.get(sourceDomain) was empty
					product = newProduct;
				}
			}
		}

		return product;
	}

	List<Map<DomainModelItentifier, Object>> createTupleList(DomainModelItentifier domainId, Iterable<?> domainObjects) {
		ArrayList<Map<DomainModelItentifier, Object>> list = new ArrayList<Map<DomainModelItentifier, Object>>();
		for (Object obj : domainObjects) {
			Map<DomainModelItentifier, Object> tuple = new HashMap<DomainModelItentifier, Object>();
			tuple.put(domainId, obj);
			list.add(tuple);
		}
		return list;
	}

	// public <T1, T2, R extends Relation2<T1, T2>> R findMatch2(DomainModelItentifier targetDomain,
	// Class<? extends R> relationType, Map<DomainModelItentifier, Iterable<?>> domains) throws
	// RelationNotValidException, RelationNotApplicableException {
	// return (R) this.findMatch(targetDomain, relationType, domains);
	// }

	boolean checkMatch(DomainModelItentifier targetDomain, Relation relation) throws RelationNotValidException {
		if (relation.when(targetDomain)) {
			boolean holds = relation.check(targetDomain);
			if (holds) {
				boolean where = relation.checkWhere(targetDomain);
				if (where) {
					return true;
				}
			}
		}
		return false;
	}

	void enforceMatch(DomainModelItentifier targetDomain, Relation relation) throws RelationNotValidException {
		if (relation.when(targetDomain)) {
			relation.enforce(targetDomain);
			relation.enforceWhere(targetDomain);
		}
	}

	Map<DomainModelItentifier, List<Object>> enforceMatches(DomainModelItentifier targetDomain, Class<? extends Relation> relationType,
			Map<DomainModelItentifier, Iterable<?>> domains) throws RelationNotValidException {
		Map<DomainModelItentifier, List<Object>> newDomainObjects = new HashMap<DomainModelItentifier, List<Object>>();

		Iterable<Map<DomainModelItentifier, Object>> options = this.createAlternatives(domains);

		for (Map<DomainModelItentifier, Object> domainArgs : options) {
			try {
				Relation relation = this.getRelation(relationType, domainArgs);
				if (null != relation) {
					this.enforceMatch(targetDomain, relation);
				}

				for (DomainModelItentifier domainId : domains.keySet()) {
					Object newObject = relation.getDomainVariable(domainId);
					if (null == newDomainObjects.get(domainId)) {
						newDomainObjects.put(domainId, new ArrayList<Object>());
					}
					newDomainObjects.get(domainId).add(newObject);
				}

			} catch (RelationNotApplicableException e) {

			}
		}
		return newDomainObjects;
	}

	// ----------------------- Transformer ------------------------------

	public Relation findRelation(DomainModelItentifier targetDomain, Class<? extends Relation> relationType, Map<DomainModelItentifier, Object> domainArgs)
			throws RelationNotValidException, RelationNotApplicableException {

		Relation relation = this.getRelation(relationType, domainArgs);
		if (this.checkMatch(targetDomain, relation)) {
			return relation;
		} else {
			throw new RelationNotApplicableException("check fails for this relation");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.akehurst.transform.core.Transformation#findMatch1(java.lang.Class, java.lang.Object, java.lang.Iterable)
	 */
	public Relation findMatch(DomainModelItentifier targetDomain, Class<? extends Relation> relationType, Map<DomainModelItentifier, Iterable<?>> domains)
			throws RelationNotValidException, RelationNotApplicableException {

		Iterable<Map<DomainModelItentifier, Object>> options = this.createAlternatives(domains);

		for (Map<DomainModelItentifier, Object> domainArgs : options) {
			Relation relation = this.findRelation(targetDomain, relationType, domainArgs);
			if (null != relation) {
				return relation;
			}
		}
		throw new RelationNotApplicableException("Relation " + relationType.getName() + " not applicable to any of the possible objects");
	}

	public boolean check(DomainModelItentifier targetDomain, Map<DomainModelItentifier, Iterable<?>> domains) throws RelationNotValidException {
		boolean result = true;

		List<Class<? extends Relation>> relations = this.getTopRelationType();
		// for each top relation there must be a match for each object in targetDomain
		//Iterable<?> targetObjects = domains.get(targetDomain);
		Iterable<Map<DomainModelItentifier, Object>> options = this.createAlternatives(domains);
		
		for (Class<? extends Relation> relationType : relations) {

//			for (Object targetObject : targetObjects) {
				try {
					for (Map<DomainModelItentifier, Object> domainArgs : options) {
						Relation relation = this.getRelation(relationType, domainArgs);
						if (relation.when(targetDomain)) {
							if (this.checkMatch(targetDomain, relation)) {
								// all OK
							} else {
								//fail fast
								return false;
							}
							
						}
					}
//					Map<DomainModelItentifier, Iterable<?>> domains2 = new HashMap<DomainModelItentifier, Iterable<?>>(domains);
//					domains2.put(targetDomain, Arrays.asList(targetObject));

//					Relation match = this.findMatch(targetDomain, relationType, domains);
//					if (null != match) {
//						// Object d2Obj = match.getDomainVariable(targetDomain);
//						// if (null == d2Obj) {
//						// // check failed so fail fast
//						// return false;
//						// }
//					} else {
//						return false;
//					}

				} catch (RelationNotApplicableException e) {
					// must be an applicable option for each top relation
					return false;
				}

	//		}

		}

		return result;
	}

	public Map<DomainModelItentifier, Iterable<?>> enforce(DomainModelItentifier targetDomain, Map<DomainModelItentifier, Iterable<?>> domains)
			throws RelationNotValidException {
		Map<DomainModelItentifier, Iterable<?>> newDomainObjects = new HashMap<DomainModelItentifier, Iterable<?>>();
		for (DomainModelItentifier domainId : domains.keySet()) {
			newDomainObjects.put(domainId, new ArrayList<Object>());
		}

		List<Class<? extends Relation>> relations = this.getTopRelationType();
		// for each top relation there must be a match for each object in targetDomain
//		Iterable<?> targetObjects = domains.get(targetDomain);

		Iterable<Map<DomainModelItentifier, Object>> options = this.createAlternatives(domains);

		for (Class<? extends Relation> relationType : relations) {

//			// for each top relation there must be a match for each object in target
//			for (Object targetObject : targetObjects) {
//
//				Map<DomainModelItentifier, Iterable<?>> domains2 = new HashMap<DomainModelItentifier, Iterable<?>>(domains);
//				domains2.put(targetDomain, Arrays.asList(targetObject));

				boolean foundRelation = false;
				for (Map<DomainModelItentifier, Object> domainArgs : options) {
					try {
						Relation relation = this.getRelation(relationType, domainArgs);
						if (relation.when(targetDomain)) {
							foundRelation = true;
							if (this.checkMatch(targetDomain, relation)) {
								// all good
							} else {
								this.enforceMatch(targetDomain, relation);
							}
							// record new domain objects
							for (DomainModelItentifier domainId : domains.keySet()) {
								((ArrayList<Object>) newDomainObjects.get(domainId)).add(relation.getDomainVariable(domainId));
							}
						} else {
							// targetObject not matched by this relation
							// look up qvt semantics to figure out what we should do!
						}
					} catch (RelationNotApplicableException e) {

					}
				}
				if (!foundRelation) {

				}
			}

	//	}
		// }

		return newDomainObjects;
	}

}
