package net.akehurst.transformation.relations.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.akehurst.transformation.relations.DomainModelItentifier;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Enforce {
	Class<?> value();
}
