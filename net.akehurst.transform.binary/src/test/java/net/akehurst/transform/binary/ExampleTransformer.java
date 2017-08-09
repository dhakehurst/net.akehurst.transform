package net.akehurst.transform.binary;

public class ExampleTransformer extends BinaryTransformer {

	public ExampleTransformer() {
		super.registerRule(AElement2BElement.class);
	}
}
