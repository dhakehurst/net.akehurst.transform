package net.akehurst.transform.example.simpleUml2simpleRdbms.pojo;

import net.akehurst.transform.binary.AbstractTransformer;

public class SimpleUml2SimpleRdbmsTransformer extends AbstractTransformer {

	public SimpleUml2SimpleRdbmsTransformer() {
		super.registerRule(Package2Schema.class);
	}
	
}
