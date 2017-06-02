package net.akehurst.transform.binary;

public class ExampleTransformer extends AbstractTransformer {

	public ExampleTransformer() {
		super.registerRule(AElement2BElement.class);
	}
}
