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

package net.akehurst.transform.binary.test.transformer

import net.akehurst.transform.binary.api.BinaryRule
import net.akehurst.transform.binary.api.BinaryRuleNotFoundException
import net.akehurst.transform.binary.api.BinaryTransformer
import net.akehurst.transform.binary.api.TransformException
import net.akehurst.transform.binary.basic.BinaryTransformerAbstract
import net.akehurst.transform.binary.test.model.*

class ABTestTransformer : BinaryTransformerAbstract() {
    init {
        super.registerRule(AElement2BElement::class)
    }
}

class AElement2BElement : BinaryRule<AElement, BElement> {

    override fun isAMatch(left: AElement, right: BElement, transformer: BinaryTransformer): Boolean {
        return left.value == right.value
    }

    override fun isValidForLeft2Right(left: AElement, transformer: BinaryTransformer): Boolean {
        return true
    }

    override fun isValidForRight2Left(right: BElement, transformer: BinaryTransformer): Boolean {
        return true
    }

    override fun constructLeft2Right(left: AElement, transformer: BinaryTransformer): BElement {
        val left_value = left.value
        return BElement(left_value)
    }

    override fun constructRight2Left(right: BElement, transformer: BinaryTransformer): AElement {
        val right_value = right.value
        return AElement(right_value)
    }

    override fun updateLeft2Right(left: AElement, right: BElement, transformer: BinaryTransformer) {
    }

    override fun updateRight2Left(left: AElement, right: BElement, transformer: BinaryTransformer) {
    }

}