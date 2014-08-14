/*************************************************************************
* Copyright (c) 2013 - 2014 Dr David H. Akehurst.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* and is available at http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* 	Dr. David H. Akehurst
*************************************************************************/
package net.akehurst.transform.example.simpleRdbms.pojo;

public class FKey {

	Table tableReference;
	public Table getTableReference() {
		return tableReference;
	}
	public void setTableReference(Table tableReference) {
		this.tableReference = tableReference;
	}

	java.util.Set<Column> column = new java.util.HashSet<Column>();
	public java.util.Set<Column> getColumn() {
		return column;
	}
	public void setColumn(java.util.Set<Column> column) {
		this.column = column;
	}
	
}
