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

package net.akehurst.transform.binary.basic

import net.akehurst.kotlinx.reflect.reflect
import net.akehurst.transform.binary.api.BinaryRule
import net.akehurst.transform.binary.api.BinaryRuleNotFoundException
import net.akehurst.transform.binary.api.BinaryTransformer
import kotlin.reflect.*

/**
 * A basic implementation of a Binary Transformer.
 *
 * @author Dr. David H. Akehurst
 *
 */
abstract
class BinaryTransformerAbstract : BinaryTransformer {


    private val mappingsLeft2Right = mutableMapOf<KClass<BinaryRule<*, *>>, Map<Any, Any>>()
    private val mappingsRight2Left = mutableMapOf<KClass<BinaryRule<*, *>>, Map<Any, Any>>()

    private fun <L:Any, R:Any> getRules(ruleType: KClass<out BinaryRule<out L, out R>>): List<BinaryRule<L, R>> {
        return ruleTypes.mapNotNull {
            if (it.reflect().isAbstract) {
                null
            } else {
                val instance = it.reflect().construct()
                if (ruleType.isInstance(instance)) {
                    instance as BinaryRule<L, R>
                } else {
                    null
                }
            }
        };
    }

    private fun <L:Any, R:Any> recordMaping(rule: KClass<BinaryRule<L, R>>, left: L, right: R) {
        this.getRuleMappingsLeft2Right(rule)[left] = right;
        this.getRuleMappingsRight2Left(rule)[right] = left;
    }

    // --- left to right ---
    private fun <L:Any, R:Any> getRuleMappingsLeft2Right(rule: KClass<BinaryRule<L, R>>): MutableMap<L, R> {
        var ruleMappings: MutableMap<L, R>? = this.mappingsLeft2Right[rule as KClass<BinaryRule<*, *>>] as MutableMap<L, R>?
        if (ruleMappings == null) {
            ruleMappings = mutableMapOf()
            this.mappingsLeft2Right[rule] = ruleMappings as MutableMap<Any, Any>
        }
        return ruleMappings;
    }

    private fun <L:Any, R:Any> getExistingTargetForLeft2Right(rule: KClass<BinaryRule<L, R>>, left: L): R? {
        return this.getRuleMappingsLeft2Right(rule)[left];
    }

    private fun <L:Any, R:Any> applyRuleLeft2Right(r: BinaryRule<L, R>, left: L): R {
        val ruleType = r::class as KClass<BinaryRule<L, R>>;
        var right = this.getExistingTargetForLeft2Right(ruleType, left);
        return if (right == null) {
            right = r.constructLeft2Right(left, this);
            this.recordMaping(ruleType, left, right);
            r.updateLeft2Right(left, right, this);
            right
        } else {
            right;
        }
    }

    // --- right to left ---
    private fun <L:Any, R:Any> getRuleMappingsRight2Left(rule: KClass<BinaryRule<L, R>>): MutableMap<R,L> {
        var ruleMappings: MutableMap<R,L>? = this.mappingsRight2Left[rule as KClass<BinaryRule<*, *>>] as MutableMap<R,L>?
        if (ruleMappings == null) {
            ruleMappings = mutableMapOf()
            this.mappingsRight2Left[rule] = ruleMappings as MutableMap<Any, Any>;
        }
        return ruleMappings;
    }

    private fun <L:Any, R:Any> getExistingTargetForRight2Left(rule: KClass<BinaryRule<L, R>>, right: R): L? {
        return this.getRuleMappingsRight2Left(rule)[right];
    }

    private fun <L:Any, R:Any> applyRuleRight2Left(r: BinaryRule<L, R>, right: R): L {
        val ruleType = r::class as KClass<BinaryRule<L, R>>;
        var left = this.getExistingTargetForRight2Left(ruleType, right);
        return if (left == null) {
            left = r.constructRight2Left(right, this);
            this.recordMaping(ruleType, left, right);
            r.updateRight2Left(left, right, this);
            left
        } else {
            left;
        }
    }

    // --- BinaryTransformer ---

    override val ruleTypes = mutableListOf<KClass<out BinaryRule<*, *>>>()

    override fun registerRule(ruleType: KClass<out BinaryRule<*, *>>) {
        this.ruleTypes.add(ruleType)
    }

    override fun clear() {
        this.mappingsLeft2Right.clear()
        this.mappingsRight2Left.clear()
    }

    override fun <L:Any, R:Any> isAMatch(ruleType: KClass<out BinaryRule<L, R>>, left: L, right: R): Boolean {
        val rules = this.getRules(ruleType);
        if (rules.isEmpty()) {
            throw BinaryRuleNotFoundException("No relation $ruleType found in transformer $this")
        } else {
            var exceptionCount = 0
            for (rule in rules) {
                var b = false
                try {
                    b = rule.isValidForLeft2Right(left, this) && rule.isValidForRight2Left(right, this)
                } catch (e: ClassCastException) {
                    ++exceptionCount
                }
                if (b) {
                    return rule.isAMatch(left, right, this)
                }
                if (exceptionCount == rules.size) {
                    throw BinaryRuleNotFoundException("No relation $ruleType found that is appicable to $left")
                }
            }
            return false;
        }
    }

