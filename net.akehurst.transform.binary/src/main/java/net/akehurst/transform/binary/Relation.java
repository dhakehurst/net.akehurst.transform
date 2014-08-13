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

public interface Relation<L, R> {
	
	boolean isValidForLeft2Right(L left);
	boolean isValidForRight2Left(R right);

	R constructLeft2Right(L left, Transformer transformer);
	L constructRight2Left(R right, Transformer transformer);

	R configureLeft2Right(L left, R right, Transformer transformer);
	L configureRight2Left(L left, R right, Transformer transformer);
}
