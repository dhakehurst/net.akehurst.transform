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

/**
 * A Binary rule defined how to map between a left hand side object and a right hand side object. i.e. it facilitates the mapping in either direction.
 *
 * @author Dr. David H. Akehurst
 *
 * @param <L>
 * type of the left hand side of the rule
 * @param <R>
 * type of the right hand side of the rule
</R></L> */
interface BinaryRule<L:Any, R:Any> {

    /**
     * Indicates that this rule is valid for mapping a left hand side object to a right hand side object.
     *
     *
     * Useful when we have a type hierarchy of rules, otherwise, in the simple case, it can return true.
     *
     * @param left
     * the left hand side object
     * @param transformer
     * the transformer
     * @return true if the rule is applicable
     */
    fun isValidForLeft2Right(left: L, transformer: BinaryTransformer): Boolean

    /**
     * Indicates that this rule is valid for applying to the right hand side object.
     *
     *
     * Useful when we have a type hierarchy of rules, otherwise, in the simple case, it can return true.
     *
     * @param right
     * the right hand side object
     * @param transformer
     * the transformer
     * @return true if the rule is applicable
     */
    fun isValidForRight2Left(right: R, transformer: BinaryTransformer): Boolean

    /**
     * Checks to see if the left and right object are a match. I.e. The result of this method defines whether, according to this rule, the two objects should be
     * considered to map to each other.
     *
     * @param left
     * hand side object
     * @param right
     * hand side object
     * @param transformer
     * the transformer
     * @return true if the left and right object are a match
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     */
    fun isAMatch(left: L, right: R, transformer: BinaryTransformer): Boolean

    /**
     * Defines how to construct a right hand side object if we are given a left hand side object.
     *
     * @param left
     * hand side object
     * @param transformer
     * the transformer
     * @return a right hand side object
     * @throws TransformException
     * if construction fails
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     */
    fun constructLeft2Right(left: L, transformer: BinaryTransformer): R

    /**
     * Defines how to construct a left hand side object if we are given a right hand side object.
     *
     * @param right
     * hand side object
     * @param transformer
     * the transformer
     * @return a left hand side object
     * @throws TransformException
     * if construction fails
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     */
    fun constructRight2Left(right: R, transformer: BinaryTransformer): L

    /**
     * Defines how to update the right hand side object, given a left hand side object.
     *
     *
     * the left hand object should not be modified
     *
     * @param left
     * hand side object
     * @param right
     * hand side object
     * @param transformer
     * the transformer
     * @throws TransformException
     * if update fails
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     */
    fun updateLeft2Right(left: L, right: R, transformer: BinaryTransformer)

    /**
     * Defines how to update the left hand side object, given a right hand side object.
     *
     *
     * the right hand object should not be modified
     *
     * @param left
     * hand side object
     * @param right
     * hand side object
     * @param transformer
     * the transformer
     * @throws TransformException
     * if update fails
     * @throws BinaryRuleNotFoundException
     * if the give rule has not been registered with the transformer
     */
    fun updateRight2Left(left: L, right: R, transformer: BinaryTransformer)
}
