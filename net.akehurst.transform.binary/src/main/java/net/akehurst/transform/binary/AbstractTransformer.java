/**
 * Copyright (C) 2015 Dr. David H. Akehurst (http://dr.david.h.akehurst.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.akehurst.transform.binary;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
	List<Relation> getRules(Class<? extends Relation> ruleType) {
		List<Relation> rules = new Vector<Relation>();
		for (Class<? extends Relation> rt : getRuleTypes()) {
			if (ruleType.isAssignableFrom(rt)) {
				if (!Modifier.isAbstract(rt.getModifiers())) {
					try {
						Constructor<? extends Relation> cons = rt.getConstructor(new Class<?>[0]);
						Relation r = cons.newInstance();
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
	public <L,R> R transformLeft2Right(Class<? extends Relation<L,R>> ruleClass, L left) throws RelationNotFoundException {
		try {
			List<Relation> rules = getRules(ruleClass);
			if (rules.isEmpty()) {
				throw new RelationNotFoundException("No relation " + ruleClass + " found in transformer " + this);
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
						throw new RelationNotFoundException("No relation " + ruleClass + " found that is appicable to " + left);
					}
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	public <L,R> List<? extends R> transformAllLeft2Right(Class<? extends Relation<L,R>> ruleType, List<? extends L> elements) throws RelationNotFoundException {
		List<R> rights = new Vector<R>();
		for (L left : elements) {
			R o = transformLeft2Right(ruleType, left);
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
	public <L,R> L transformRight2Left(Class<? extends Relation<L,R>> ruleClass, R right) throws RelationNotFoundException {
		try {
			List<Relation> rules = getRules(ruleClass);
		if (rules.isEmpty()) {
			throw new RelationNotFoundException("No relation " + ruleClass + " found in transformer " + this);
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
					throw new RelationNotFoundException("No relation " + ruleClass + " found that is appicable to " + right);
				}
			}
		}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	public <L,R> List<? extends L> transformAllRight2Left(Class<? extends Relation<L,R>> ruleClass, List<? extends R> rightObjects) throws RelationNotFoundException
	{
		List<L> leftObjects = new Vector<L>();
		for (R right : rightObjects) {
			L left = transformRight2Left(ruleClass, right);
			leftObjects.add(left);
		}
		return leftObjects;
	}

}