    override fun <L:Any, R:Any> isAllAMatch(ruleType: KClass<out BinaryRule<L, R>>, leftObjects: List<L>, rightObjects: List<R>): Boolean {
        return when {
            //(null == leftObjects && null == rightObjects) -> true
            //(null == leftObjects || null == rightObjects) -> false
            (leftObjects.isEmpty() && rightObjects.isEmpty()) -> true
            (leftObjects.size != rightObjects.size) -> false
            else -> {
                for (i in 0 until leftObjects.size) {
                    val left = leftObjects[i]
                    val right = rightObjects[i]
                    val elementMatch = this.isAMatch(ruleType, left, right)
                    // fail fast
                    if (!elementMatch) {
                        return false;
                    }
                }
                true
            }
        }
    }

    // --- left2right ---
    override fun <L:Any, R:Any> transformLeft2Right(ruleType: KClass<out BinaryRule<L, R>>, left: L): R {
        val rules = this.getRules(ruleType);
        if (rules.isEmpty()) {
            throw BinaryRuleNotFoundException("No relation " + ruleType + " found in transformer " + this);
        } else {
            var exceptionCount = 0;
            for (rule in rules) {
                var b = false;
                try {
                    b = rule.isValidForLeft2Right(left, this);
                } catch (e: ClassCastException) {
                    ++exceptionCount;
                }
                if (b) {
                    return this.applyRuleLeft2Right(rule, left);
                }
                if (exceptionCount == rules.size) {
                    throw BinaryRuleNotFoundException("No relation " + ruleType + " found that is appicable to " + left);
                }
            }
        }
        throw  BinaryRuleNotFoundException("No relation " + ruleType + " found that is appicable to " + left);
    }

    override fun <L:Any, R:Any> transformAllLeft2Right(ruleType: KClass<out BinaryRule<L, R>>, leftObjects: List<L>): List<R> {
            val rightObjects = mutableListOf<R>()
            for (left in leftObjects) {
                val right = this.transformLeft2Right(ruleType, left);
                rightObjects.add(right);
            }
            return rightObjects;
    }

    override fun <L:Any, R:Any> transformAllLeft2Right(ruleType: KClass<out BinaryRule<L, R>>, leftObjects: Set<L>): Set<R> {
        val rightObjects = mutableSetOf<R>()
        for (left in leftObjects) {
            val right = this.transformLeft2Right(ruleType, left);
            rightObjects.add(right);
        }
        return rightObjects;
    }

    override fun <L:Any, R:Any> updateLeft2Right(ruleType: KClass<out BinaryRule<L, R>>, left: L, right: R) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <L:Any, R:Any> updateAllLeft2Right(ruleType: KClass<out BinaryRule<L, R>>, leftObjects: List<L>, rightObjects: List<R>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <L:Any, R:Any> updateAllLeft2Right(ruleType: KClass<out BinaryRule<L, R>>, leftObjects: Set<L>, rightObjects: Set<R>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // --- right to left ---
    override fun <L:Any, R:Any> transformRight2Left(ruleType: KClass<out BinaryRule<L, R>>, right: R): L {
        val rules = this.getRules(ruleType);
        if (rules.isEmpty()) {
            throw BinaryRuleNotFoundException("No relation " + ruleType + " found in transformer " + this);
        } else {
            var exceptionCount = 0;
            for (rule in rules) {
                var b = false;
                try {
                    b = rule.isValidForRight2Left(right, this);
                } catch (e: ClassCastException) {
                    ++exceptionCount;
                }
                if (b) {
                    return this.applyRuleRight2Left(rule, right);
                }
                if (exceptionCount == rules.size) {
                    throw BinaryRuleNotFoundException("No relation " + ruleType + " found that is appicable to " + right);
                }
            }
        }
        throw  BinaryRuleNotFoundException("No relation " + ruleType + " found that is appicable to " + right);
    }

    override fun <L:Any, R:Any> transformAllRight2Left(ruleType: KClass<out BinaryRule<L, R>>, rightObjects: List<R>): List<L> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <L:Any, R:Any> transformAllRight2Left(ruleType: KClass<out BinaryRule<L, R>>, rightObjects: Set<R>): Set<L> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <L:Any, R:Any> updateRight2Left(ruleType: KClass<out BinaryRule<L, R>>, left: L, right: R) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <L:Any, R:Any> updateAllRight2Left(ruleType: KClass<out BinaryRule<L, R>>, leftObjects: List<L>, rightObjects: List<R>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <L:Any, R:Any> updateAllRight2Left(ruleType: KClass<out BinaryRule<L, R>>, leftObjects: Set<L>, rightObjects: Set<R>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

