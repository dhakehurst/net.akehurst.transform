package net.akehurst.transform.binary.util;

import java.util.List;

import net.akehurst.transform.binary.IBinaryRule;
import net.akehurst.transform.binary.ITransformer;
import net.akehurst.transform.binary.RuleNotFoundException;
import net.akehurst.transform.binary.TransformException;

public class ListPrimitiveObject2ListPrimitiveObject implements IBinaryRule<List<Object>, List<Object>> {

	@Override
	public boolean isValidForLeft2Right(final List<Object> left) {
		return true;
	}

	@Override
	public boolean isValidForRight2Left(final List<Object> right) {
		return true;
	}

	@Override
	public boolean isAMatch(final List<Object> left, final List<Object> right, final ITransformer transformer) throws RuleNotFoundException {
		final boolean res = transformer.isAllAMatch(PrimitiveObject2PrimitiveObject.class, left, right);
		return res;
	}

	@Override
	public List<Object> constructLeft2Right(final List<Object> left, final ITransformer transformer) throws TransformException, RuleNotFoundException {
		final List<? extends Object> right = transformer.transformAllLeft2Right(PrimitiveObject2PrimitiveObject.class, left);
		return (List<Object>) right;
	}

	@Override
	public List<Object> constructRight2Left(final List<Object> right, final ITransformer transformer) throws TransformException, RuleNotFoundException {
		final List<? extends Object> left = transformer.transformAllRight2Left(PrimitiveObject2PrimitiveObject.class, right);
		return (List<Object>) left;
	}

	@Override
	public void updateLeft2Right(final List<Object> left, final List<Object> right, final ITransformer transformer)
			throws TransformException, RuleNotFoundException {
	}

	@Override
	public void updateRight2Left(final List<Object> left, final List<Object> right, final ITransformer transformer)
			throws TransformException, RuleNotFoundException {
	}

}
