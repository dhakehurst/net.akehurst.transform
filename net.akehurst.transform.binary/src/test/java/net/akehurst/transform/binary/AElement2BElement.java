package net.akehurst.transform.binary;

public class AElement2BElement implements IBinaryRule<AElement, BElement> {

	@Override
	public boolean isAMatch(final AElement left, final BElement right, final ITransformer transformer) {
		return left.getValue() == right.getValue();
	}

	@Override
	public boolean isValidForLeft2Right(final AElement left) {
		return true;
	}

	@Override
	public boolean isValidForRight2Left(final BElement right) {
		return true;
	}

	@Override
	public BElement constructLeft2Right(final AElement left, final ITransformer transformer) throws TransformException, RuleNotFoundException {
		final int left_value = left.getValue();
		final BElement right = new BElement(left_value);
		return right;
	}

	@Override
	public AElement constructRight2Left(final BElement right, final ITransformer transformer) throws TransformException, RuleNotFoundException {
		final int right_value = right.getValue();
		final AElement left = new AElement(right_value);
		return left;
	}

	@Override
	public void updateLeft2Right(final AElement left, final BElement right, final ITransformer transformer) throws TransformException, RuleNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRight2Left(final AElement left, final BElement right, final ITransformer transformer) throws TransformException, RuleNotFoundException {
		// TODO Auto-generated method stub

	}

}
