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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractTransformer implements ITransformer {

	public AbstractTransformer() {
		this.ruleTypes = new ArrayList<>();
		this.mappingsLeft2Right = new HashMap<>();
		this.mappingsRight2Left = new HashMap<>();
	}

	private final List<Class<? extends IBinaryRule<?, ?>>> ruleTypes;
	private final Map<Class<? extends IBinaryRule<?, ?>>, Map<Object, Object>> mappingsLeft2Right;
	private final Map<Class<? extends IBinaryRule<?, ?>>, Map<Object, Object>> mappingsRight2Left;

	@Override
	public void clear() {
		this.mappingsLeft2Right.clear();
		this.mappingsRight2Left.clear();
	}

	// --- Transformer ---

	@Override
	public List<Class<? extends IBinaryRule<?, ?>>> getRuleTypes() {
		return this.ruleTypes;
	}

	@Override
	public void registerRule(final Class<? extends IBinaryRule<?, ?>> ruleType) {
		this.getRuleTypes().add(ruleType);
	}

	private List<IBinaryRule> getRules(final Class<? extends IBinaryRule> ruleType) {
		final List<IBinaryRule> rules = new ArrayList<>();
		for (final Class<? extends IBinaryRule> rt : this.getRuleTypes()) {
			if (ruleType.isAssignableFrom(rt)) {
				if (!Modifier.isAbstract(rt.getModifiers())) {
					try {
						final Constructor<? extends IBinaryRule> cons = rt.getConstructor(new Class<?>[0]);
						final IBinaryRule r = cons.newInstance();
						rules.add(r);
					} catch (final Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return rules;
	}

	private <L, R> void recordMaping(final Class<? extends IBinaryRule<L, R>> rule, final L left, final R right) {
		this.getRuleMappingsLeft2Right(rule).put(left, right);
		this.getRuleMappingsRight2Left(rule).put(right, left);
	}

	@Override
	public <L, R> boolean isAMatch(final Class<? extends IBinaryRule<L, R>> ruleClass, final L left, final R right) throws RuleNotFoundException {
		final List<IBinaryRule> rules = this.getRules(ruleClass);
		if (rules.isEmpty()) {
			throw new RuleNotFoundException("No relation " + ruleClass + " found in transformer " + this);
		} else {
			int exceptionCount = 0;
			for (final IBinaryRule<L, R> rule : rules) {
				boolean b = false;
				try {
					b = rule.isValidForLeft2Right(left) && rule.isValidForRight2Left(right);
				} catch (final ClassCastException e) {
					++exceptionCount;
				}
				if (b) {
					return rule.isAMatch(left, right, this);
				}
				if (exceptionCount == rules.size()) {
					throw new RuleNotFoundException("No relation " + ruleClass + " found that is appicable to " + left);
				}
			}
			return false;
		}
	}

	// --- left to right ---
	private <L, R> Map<L, R> getRuleMappingsLeft2Right(final Class<? extends IBinaryRule<L, R>> rule) {
		Map<L, R> ruleMappings = (Map<L, R>) this.mappingsLeft2Right.get(rule);
		if (ruleMappings == null) {
			ruleMappings = new HashMap<>();
			this.mappingsLeft2Right.put(rule, (Map<Object, Object>) ruleMappings);
		}
		return ruleMappings;
	}

	private <L, R> R getExistingTargetForLeft2Right(final Class<? extends IBinaryRule<L, R>> rule, final L left) {
		return this.getRuleMappingsLeft2Right(rule).get(left);
	}

	private <L, R> R applyRuleLeft2Right(final IBinaryRule<L, R> r, final L left) throws TransformException, RuleNotFoundException {
		final Class<? extends IBinaryRule<L, R>> ruleType = (Class<? extends IBinaryRule<L, R>>) r.getClass();
		R right = this.getExistingTargetForLeft2Right(ruleType, left);
		if (right == null) {
			right = r.constructLeft2Right(left, this);
			this.recordMaping(ruleType, left, right);
			r.updateLeft2Right(left, right, this);
		}
		return right;
	}

	@Override
	public <L, R> R transformLeft2Right(final Class<? extends IBinaryRule<L, R>> ruleClass, final L left) throws RuleNotFoundException, TransformException {
		if (null == left) {
			return null;
		} else {
			final List<IBinaryRule> rules = this.getRules(ruleClass);
			if (rules.isEmpty()) {
				throw new RuleNotFoundException("No relation " + ruleClass + " found in transformer " + this);
			} else {
				int exceptionCount = 0;
				for (final IBinaryRule rule : rules) {
					boolean b = false;
					try {
						b = rule.isValidForLeft2Right(left);
					} catch (final ClassCastException e) {
						++exceptionCount;
					}
					if (b) {
						return this.applyRuleLeft2Right((IBinaryRule<L, R>) rule, left);
					}
					if (exceptionCount == rules.size()) {
						throw new RuleNotFoundException("No relation " + ruleClass + " found that is appicable to " + left);
					}
				}
			}
			return null;
		}
	}

	@Override
	public <L, R> List<? extends R> transformAllLeft2Right(final Class<? extends IBinaryRule<L, R>> ruleType, final List<? extends L> leftList)
			throws RuleNotFoundException, TransformException {
		if (null == leftList) {
			return null;
		} else {
			final List<R> rightList = new ArrayList<>();
			for (final L left : leftList) {
				final R o = this.transformLeft2Right(ruleType, left);
				rightList.add(o);
			}
			return rightList;
		}
	}

	@Override
	public <L, R> void updateLeft2Right(final Class<? extends IBinaryRule<L, R>> ruleClass, final L left, final R right)
			throws RuleNotFoundException, TransformException {
		if (null == left || null == right) {
			throw new TransformException("Cannot update from or to a null object", null);
		}
		final List<IBinaryRule> rules = this.getRules(ruleClass);
		if (rules.isEmpty()) {
			throw new RuleNotFoundException("No relation " + ruleClass + " found in transformer " + this);
		} else {
			int exceptionCount = 0;
			for (final IBinaryRule<L, R> rule : rules) {
				boolean b = false;
				try {
					b = rule.isValidForLeft2Right(left);
				} catch (final ClassCastException e) {
					++exceptionCount;
				}
				if (b) {
					final Class<? extends IBinaryRule<L, R>> ruleType = (Class<? extends IBinaryRule<L, R>>) rule.getClass();
					this.recordMaping(ruleType, left, right);
					rule.updateLeft2Right(left, right, this);
				}
				if (exceptionCount == rules.size()) {
					throw new RuleNotFoundException("No relation " + ruleClass + " found that is appicable to " + left);
				}
			}
		}

	}

	@Override
	public <L, R> void updateAllLeft2Right(final Class<? extends IBinaryRule<L, R>> ruleClass, final List<? extends L> leftList,
			final List<? extends R> rightList) throws RuleNotFoundException, TransformException {
		if (null == leftList || null == rightList) {
			throw new TransformException("Cannot update from or to a null collection", null);
		}

		final List<R> newRightList = new ArrayList<>();
		for (final L left : leftList) {
			R right = this.findMatchLeft2Right(ruleClass, left, rightList);
			if (null == right) {
				right = this.transformLeft2Right(ruleClass, left);
			} else {
				this.updateLeft2Right(ruleClass, left, right);
			}
			newRightList.add(right);
		}

		int i = 0;
		while (i < newRightList.size()) {
			final R nr = newRightList.get(i);
			if (i < rightList.size()) {
				((List<R>) rightList).set(i, nr);

			} else {
				((List<R>) rightList).add(nr);
			}
			++i;
		}
		while (i < rightList.size()) {
			rightList.remove(i);
		}
	}

	private <L, R> R findMatchLeft2Right(final Class<? extends IBinaryRule<L, R>> ruleClass, final L left, final List<? extends R> rightList)
			throws RuleNotFoundException {
		for (final R right : rightList) {
			if (this.isAMatch(ruleClass, left, right)) {
				return right;
			}
		}
		return null;
	}

	// --- right to left ---
	private <L, R> Map<R, L> getRuleMappingsRight2Left(final Class<? extends IBinaryRule<L, R>> rule) {
		Map<R, L> ruleMappings = (Map<R, L>) this.mappingsRight2Left.get(rule);
		if (ruleMappings == null) {
			ruleMappings = new HashMap<>();
			this.mappingsRight2Left.put(rule, (Map<Object, Object>) ruleMappings);
		}
		return ruleMappings;
	}

	private <L, R> L getExistingTargetForRight2Left(final Class<? extends IBinaryRule<L, R>> rule, final R right) {
		return this.getRuleMappingsRight2Left(rule).get(right);
	}

	private <L, R> L applyRuleRight2Left(final IBinaryRule<L, R> r, final R right) throws TransformException, RuleNotFoundException {
		final Class<? extends IBinaryRule<L, R>> ruleType = (Class<? extends IBinaryRule<L, R>>) r.getClass();
		L left = this.getExistingTargetForRight2Left(ruleType, right);
		if (left == null) {
			left = r.constructRight2Left(right, this);
			this.recordMaping(ruleType, left, right);
			r.updateRight2Left(left, right, this);
		}
		return left;
	}

	@Override
	public <L, R> L transformRight2Left(final Class<? extends IBinaryRule<L, R>> ruleClass, final R right) throws RuleNotFoundException, TransformException {
		if (null == right) {
			return null;
		} else {
			final List<IBinaryRule> rules = this.getRules(ruleClass);
			if (rules.isEmpty()) {
				throw new RuleNotFoundException("No relation " + ruleClass + " found in transformer " + this);
			} else {
				int exceptionCount = 0;
				for (final IBinaryRule rule : rules) {
					Boolean b = false;
					try {
						b = rule.isValidForRight2Left(right);
					} catch (final ClassCastException e) {
						++exceptionCount;
					}
					if (b) {
						return this.applyRuleRight2Left((IBinaryRule<L, R>) rule, right);
					}
					if (exceptionCount == rules.size()) {
						throw new RuleNotFoundException("No relation " + ruleClass + " found that is appicable to " + right);
					}
				}
			}

			return null;
		}
	}

	@Override
	public <L, R> List<? extends L> transformAllRight2Left(final Class<? extends IBinaryRule<L, R>> ruleClass, final List<? extends R> rightObjects)
			throws RuleNotFoundException, TransformException {
		if (null == rightObjects) {
			return null;
		} else {
			final List<L> leftObjects = new ArrayList<>();
			for (final R right : rightObjects) {
				final L left = this.transformRight2Left(ruleClass, right);
				leftObjects.add(left);
			}
			return leftObjects;
		}
	}

	@Override
	public <L, R> void updateRight2Left(final Class<? extends IBinaryRule<L, R>> ruleClass, final L left, final R right)
			throws RuleNotFoundException, TransformException {
		if (null == left || null == right) {
			throw new TransformException("Cannot update from or to a null object", null);
		}
		try {
			final List<IBinaryRule> rules = this.getRules(ruleClass);
			if (rules.isEmpty()) {
				throw new RuleNotFoundException("No relation " + ruleClass + " found in transformer " + this);
			} else {
				int exceptionCount = 0;
				for (final IBinaryRule<L, R> rule : rules) {
					boolean b = false;
					try {
						b = rule.isValidForRight2Left(right);
					} catch (final ClassCastException e) {
						++exceptionCount;
					}
					if (b) {
						final Class<? extends IBinaryRule<L, R>> ruleType = (Class<? extends IBinaryRule<L, R>>) rule.getClass();
						this.recordMaping(ruleType, left, right);
						rule.updateRight2Left(left, right, this);
					}
					if (exceptionCount == rules.size()) {
						throw new RuleNotFoundException("No relation " + ruleClass + " found that is appicable to " + right);
					}
				}
			}
		} catch (final Throwable t) {
			t.printStackTrace();
		}
	}

	@Override
	public <L, R> void updateAllRight2Left(final Class<? extends IBinaryRule<L, R>> ruleClass, final List<? extends L> leftList,
			final List<? extends R> rightList) throws RuleNotFoundException, TransformException {
		if (null == leftList || null == rightList) {
			throw new TransformException("Cannot update from or to a null collection", null);
		}

		final List<L> newLeftList = new ArrayList<>();
		for (final R right : rightList) {
			L left = this.findMatchRight2Left(ruleClass, right, leftList);
			if (null == left) {
				left = this.transformRight2Left(ruleClass, right);
			} else {
				this.updateRight2Left(ruleClass, left, right);
			}
			newLeftList.add(left);
		}

		int i = 0;
		while (i < newLeftList.size()) {
			final L newLeft = newLeftList.get(i);
			if (i < leftList.size()) {
				((List<L>) leftList).set(i, newLeft);

			} else {
				((List<L>) leftList).add(newLeft);
			}
			++i;
		}
		while (i < leftList.size()) {
			leftList.remove(i);
		}
	}

	private <L, R> L findMatchRight2Left(final Class<? extends IBinaryRule<L, R>> ruleClass, final R right, final List<? extends L> leftList)
			throws RuleNotFoundException {
		for (final L left : leftList) {
			if (this.isAMatch(ruleClass, left, right)) {
				return left;
			}
		}
		return null;
	}

}
