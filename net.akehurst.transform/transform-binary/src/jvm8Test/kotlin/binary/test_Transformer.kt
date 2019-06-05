/**
 * Copyright (C) 2019 Dr. David H. Akehurst (http://dr.david.h.akehurst.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.akehurst.transform.binary.test

import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameter
import org.junit.runners.Parameterized.Parameters
import net.akehurst.transform.binary.api.TransformException
import net.akehurst.transform.binary.test.model.AElement
import net.akehurst.transform.binary.test.model.BElement
import net.akehurst.transform.binary.test.transformer.ABTestTransformer
import net.akehurst.transform.binary.test.transformer.AElement2BElement
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class test_Transformer(val data:Data) {

    companion object {

        data class Data(
                val left:Any,
                val right:Any
        )

        @JvmStatic
        @Parameters(name = "{0}")
        fun data(): List<Data> {
            return listOf(
                    Data( AElement(1), BElement(1) )

            )
        }

    }

    @Test
    fun isAMatch() {
        val sut = ABTestTransformer()

        val actual = sut.isAMatch(AElement2BElement::class, this.data.left as AElement, this.data.right as BElement)

        assertEquals(true, actual)
    }

    @Test
    fun transformLeft2Right() {
        val sut = ABTestTransformer()

        val actual = sut.transformLeft2Right(AElement2BElement::class, this.data.left as AElement)

        assertEquals(this.data.right as BElement, actual)
    }


    @Test
    fun transformRight2Left() {
        val sut = ABTestTransformer()

        val actual = sut.transformRight2Left(AElement2BElement::class, this.data.right as BElement)

        assertEquals(this.data.left as AElement, actual)
    }
}
