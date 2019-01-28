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

import net.akehurst.transform.binary.api.BinaryRule;
import net.akehurst.transform.binary.api.BinaryRuleNotFoundException;
import net.akehurst.transform.binary.api.BinaryTransformer;
import net.akehurst.transform.binary.api.TransformException;

public class AElement2BElement implements BinaryRule<AElement, BElement> {

    @Override
    public boolean isAMatch(final AElement left, final BElement right, final BinaryTransformer transformer) {
        return left.getValue() == right.getValue();
    }

    @Override
    public boolean isValidForLeft2Right(final AElement left, final BinaryTransformer transformer) {
        return true;
    }

    @Override
    public boolean isValidForRight2Left(final BElement right, final BinaryTransformer transformer) {
        return true;
    }

    @Override
    public BElement constructLeft2Right(final AElement left, final BinaryTransformer transformer) throws TransformException, BinaryRuleNotFoundException {
        final int left_value = left.getValue();
        final BElement right = new BElement(left_value);
        return right;
    }

    @Override
    public AElement constructRight2Left(final BElement right, final BinaryTransformer transformer) throws TransformException, BinaryRuleNotFoundException {
        final int right_value = right.getValue();
        final AElement left = new AElement(right_value);
        return left;
    }

    @Override
    public void updateLeft2Right(final AElement left, final BElement right, final BinaryTransformer transformer)
            throws TransformException, BinaryRuleNotFoundException {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateRight2Left(final AElement left, final BElement right, final BinaryTransformer transformer)
            throws TransformException, BinaryRuleNotFoundException {
        // TODO Auto-generated method stub

    }

}
