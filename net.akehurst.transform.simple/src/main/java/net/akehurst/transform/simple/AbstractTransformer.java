/*************************************************************************
* Copyright (c) 2013 - 2014 Dr David H. Akehurst.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* and is available at http://www.eclipse.org/legal/epl-v10.html
*
* This is based on the original works that contributed to SiTra [http://www.cs.bham.ac.uk/~bxb/Sitra/index.html]
*
* Contributors:
* 	Dr. David H. Akehurst
*   Dr. Behzad Bordbar
*************************************************************************/
package net.akehurst.transform.simple;

	import java.lang.reflect.Constructor;
	import java.lang.reflect.InvocationTargetException;
	import java.lang.reflect.Modifier;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import java.util.Vector;

	import net.akehurst.reflect.BetterMethodFinder;

	public class AbstractTransformer implements Transformer {
		public AbstractTransformer() {
			
		}
		
		public 	void clear() {
			mappings.clear();
		}
		
		Map<Class<? extends Relation>, Map<Object, Object>> mappings = new HashMap<Class<? extends Relation>, Map<Object, Object>>();
		<S, T> Map<S, T> getRuleMappings(Class<? extends Relation<S, T>> rule) {
			Map<S, T> ruleMappings = (Map<S, T>) mappings.get(rule);
			if (ruleMappings == null) {
				ruleMappings = new HashMap<S, T>();
				mappings.put(rule, (Map<Object, Object>) ruleMappings);
			}
			return ruleMappings;
		}
		<S, T> void recordMaping(Class<? extends Relation<S, T>> rule, S source, T target) {
			getRuleMappings(rule).put(source, target);
		}
		public <S, T> T getExistingTargetFor(Class<? extends Relation<S, T>> rule, S source) {
			return getRuleMappings(rule).get(source);
		}
		<S, T> T applyRule(Relation<S, T> r, S source) {
			Class<? extends Relation<S, T>> ruleType = (Class<? extends Relation<S, T>>) r.getClass();
			T target = getExistingTargetFor(ruleType, source);
			if (target == null) {
				target = r.construct(source, this);
				recordMaping(ruleType, source, target);
				r.configure(source, target, this);
			}
			return target;
		}
		// --- Transformer ---
		List<Class<? extends Relation>> ruleTypes = new Vector<Class<? extends Relation>>();
		public List<Class<? extends Relation>> getRuleTypes() {
			if (this.ruleTypes == null) {
				this.ruleTypes = new Vector<Class<? extends Relation>>();
			}
			return this.ruleTypes;
		}
		public void addRuleType(Class<? extends Relation> ruleType) {
			getRuleTypes().add(ruleType);
		}
		List<Relation> getRules(Class<? extends Relation> ruleType, Object... constructorArgs) {
			List<Relation> rules = new Vector<Relation>();
			for (Class<? extends Relation> rt : getRuleTypes()) {
				if (ruleType.isAssignableFrom(rt)) {
					if (!Modifier.isAbstract(rt.getModifiers())) {
						try {
							BetterMethodFinder bmf = new BetterMethodFinder(rt);
							Constructor<Relation> cons = bmf.findConstructor(constructorArgs);
							Relation r = cons.newInstance(constructorArgs);
							rules.add(r);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			return rules;
		}
		public <S, T> T transform(Class<? extends Relation> ruleType, S source, Object... constructorArgs) {
			try {
				List<Relation> rules = getRules(ruleType, constructorArgs);
				//might be better to do this as an follows, but needs assertions to be switched on
				assert !rules.isEmpty() : "No rule " + ruleType + " found in transformer " + this;
				if (rules.isEmpty()) {
					System.err.println("No rule " + ruleType + " found in transformer " + this);
				} else {
					for (Relation rule : rules) {
						Boolean b = false;
						try {
							b = rule.isValidFor(source);
						} catch (ClassCastException e) {}
						if (b) {
							return applyRule((Relation<S, T>) rule, source);
						}
					}
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
			return null;
		}
		public <S, T> List<? extends T> transformAll(Class<? extends Relation> ruleType, List<? extends S> element, Object... constructorArgs) {
			List<T> targets = new Vector<T>();
			for (S s : element) {
				T o = transform(ruleType, s, constructorArgs);
				targets.add(o);
			}
			return targets;
		}
		public Object transform(Object object, Object... constructorArgs) {
			return transform(Relation.class, object, constructorArgs);
		}
		public List<? extends Object> transformAll(List<? extends Object> sourceObjects, Object... constructorArgs) {
			return transformAll(Relation.class, sourceObjects, constructorArgs);
		}
	}
