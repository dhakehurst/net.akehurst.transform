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

import java.util.List;
import java.util.Set;

public interface ITransformer {

	void registerRule(Class<? extends IBinaryRule<?, ?>> ruleType);

	List<Class<? extends IBinaryRule<?, ?>>> getRuleTypes();

	void clear();

	//
	/**
	 * checks to see if the left and right object match according to the given rule
	 *
	 * @param <L>
	 *            type of the left object
	 * @param <R>
	 *            type of the right object
	 * @param ruleClass
	 *            rule class used to check the match
	 * @param left
	 *            the left object
	 * @param right
	 *            the right object
	 * @return true if left matched right according to the rule
	 * @throws RuleNotFoundException
	 *             if the give rule has not been registered with the transformer
	 */
	<L, R> boolean isAMatch(final Class<? extends IBinaryRule<L, R>> ruleClass, final L left, final R right) throws RuleNotFoundException;

	<L, R> boolean isAllAMatch(final Class<? extends IBinaryRule<L, R>> ruleClass, final List<L> left, final List<R> right) throws RuleNotFoundException;

	// transform
	/**
	 *
	 * Using the given rule transform the left object into a right object. If left is null, right will be null. If this transformer has already transformed the
	 * left object using the given rule, then the same right object as last time will be returned, a new one will not be created, and the right object will not
	 * be updated.
	 *
	 *
	 * @param <L>
	 *            type of the left object
	 * @param <R>
	 *            type of the right object
	 * @param ruleClass
	 *            rule class used to perform the transformation
	 * @param left
	 *            the left object
	 * @return the right object or null if left was null
	 * @throws RuleNotFoundException
	 * @throws TransformException
	 */
	<L, R> R transformLeft2Right(Class<? extends IBinaryRule<L, R>> ruleClass, L left) throws RuleNotFoundException, TransformException;

	<L, R> List<? extends R> transformAllLeft2Right(Class<? extends IBinaryRule<L, R>> ruleClass, List<? extends L> leftList)
			throws RuleNotFoundException, TransformException;

	<L, R> Set<? extends R> transformAllLeft2Right(Class<? extends IBinaryRule<L, R>> ruleClass, Set<? extends L> leftSet)
			throws RuleNotFoundException, TransformException;

	<L, R> L transformRight2Left(Class<? extends IBinaryRule<L, R>> ruleClass, R right) throws RuleNotFoundException, TransformException;

	<L, R> List<? extends L> transformAllRight2Left(Class<? extends IBinaryRule<L, R>> ruleClass, List<? extends R> rightList)
			throws RuleNotFoundException, TransformException;

	<L, R> Set<? extends L> transformAllRight2Left(Class<? extends IBinaryRule<L, R>> ruleClass, Set<? extends R> rightSet)
			throws RuleNotFoundException, TransformException;

	/**
	 * This method will update the rightSet to make it transfomationally consistent with the content of the leftSet. I.e. for the relevant rule, "isAMatch" will
	 * return true for each left,right pair. The leftSet will not be modified. The elements of both left and right Sets must appropriately implement hashcode
	 * and equals methods. The rightSet must support add and remove methods, i.e. it must be modifiable.
	 *
	 * @param ruleClass
	 * @param leftSet
	 * @param rightSet
	 * @throws RuleNotFoundException
	 * @throws TransformException
	 */
	<L, R> void updateAllLeft2Right(final Class<? extends IBinaryRule<L, R>> ruleClass, final Set<? extends L> leftSet, final Set<? extends R> rightSet)
			throws RuleNotFoundException, TransformException;

	// update
	<L, R> void updateLeft2Right(Class<? extends IBinaryRule<L, R>> ruleClass, L left, R right) throws RuleNotFoundException, TransformException;

	<L, R> void updateAllLeft2Right(Class<? extends IBinaryRule<L, R>> ruleClass, List<? extends L> leftList, List<? extends R> rightList)
			throws RuleNotFoundException, TransformException;

	<L, R> void updateRight2Left(Class<? extends IBinaryRule<L, R>> ruleClass, L left, R right) throws RuleNotFoundException, TransformException;

	<L, R> void updateAllRight2Left(Class<? extends IBinaryRule<L, R>> ruleClass, List<? extends L> leftList, List<? extends R> rightList)
			throws RuleNotFoundException, TransformException;

	<L, R> void updateAllRight2Left(final Class<? extends IBinaryRule<L, R>> ruleClass, final Set<? extends L> leftList, final Set<? extends R> rightList)
			throws RuleNotFoundException, TransformException;
}
