package net.akehurst.transformation.relations;

import java.util.List;
import java.util.Map;

public interface Transformer {
	
	List<Class<? extends Relation>> getTopRelationType();
	
	
	boolean check(DomainModelItentifier targetDomain, Map<DomainModelItentifier,Iterable<?>> domains) throws RelationNotValidException;

	/**
	 * Given the existing objects in the defined domains, 'enforce' the targetDomain, by creating any necessary 
	 * objects in the targetDomain and setting their properties appropriately.
	 * 
	 * @param targetDomain
	 * @param domains
	 * @return
	 * @throws RelationNotValidException
	 */
	Map<DomainModelItentifier, Iterable<?>> enforce(DomainModelItentifier targetDomain, Map<DomainModelItentifier,Iterable<?>> domains) throws RelationNotValidException;

	Relation findMatch(DomainModelItentifier targetDomain, Class<? extends Relation> relationType, Map<DomainModelItentifier, Iterable<?>> domains) throws RelationNotValidException,
	RelationNotApplicableException;

	//<T1,T2,R extends Relation2<T1,T2>> R findMatch2(DomainModelItentifier fromDomain, DomainModelItentifier toDomain, Class<? extends R> relationType, Map<DomainModelItentifier, Iterable<?>> domains) throws RelationNotValidException, RelationNotApplicableException;
	Relation findRelation(DomainModelItentifier targetName, Class<? extends Relation> relationType, Map<DomainModelItentifier, Object> domainArgs) throws RelationNotValidException, RelationNotApplicableException; 
	/*
	 * return match if one is found.
	 * return null if match not found
	 * 
	 * throws RelationNotApplicable if relation is not valid due to nothing in domain2 that matches relation.domain2Type
	 */
	//<T1,T2> Relation findMatch(String domainName, Class<? extends Relation> relationType, T1 d1Object, Iterable<? extends T2> d2Posibles) throws RelationNotValidException, RelationNotApplicableException;

	//<T1,T2> T1 findMatch2(Class<? extends Relation2<T1,T2>> r, Iterable<? extends T1> d1Posibles, T2 d2Object) throws RelationNotApplicableException;
	
}
