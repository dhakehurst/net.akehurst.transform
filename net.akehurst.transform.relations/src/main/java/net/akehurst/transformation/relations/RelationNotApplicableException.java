package net.akehurst.transformation.relations;

import java.lang.reflect.Constructor;

public class RelationNotApplicableException extends Exception {
	public RelationNotApplicableException(String message) {
		super(message);
	}
}
