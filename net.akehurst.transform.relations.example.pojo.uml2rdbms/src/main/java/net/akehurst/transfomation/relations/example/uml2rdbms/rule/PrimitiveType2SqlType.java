/*
 * Copyright (c) 2015 D. David H. Akehurst
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.akehurst.transfomation.relations.example.uml2rdbms.rule;

import java.util.HashMap;
import java.util.Map;

import net.akehurst.transformation.relations.AbstractRelation;
import net.akehurst.transformation.relations.Transformer;
import net.akehurst.transformation.relations.annotations.Check;
import net.akehurst.transformation.relations.annotations.CheckWhere;
import net.akehurst.transformation.relations.annotations.Domain;
import net.akehurst.transformation.relations.annotations.When;

/*
function PrimitiveTypeToSqlType(primitiveTpe:String):String
{
   if (primitiveType='INTEGER')
   then 'NUMBER'
   else if (primitiveType='BOOLEAN')
      then 'BOOLEAN'
      else 'VARCHAR'
      endif
	  endif;
}
*/
public class PrimitiveType2SqlType extends AbstractRelation {

	public PrimitiveType2SqlType(Transformer t) {
		super(t);
		this.uml2sql = new HashMap<String, String>();
		this.sql2uml = new HashMap<String, String>();
		this.uml2sql.put("Integer", "NUMBER");
		this.uml2sql.put("String", "VARCHAR");
		this.uml2sql.put("Boolean", "BOOLEAN");
		this.sql2uml.put("NUMBER", "Integer");
		this.sql2uml.put("VARCHAR", "String");
		this.sql2uml.put("BOOLEAN", "Boolean");
	}

	Map<String,String> sql2uml;
	Map<String,String> uml2sql;
	
	String umlType;

	@Domain(simpleUml.SimpleUmlFactory.class)
	public String getUmlType() {
		return this.umlType;
	}

	@Domain(simpleUml.SimpleUmlFactory.class)
	public void setUmlType(String value) {
		this.umlType = value;
	}

	String sqlType;

	@Domain(simpleRdbms.SimpleRdbmsFactory.class)
	public String getSqlType() {
		return this.sqlType;
	}

	@Domain(simpleRdbms.SimpleRdbmsFactory.class)
	public void setSqlType(String value) {
		this.sqlType = value;
	}

	@When(simpleUml.SimpleUmlFactory.class)
	public boolean whenUml() {
		return true;
	}
	
	@When(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean whenRdbms() {
		return true;
	}

	@CheckWhere(simpleUml.SimpleUmlFactory.class)
	public boolean checkWhereUml() {
		return true;
	}
	
	@CheckWhere(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean checkWhereRdbms() {
		return true;
	}
	
	@Check(simpleUml.SimpleUmlFactory.class)
	public boolean checkUml() {
		return this.getSqlType().equals( this.uml2sql.get(this.getUmlType()));
	}
	
	@Check(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean checkRdbms() {
		return this.getUmlType().equals( this.sql2uml.get(this.getSqlType()) );
	}
}
