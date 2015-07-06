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

import net.akehurst.transfomation.relations.example.uml2rdbms.Uml2Rdbms;
import net.akehurst.transformation.relations.DomainModelItentifier;
import net.akehurst.transformation.relations.RelationNotValidException;
import net.akehurst.transformation.relations.Transformer;
import net.akehurst.transformation.relations.annotations.Check;
import net.akehurst.transformation.relations.annotations.CheckWhere;
import net.akehurst.transformation.relations.annotations.Domain;
import net.akehurst.transformation.relations.annotations.When;


/**
 * 
 * @author akehurst
 *
 * <code>
 * relation Attribute2Column {
 * 
 *   domain uml a:Attribute {
 *     name = an
 *     type = umlType:PrimitiveDataType { name=umlTypeName }
 *   }
 *   
 *   domain rdbms c:Column {
 *   	name = cn
 *   	type = sqlTypeName
 *   }
 *   
 *   domain x prefix:String {}
 *   
 *   where {
 *     cn <-> if (''==prefix) then an else prefix+'_'+an endif
 *     PrimitiveType2SqlType(umlTypeName, sqlTypeName)
 *   }
 *   
 * 
 * }
 * </code>
 *
 */
public class Attribute2Column extends AbstractUml2RdbmsRelation {

	public static DomainModelItentifier prefixDomainId = new DomainModelItentifier(String.class);

	public Attribute2Column(Transformer transformer) {
		super(transformer);
	}

	//#region domain uml a:Attribute
	simpleUml.Attribute a;

	@Domain(simpleUml.SimpleUmlFactory.class)
	public simpleUml.Attribute getA() {
		return this.a;
	}

	@Domain(simpleUml.SimpleUmlFactory.class)
	public void setA(simpleUml.Attribute value) {
		this.a = value;
	}

	//#region domain rdbms c:Column
	simpleRdbms.Column c;

	@Domain(simpleRdbms.SimpleRdbmsFactory.class)
	public simpleRdbms.Column getC() {
		return this.c;
	}

	@Domain(simpleRdbms.SimpleRdbmsFactory.class)
	public void setC(simpleRdbms.Column value) {
		this.c = value;
	}
	
	//#region domain x prefix:String {}
	String prefix;

	@Domain(String.class)
	public String getPrefix() {
		return this.prefix;
	}

	@Domain(String.class)
	public void setPrefix(String value) {
		this.prefix = value;
	}

	//#region when target==uml
	@When(simpleUml.SimpleUmlFactory.class)
	public boolean when_uml() {
		boolean result = true;
		return result;
	}
	
	//#region when target==rdbms
	@When(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean when_rdbms() {
		boolean result = true;
		return result;
	}
	
	//#region when target==x
	@When(String.class)
	public boolean when_x() {
		boolean result = true;
		return result;
	}
	
	/**
	 * <code>
	 *  where {
	 *     cd <-> if (''==prefix) then an else prefix+'_'+an endif
	 *     PrimitiveType2SqlType(umlTypeName, sqlTypeName)
	 *   }
	 * </code>   
	 * @return
	 * @throws RelationNotValidException
	 */
	@CheckWhere(simpleUml.SimpleUmlFactory.class)
	public boolean checkWhere_uml() throws RelationNotValidException {
		boolean result = true;
		
		if ("".equals(this.getPrefix())) {
			result &= this.getCn_uml().equals( this.getAn_uml() );
		} else {
			result &= this.getCn_uml().equals( this.getPrefix()+"_"+this.getAn_uml() );
		}
		result &= this.checkMatch(Uml2Rdbms.umlDomainId, PrimitiveType2SqlType.class, getUmlTypeName_uml(), getSqlTypeName_uml());
		
		return result;		
	}
	
	@CheckWhere(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean checkWhere_rdbms() throws RelationNotValidException {
		boolean result = true;
		
		if ("".equals(this.getPrefix())) {
			result &= this.getCn_uml().equals( this.getAn_uml() );
		} else {
			result &= this.getCn_uml().equals( this.getPrefix()+"_"+this.getAn_uml() );
		}
		result &= this.checkMatch(Uml2Rdbms.rdbmsDomainId, PrimitiveType2SqlType.class, getUmlTypeName_uml(), getSqlTypeName_uml());
		
		return result;		
	}
	
	@Check(simpleUml.SimpleUmlFactory.class)
	public boolean check_uml() throws RelationNotValidException {
		boolean result = true;
		
		return result;		
	}
	
	@Check(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean check_rdbms() throws RelationNotValidException {
		boolean result = true;
		
		return result;		
	}
	
	//#region variables
	String getSqlTypeName_uml() {
		return this.getC().getType();
	}
	
	String getUmlTypeName_uml() {
		return this.getA().getType().getName();
	}
	
	String getAn_uml() {
		return this.getA().getName();
	}
	
	String getCn_uml() {
		return this.getC().getName();
	}
}
