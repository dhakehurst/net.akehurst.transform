/*************************************************************************
* Copyright (c) 2013 - 2014 Dr David H. Akehurst.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* and is available at http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* 	Dr. David H. Akehurst
*************************************************************************/
package simpleRdbms;

public class Schema {

	String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	java.util.Set<Table> tables = new java.util.HashSet<Table>();
	public java.util.Set<Table> getTables() {
		return tables;
	}
	public void setTables(java.util.Set<Table> value) {
		this.tables = value;
	}
	
	@Override
	public String toString() {
		return "Schema { name='"+this.getName()+"' }";
	}

}
