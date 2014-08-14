/*************************************************************************
* Copyright (c) 2013 - 2014 Dr David H. Akehurst.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* and is available at http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* 	Dr. David H. Akehurst
*************************************************************************/
package net.akehurst.transform.example.pojo.simpleRdbms;

public class Table {

	String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	java.util.Set<Column> pkey = new java.util.HashSet<Column>();
	public java.util.Set<Column> getPkey() {
		return pkey;
	}
	public void setPkey(java.util.Set<Column> pkey) {
		this.pkey = pkey;
	}

	java.util.Set<Column> column = new java.util.HashSet<Column>();
	public java.util.Set<Column> getColumn() {
		return column;
	}
	public void setColumn(java.util.Set<Column> column) {
		this.column = column;
	}

	java.util.Set<FKey> fkeys = new java.util.HashSet<FKey>();
	public java.util.Set<FKey> getFkeys() {
		return fkeys;
	}
	public void setFkeys(java.util.Set<FKey> fkeys) {
		this.fkeys = fkeys;
	}

}
