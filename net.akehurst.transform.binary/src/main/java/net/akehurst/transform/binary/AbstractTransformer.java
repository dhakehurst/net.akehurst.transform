/*************************************************************************
* Copyright (c) 2013 - 2014 Dr David H. Akehurst.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* and is available at http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* 	Dr. David H. Akehurst
*************************************************************************/
package net.akehurst.transform.binary;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.akehurst.reflect.BetterMethodFinder;

public class AbstractTransformer implements Transformer {
	public AbstractTransformer() {
	}
	
	public void clear() {}
	// --- Transformer ---
	List<Class<? extends Relation<?,?>>> ruleTypes = new Vector<Class<? extends Relation<?,?>>>();
	public List<Class<? extends Relation<?,?>>> getRuleTypes() {
		if (this.ruleTypes == null) {
			this.ruleTypes = new Vector<Class<? extends Relation<?,?>>>();
		}
		return this.ruleTypes;
	}
	public void registerRule(Class<? extends Relation<?,?>> ruleType) {
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
	//--- left to right ---
	Map<Class<? extends Relation>, Map<Object, Object>> mappingsLeft2Right = new HashMap<Class<? extends Relation>, Map<Object, Object>>();
	<L,R> Map<L,R> getRuleMappingsLeft2Right(Class<? extends Relation<L,R>> rule) {
		Map<L,R> ruleMappings = (Map<L,R>) mappingsLeft2Right.get(rule);
		if (ruleMappings == null) {
			ruleMappings = new HashMap<L,R>();
			mappingsLeft2Right.put(rule, (Map<Object, Object>) ruleMappings);
		}
		return ruleMappings;
	}
	<L,R> void recordMapingLeft2Right(Class<? extends Relation<L,R>> rule, L left, R right) {
		getRuleMappingsLeft2Right(rule).put(left, right);
	}
	<L,R> R getExistingTargetForLeft2Right(Class<? extends Relation<L,R>> rule, L left) {
		return getRuleMappingsLeft2Right(rule).get(left);
	}
	<L,R> R applyRuleLeft2Right(Relation<L,R> r, L left) {
		Class<? extends Relation<L,R>> ruleType = (Class<? extends Relation<L,R>>) r.getClass();
		R right = getExistingTargetForLeft2Right(ruleType, left);
		if (right == null) {
			right = r.constructLeft2Right(left, this);
			recordMapingLeft2Right(ruleType, left, right);
			r.configureLeft2Right(left, right, this);
		}
		return right;
	}
	public <L,R> R transformLeft2Right(Class<? extends Relation<L,R>> ruleType, L left, Object... constructorArgs) throws RelationNotFoundException {
		List<Relation> rules = getRules(ruleType, constructorArgs);
		if (rules.isEmpty()) {
			throw new RelationNotFoundException("No relation " + ruleType + " found in transformer " + this);
		} else {
			int exceptionCount = 0;
			for (Relation rule : rules) {
				Boolean b = false;
				try {
					b = rule.isValidForLeft2Right(left);
				} catch (ClassCastException e) {
					++exceptionCount;
				}
				if (b) {
					return applyRuleLeft2Right((Relation<L,R>) rule, left);
				}
				if (exceptionCount == rules.size()) {
					throw new RelationNotFoundException("No relation " + ruleType + " found that is appicable to " + left);
				}
			}
		}
		return null;
	}
	public <L,R> List<? extends R> transformAllLeft2Right(Class<? extends Relation<L,R>> ruleType, List<? extends L> elements,
			Object... constructorArgs) throws RelationNotFoundException {
		List<R> rights = new Vector<R>();
		for (L left : elements) {
			R o = transformLeft2Right(ruleType, left, constructorArgs);
			rights.add(o);
		}
		return rights;
	}

	//--- right to left ---
	Map<Class<? extends Relation<?,?>>, Map<Object, Object>> mappingsRight2Left = new HashMap<Class<? extends Relation<?,?>>, Map<Object, Object>>();
	<L,R> Map<R,L> getRuleMappingsRight2Left(Class<? extends Relation<L,R>> rule) {
		Map<R,L> ruleMappings = (Map<R,L>) mappingsRight2Left.get(rule);
		if (ruleMappings == null) {
			ruleMappings = new HashMap<R,L>();
			mappingsRight2Left.put(rule, (Map<Object, Object>) ruleMappings);
		}
		return ruleMappings;
	}
	<L,R> void recordMapingRight2Left(Class<? extends Relation<L,R>> rule, L left, R right) {
		getRuleMappingsRight2Left(rule).put(right, left);
	}
	<L,R> L getExistingTargetForRight2Left(Class<? extends Relation<L,R>> rule, R right) {
		return getRuleMappingsRight2Left(rule).get(right);
	}
	<L,R> L applyRuleRight2Left(Relation<L,R> r, R right) {
		Class<? extends Relation<L,R>> ruleType = (Class<? extends Relation<L,R>>) r.getClass();
		L left = getExistingTargetForRight2Left(ruleType, right);
		if (left == null) {
			left = r.constructRight2Left(right, this);
			this.recordMapingRight2Left(ruleType, left, right);
			r.configureRight2Left(left, right, this);
		}
		return left;
	}
	public <L,R> L transformRight2Left(Class<? extends Relation<L,R>> ruleType, R right, Object... constructorArgs) throws RelationNotFoundException {
		List<Relation> rules = getRules(ruleType, constructorArgs);
		if (rules.isEmpty()) {
			throw new RelationNotFoundException("No relation " + ruleType + " found in transformer " + this);
		} else {
			int exceptionCount = 0;
			for (Relation rule : rules) {
				Boolean b = false;
				try {
					b = rule.isValidForRight2Left(right);
				} catch (ClassCastException e) {
					++exceptionCount;
				}
				if (b) {
					return applyRuleRight2Left((Relation<L,R>) rule, right);
				}
				if (exceptionCount == rules.size()) {
					throw new RelationNotFoundException("No relation " + ruleType + " found that is appicable to " + right);
				}
			}
		}
		return null;
	}
	public <L,R> List<? extends L> transformAllRight2Left(Class<? extends Relation<L,R>> ruleClass, List<? extends R> rightObjects, Object...constructorArgs)throws RelationNotFoundException
	{
		List<L> leftObjects = new Vector<L>();
		for (R right : rightObjects) {
			L left = transformRight2Left(ruleClass, right, constructorArgs);
			leftObjects.add(left);
		}
		return leftObjects;
	}

}
