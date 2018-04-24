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

package net.akehurst.transform.binary.util;

import java.util.List;

import net.akehurst.transform.binary.api.BinaryRule;
import net.akehurst.transform.binary.api.BinaryRuleNotFoundException;
import net.akehurst.transform.binary.api.BinaryTransformer;
import net.akehurst.transform.binary.api.TransformException;

public class ListPrimitiveObject2ListPrimitiveObject implements BinaryRule<List<Object>, List<Object>> {

    @Override
    public boolean isValidForLeft2Right(final List<Object> left) {
        return true;
    }

    @Override
    public boolean isValidForRight2Left(final List<Object> right) {
        return true;
    }

    @Override
    public boolean isAMatch(final List<Object> left, final List<Object> right, final BinaryTransformer transformer) throws BinaryRuleNotFoundException {
        final boolean res = transformer.isAllAMatch(PrimitiveObject2PrimitiveObject.class, left, right);
        return res;
    }

    @Override
    public List<Object> constructLeft2Right(final List<Object> left, final BinaryTransformer transformer)
        throws TransformException, BinaryRuleNotFoundException {
        final List<? extends Object> right = transformer.transformAllLeft2Right(PrimitiveObject2PrimitiveObject.class, left);
        return (List<Object>) right;
    }

    @Override
    public List<Object> constructRight2Left(final List<Object> right, final BinaryTransformer transformer)
        throws TransformException, BinaryRuleNotFoundException {
        final List<? extends Object> left = transformer.transformAllRight2Left(PrimitiveObject2PrimitiveObject.class, right);
        return (List<Object>) left;
    }

    @Override
    public void updateLeft2Right(final List<Object> left, final List<Object> right, final BinaryTransformer transformer)
        throws TransformException, BinaryRuleNotFoundException {}

    @Override
    public void updateRight2Left(final List<Object> left, final List<Object> right, final BinaryTransformer transformer)
        throws TransformException, BinaryRuleNotFoundException {}

}
