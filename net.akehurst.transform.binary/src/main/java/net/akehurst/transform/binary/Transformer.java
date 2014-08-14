/*************************************************************************
* Copyright (c) 2013 - 2014 Dr David H. Akehurst.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* and is available at http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* 	Dr. David H. Akehurst
*************************************************************************/
package net.akehurst.transform.binary;

import java.util.List;

public interface Transformer {
	
	Object transformLeft2Right(Object left, Object...constructorArgs);
	List<? extends Object> transformAllLeft2Right(List<? extends Object> leftObjects, Object...constructorArgs);
	
	<L, R> R transformLeft2Right(Class<? extends Relation> ruleClass, L left, Object...constructorArgs);
	<L, R> List<? extends R> transformAllLeft2Right(Class<? extends Relation> ruleClass, List<? extends L> leftObjects, Object...constructorArgs);
	
	Object transformRight2Left(Object right, Object...constructorArgs);
	List<? extends Object> transformAllRight2Left(List<? extends Object> rightObjects, Object...constructorArgs);
	<L, R> L transformRight2Left(Class<? extends Relation> ruleClass, R right, Object...constructorArgs);
	<L, R> List<? extends L> transformAllRight2Left(Class<? extends Relation> ruleClass, List<? extends R> rightObjects, Object...constructorArgs);

	void clear();
	List<Class<? extends Relation>> getRuleTypes();
	void registerRule(Class<? extends Relation> ruleType);

}
