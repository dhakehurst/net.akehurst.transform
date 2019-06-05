/**
 * Copyright (C) 2019 Dr. David H. Akehurst (http://dr.david.h.akehurst.net)
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

package net.akehurst.transform.binary.api

import kotlin.reflect.KClass

class BinaryRuleNotFoundException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}

class TransformException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}

/**
 * A Binary (Bi-directional) Transformer. Facilitates a bi-directional mapping between two data-structures (left and right).
 *
 * @author Dr. David H. Akehurst
 */
interface BinaryTransformer {

    /**
     * Return the list of registered rules.
     *
     * @return a list of the rules registered with this transformer
     */
    val ruleTypes: List<KClass<out BinaryRule<*, *>>>

    /**
     * Registers a given rule, so that it can be used by this transformer.
     *
     * @param ruleType
     * the rule to register
     */
    fun registerRule(ruleType: KClass<out BinaryRule<*, *>>)

    /**
     * Clears all cached mappings so that this transformer can be reused as though it were newly constructed.
     *
     *
     * Rules remain registered.
     */
    fun clear()

    /**
     * Checks to see if the left and right object are a match according to the given rule.
     *
     * @param <L>
     * type of the left object
     * @param <R>
     * type of the right object
     * @param ruleType
     * rule used to check the match
     * @param left
     * the left object
     * @param right
     * the right object
     * @return true if left matched right according to the rule
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
    </R></L> */
    fun <L:Any, R:Any> isAMatch(ruleType: KClass<out BinaryRule<L, R>>, left: L, right: R): Boolean

    /**
     * Checks to see if the list of left objects match the list of right objects (in order) according to the given rule.
     *
     * @param <L>
     * type of the left object
     * @param <R>
     * type of the right object
     * @param ruleType
     * rule class used to check the match
     * @param leftObjects
     * the left object
     * @param rightObjects
     * the right object
     * @return true if all match
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
    </R></L> */
    fun <L:Any, R:Any> isAllAMatch(ruleType: KClass<out BinaryRule<L, R>>, leftObjects: List<L>, rightObjects: List<R>): Boolean

    /**
     * Using the given rule, transform the left object into a right object. If left is null, right will be null. If this transformer has already transformed the
     * left object left-to-right using the given rule, then the same right object as last time will be returned, a new one will not be created, and the right
     * object will not be updated.
     *
     * @param <L>
     * type of the left object
     * @param <R>
     * type of the right object
     * @param ruleType
     * rule used to perform the transformation
     * @param left
     * the left object
     * @return the right object or null if left was null
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     * @throws TransformException
     * if the rule fails to perform the transformation
    </R></L> */
    fun <L:Any, R:Any> transformLeft2Right(ruleType: KClass<out BinaryRule<L, R>>, left: L): R

    /**
     * Using the given rule, transform all the left objects into right objects. If leftObjects is null, return null. If leftObjects is empty, return empty. If
     * this transformer has already transformed any left object left-to-right using the given rule, then the same right object as last time will be returned, a
     * new one will not be created, and the right object will not be updated.
     *
     * @param <L>
     * type of the left object
     * @param <R>
     * type of the right object
     * @param ruleType
     * rule used to perform the transformation
     * @param leftObjects
     * the List left objects to transform
     * @return a List of right objects, in the corresponding order to the input leftObjects
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     * @throws TransformException
     * if the rule fails to perform the transformation
    </R></L> */
    fun <L:Any, R:Any> transformAllLeft2Right(ruleType: KClass<out BinaryRule<L, R>>, leftObjects: List<L>): List<R>

    /**
     * Using the given rule, transform all the left objects into right objects. If leftObjects is null, return null. If leftObjects is empty, return empty. If
     * this transformer has already transformed any left object left-to-right using the given rule, then the same right object as last time will be returned, a
     * new one will not be created, and the right object will not be updated.
     *
     * @param <L>
     * type of the left object
     * @param <R>
     * type of the right object
     * @param ruleType
     * rule used to perform the transformation
     * @param leftObjects
     * the Set of left objects to transform
     * @return a set of right objects corresponding to the input leftObjects
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     * @throws TransformException
     * if the rule fails to perform the transformation
    </R></L> */
    fun <L:Any, R:Any> transformAllLeft2Right(ruleType: KClass<out BinaryRule<L, R>>, leftObjects: Set<L>): Set<R>

    /**
     * Using the given rule, transform the right object into a left object. If right is null, left will be null. If this transformer has already transformed the
     * right object right-to-left using the given rule, then the same left object as last time will be returned, a new one will not be created, and the left
     * object will not be updated.
     *
     * @param <L>
     * type of the left object
     * @param <R>
     * type of the right object
     * @param ruleType
     * rule used to perform the transformation
     * @param right
     * the right object
     * @return the left object or null if right was null
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     * @throws TransformException
     * if the rule fails to perform the transformation
    </R></L> */
    fun <L:Any, R:Any> transformRight2Left(ruleType: KClass<out BinaryRule<L, R>>, right: R): L

    /**
     * Using the given rule, transform all the right objects into left objects. If rightObjects is null, return null. If rightObjects is empty, return empty. If
     * this transformer has already transformed any right object right-to-left using the given rule, then the same left object as last time will be returned, a
     * new one will not be created, and the left object will not be updated.
     *
     * @param <L>
     * type of the left object
     * @param <R>
     * type of the right object
     * @param ruleType
     * rule used to perform the transformation
     * @param rightObjects
     * a List of right objects
     * @return a List of left objects, in the corresponding order to the input rightObjects
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     * @throws TransformException
     * if the rule fails to perform the transformation
    </R></L> */
    fun <L:Any, R:Any> transformAllRight2Left(ruleType: KClass<out BinaryRule<L, R>>, rightObjects: List<R>): List<L>

