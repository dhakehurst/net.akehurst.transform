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

import net.akehurst.transform.binary.api.BinaryTransformer
import net.akehurst.transform.binary.api.BinaryRule


class ListPrimitive2ListPrimitive : BinaryRule<List<Any>, List<Any>> {

    override fun isValidForLeft2Right(left: List<Any>, transformer: BinaryTransformer): Boolean {
        return true
    }

    override fun isValidForRight2Left(right: List<Any>, transformer: BinaryTransformer): Boolean {
        return true
    }

    override fun isAMatch(left: List<Any>, right: List<Any>, transformer: BinaryTransformer): Boolean {
        return transformer.isAllAMatch(Primitive2Primitive::class, left, right)
    }

    override fun constructLeft2Right(left: List<Any>, transformer: BinaryTransformer): List<Any> {
        return transformer.transformAllLeft2Right(Primitive2Primitive::class, left)
    }

    override fun constructRight2Left(right: List<Any>, transformer: BinaryTransformer): List<Any> {
        return transformer.transformAllRight2Left(Primitive2Primitive::class, right)
    }

    override fun updateLeft2Right(left: List<Any>, right: List<Any>, transformer: BinaryTransformer) {
    }

    override fun updateRight2Left(left: List<Any>, right: List<Any>, transformer: BinaryTransformer) {
    }

}
