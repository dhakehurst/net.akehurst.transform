/*************************************************************************
* Copyright (c) 2013 - 2014 Dr David H. Akehurst.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* and is available at http://www.eclipse.org/legal/epl-v10.html
*
* This is based on the original works that contributed to SiTra [http://www.cs.bham.ac.uk/~bxb/Sitra/index.html]
*
* Contributors:
* 	Dr. David H. Akehurst
*   Dr. Behzad Bordbar
*************************************************************************/
package net.akehurst.transform.simple;

import java.util.List;

public interface Transformer {

	
	<S, T> T transform(Class<? extends Relation> ruleType, S source, Object... constructorArgs) throws RelationNotFoundException;
	<S, T> List<? extends T> transformAll(Class<? extends Relation> ruleType, List<? extends S> element, Object... constructorArgs) throws RelationNotFoundException;
	
	Object transform(Object object, Object... constructorArgs) throws RelationNotFoundException;
	List<? extends Object> transformAll(List<? extends Object> sourceObjects, Object... constructorArgs) throws RelationNotFoundException;
	
}