    /**
     * Using the given rule, transform all the right objects into left objects. If rightObjects is null, return null. If rightObjects is empty, return empty. If
     * this transformer has already transformed any right object right-to-left using the given rule, then the same left object as last time will be returned, a
     * new one will not be created, and the left object will not be updated.
     *
     * @param <L>
     * type of the left object
     * @param <R>
     * type of the right object
     * @param ruleType
     * rule used to perform the transformation
     * @param rightObjects
     * a Set of right objects
     * @return a Set of left objects corresponding to the input rightObjects
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     * @throws TransformException
     * if the rule fails to perform the transformation
    </R></L> */
    fun <L:Any, R:Any> transformAllRight2Left(ruleType: KClass<out BinaryRule<L, R>>, rightObjects: Set<R>): Set<L>

    /**
     * Using the given rule, update the right object in accordance with the left object. The left object will not be modified. Neither the left or right objects
     * are allowed to be null (null for either value will cause a TransformException to be thrown.
     *
     * @param <L>
     * type of the left object
     * @param <R>
     * type of the right object
     * @param ruleType
     * rule used to perform the transformation
     * @param left
     * the left object
     * @param right
     * the right object
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     * @throws TransformException
     * if the rule fails to perform the transformation
    </R></L> */
    fun <L:Any, R:Any> updateLeft2Right(ruleType: KClass<out BinaryRule<L, R>>, left: L, right: R)

    /**
     * Using the given rule, update all the right objects in accordance with the corresponding (by index) left object. The left object will not be modified.
     * Neither the left or right objects are allowed to be null (null for either value will cause a TransformException to be thrown.
     *
     * @param <L>
     * type of the left object
     * @param <R>
     * type of the right object
     * @param ruleType
     * rule used to perform the transformation
     * @param leftObjects
     * the left objects
     * @param rightObjects
     * the right objects
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     * @throws TransformException
     * if the rule fails to perform the transformation
    </R></L> */
    fun <L:Any, R:Any> updateAllLeft2Right(ruleType: KClass<out BinaryRule<L, R>>, leftObjects: List<L>, rightObjects: List<R>)

    /**
     * This method will update the Set of right objects to make it transfomationally consistent with the content of the Set of left objects. I.e. for the
     * relevant rule, "isAMatch" will return true for each left,right pair. The leftObjects will not be modified. The elements of both left and right Sets must
     * appropriately implement hashcode and equals methods. The rightSet must support add and remove methods, i.e. it must be modifiable.
     *
     * @param <L>
     * type of the left object
     * @param <R>
     * type of the right object
     * @param ruleType
     * rule used to perform the transformation
     * @param leftObjects
     * the left objects
     * @param rightObjects
     * the right objects
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     * @throws TransformException
     * if the rule fails to perform the transformation
    </R></L> */
    fun <L:Any, R:Any> updateAllLeft2Right(ruleType: KClass<out BinaryRule<L, R>>, leftObjects: Set<L>, rightObjects: Set<R>)

    /**
     * Using the given rule, update the left object in accordance with the right object. The right object will not be modified. Neither the left or right
     * objects are allowed to be null (null for either value will cause a TransformException to be thrown.
     *
     * @param <L>
     * type of the left object
     * @param <R>
     * type of the right object
     * @param ruleType
     * rule used to perform the transformation
     * @param left
     * the left object
     * @param right
     * the right object
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     * @throws TransformException
     * if the rule fails to perform the transformation
    </R></L> */
    fun <L:Any, R:Any> updateRight2Left(ruleType: KClass<out BinaryRule<L, R>>, left: L, right: R)

    /**
     * Using the given rule, update all the left objects in accordance with the corresponding (by index) right object. The right object will not be modified.
     * Neither the left or right objects are allowed to be null (null for either value will cause a TransformException to be thrown.
     *
     * @param <L>
     * type of the left object
     * @param <R>
     * type of the right object
     * @param ruleType
     * rule used to perform the transformation
     * @param leftObjects
     * the left objects
     * @param rightObjects
     * the right objects
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     * @throws TransformException
     * if the rule fails to perform the transformation
    </R></L> */
    fun <L:Any, R:Any> updateAllRight2Left(ruleType: KClass<out BinaryRule<L, R>>, leftObjects: List<L>, rightObjects: List<R>)

    /**
     * This method will update the Set of left objects to make it transfomationally consistent with the content of the Set of right objects. I.e. for the
     * relevant rule, "isAMatch" will return true for each left,right pair. The rightObjects will not be modified. The elements of both left and right Sets must
     * appropriately implement hashcode and equals methods. The leftSet must support add and remove methods, i.e. it must be modifiable.
     *
     * @param <L>
     * type of the left object
     * @param <R>
     * type of the right object
     * @param ruleType
     * rule used to perform the transformation
     * @param leftObjects
     * the left objects
     * @param rightObjects
     * the right objects
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     * @throws TransformException
     * if the rule fails to perform the transformation
    </R></L> */
    fun <L:Any, R:Any> updateAllRight2Left(ruleType: KClass<out BinaryRule<L, R>>, leftObjects: Set<L>, rightObjects: Set<R>)
}
