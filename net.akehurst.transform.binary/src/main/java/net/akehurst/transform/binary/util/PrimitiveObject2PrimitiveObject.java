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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import net.akehurst.transform.binary.api.BinaryRule;
import net.akehurst.transform.binary.api.BinaryRuleNotFoundException;
import net.akehurst.transform.binary.api.BinaryTransformer;
import net.akehurst.transform.binary.api.TransformException;

public class PrimitiveObject2PrimitiveObject implements BinaryRule<Object, Object> {

    private static final Set<Class<?>> PRIMITIVES = new HashSet<>(
        Arrays.asList(Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Void.class, String.class));

    private static boolean isPrimitive(final Class<?> clazz) {
        return clazz.isPrimitive() || PrimitiveObject2PrimitiveObject.PRIMITIVES.contains(clazz);
    }

    @Override
    public boolean isValidForLeft2Right(final Object left, final BinaryTransformer transformer) {
        return PrimitiveObject2PrimitiveObject.isPrimitive(left.getClass());
    }

    @Override
    public boolean isValidForRight2Left(final Object right, final BinaryTransformer transformer) {
        return PrimitiveObject2PrimitiveObject.isPrimitive(right.getClass());
    }

    @Override
    public boolean isAMatch(final Object left, final Object right, final BinaryTransformer transformer) throws BinaryRuleNotFoundException {
        return Objects.equals(left, right);
    }

    @Override
    public Object constructLeft2Right(final Object left, final BinaryTransformer transformer) throws TransformException, BinaryRuleNotFoundException {
        return left;
    }

    @Override
    public Object constructRight2Left(final Object right, final BinaryTransformer transformer) throws TransformException, BinaryRuleNotFoundException {
        return right;
    }

    @Override
    public void updateLeft2Right(final Object left, final Object right, final BinaryTransformer transformer)
        throws TransformException, BinaryRuleNotFoundException {}

    @Override
    public void updateRight2Left(final Object left, final Object right, final BinaryTransformer transformer)
        throws TransformException, BinaryRuleNotFoundException {}

}
