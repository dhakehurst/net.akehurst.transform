package net.akehurst.transformation.relations;

import java.lang.reflect.Constructor;

public class RelationNotValidException extends Exception {
	public RelationNotValidException(String message) {
		super(message);
	}
}
