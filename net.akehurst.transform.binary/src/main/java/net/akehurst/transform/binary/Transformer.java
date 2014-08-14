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
	
	<L, R, OL extends L, OR extends R> OR transformLeft2Right(Class<? extends Relation<L,R>> ruleClass, OL left, Object...constructorArgs)throws RelationNotFoundException;
	<L, R> List<? extends R> transformAllLeft2Right(Class<? extends Relation<L,R>> ruleClass, List<? extends L> leftObjects, Object...constructorArgs)throws RelationNotFoundException;
	
	<L, R> L transformRight2Left(Class<? extends Relation<L,R>> ruleClass, R right, Object...constructorArgs) throws RelationNotFoundException;
	<L, R> List<? extends L> transformAllRight2Left(Class<? extends Relation<L,R>> ruleClass, List<? extends R> rightObjects, Object...constructorArgs)throws RelationNotFoundException;

	void clear();
	List<Class<? extends Relation<?,?>>> getRuleTypes();
	void registerRule(Class<? extends Relation<?,?>> ruleType);

}
