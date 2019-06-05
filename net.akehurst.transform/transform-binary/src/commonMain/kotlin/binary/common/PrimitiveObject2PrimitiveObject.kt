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

package net.akehurst.transform.binary.common

import net.akehurst.transform.binary.api.BinaryRuleNotFoundException
import net.akehurst.transform.binary.api.BinaryTransformer
import net.akehurst.transform.binary.api.BinaryRule
import kotlin.reflect.KClass


class Primitive2Primitive : BinaryRule<Any, Any> {

    companion object {

        private val PRIMITIVES = setOf(
                Boolean::class,
                Char::class,
                Byte::class,
                Short::class,
                Int::class,
                Long::class,
                Float::class,
                Double::class,
                Unit::class,
                String::class
        )

        private fun <T : Any> KClass<T>.isPrimitive(): Boolean {
            return PRIMITIVES.contains(this)
        }
    }

    override fun isValidForLeft2Right(left: Any, transformer: BinaryTransformer): Boolean {
        return left::class.isPrimitive()
    }

    override fun isValidForRight2Left(right: Any, transformer: BinaryTransformer): Boolean {
        return right::class.isPrimitive()
    }

    override fun isAMatch(left: Any, right: Any, transformer: BinaryTransformer): Boolean {
        return left == right
    }

    override fun constructLeft2Right(left: Any, transformer: BinaryTransformer): Any {
        return left
    }

    override fun constructRight2Left(right: Any, transformer: BinaryTransformer): Any {
        return right
    }

    override fun updateLeft2Right(left: Any, right: Any, transformer: BinaryTransformer) {
    }

    override fun updateRight2Left(left: Any, right: Any, transformer: BinaryTransformer) {
    }



}
