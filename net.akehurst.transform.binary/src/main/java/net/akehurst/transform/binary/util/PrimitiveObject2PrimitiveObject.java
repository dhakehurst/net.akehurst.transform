package net.akehurst.transform.binary.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import net.akehurst.transform.binary.IBinaryRule;
import net.akehurst.transform.binary.ITransformer;
import net.akehurst.transform.binary.RuleNotFoundException;
import net.akehurst.transform.binary.TransformException;

public class PrimitiveObject2PrimitiveObject implements IBinaryRule<Object, Object> {

	private static final Set<Class<?>> PRIMITIVES = new HashSet<>(Arrays.asList(Boolean.class, Character.class, Byte.class, Short.class, Integer.class,
			Long.class, Float.class, Double.class, Void.class, String.class));

	private static boolean isPrimitive(final Class<?> clazz) {
		return clazz.isPrimitive() || PrimitiveObject2PrimitiveObject.PRIMITIVES.contains(clazz);
	}

	@Override
	public boolean isValidForLeft2Right(final Object left) {
		return PrimitiveObject2PrimitiveObject.isPrimitive(left.getClass());
	}

	@Override
	public boolean isValidForRight2Left(final Object right) {
		return PrimitiveObject2PrimitiveObject.isPrimitive(right.getClass());
	}

	@Override
	public boolean isAMatch(final Object left, final Object right, final ITransformer transformer) throws RuleNotFoundException {
		return Objects.equals(left, right);
	}

	@Override
	public Object constructLeft2Right(final Object left, final ITransformer transformer) throws TransformException, RuleNotFoundException {
		return left;
	}

	@Override
	public Object constructRight2Left(final Object right, final ITransformer transformer) throws TransformException, RuleNotFoundException {
		return right;
	}

	@Override
	public void updateLeft2Right(final Object left, final Object right, final ITransformer transformer) throws TransformException, RuleNotFoundException {
	}

	@Override
	public void updateRight2Left(final Object left, final Object right, final ITransformer transformer) throws TransformException, RuleNotFoundException {
	}

}
