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
import net.akehurst.transformation.relations.Relation;
import net.akehurst.transformation.relations.RelationNotApplicableException;
import net.akehurst.transformation.relations.RelationNotValidException;
import net.akehurst.transformation.relations.Transformer;
import net.akehurst.transformation.relations.annotations.Check;
import net.akehurst.transformation.relations.annotations.CheckWhere;
import net.akehurst.transformation.relations.annotations.Domain;
import net.akehurst.transformation.relations.annotations.When;
import simpleUml.PrimitiveDataType;

/*
relation PrimitiveAttributeToColumn
{
   an, pn, cn, sqltype: String;
   
   checkonly domain uml c:Class {
		attribute=a:Attribute {
			name=an,
            type=p:PrimitiveDataType { name=pn }
		}
   };
   enforce domain rdbms t:Table {
   		column=cl:Column {
   			name=cn,
            type=sqltype
        }
   };
   primitive domain prefix:String;
   
   where {
      cn = if (prefix = '') then an else prefix+'_'+an endif;
      sqltype = PrimitiveTypeToSqlType(pn);
   }
}
 */
public class PrimitiveAttribute2Column extends AbstractUml2RdbmsRelation {
	
	public PrimitiveAttribute2Column(Transformer transformer) {
		super(transformer);
	}

	simpleUml.Class c;

	@Domain(simpleUml.SimpleUmlFactory.class)
	public simpleUml.Class getC() {
		return this.c;
	}

	@Domain(simpleUml.SimpleUmlFactory.class)
	public void setC(simpleUml.Class value) {
		this.c = value;
	}

	simpleRdbms.Table t;

	@Domain(simpleRdbms.SimpleRdbmsFactory.class)
	public simpleRdbms.Table getT() {
		return this.t;
	}

	@Domain(simpleRdbms.SimpleRdbmsFactory.class)
	public void setT(simpleRdbms.Table value) {
		this.t = value;
	}
	
	String prefix;

	@Domain(String.class)
	public String getPrefix() {
		return this.prefix;
	}

	@Domain(String.class)
	public void setPrefix(String value) {
		this.prefix = value;
	}

	
	String an; String pn; String cn; String sqlType;
	
	
	@When(simpleUml.SimpleUmlFactory.class)
	public boolean when1() {
		boolean result = true;
		return result;
	}

	@When(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean when2() {
		boolean result = true;
		return result;
	}

	@Check(simpleUml.SimpleUmlFactory.class)
	public boolean check_uml() {
		boolean result = true;
		
		simpleRdbms.Table t = this.getT();
		result &= t.getColumn().stream().anyMatch(c ->
			c.getName().equals(cn) && c.getType().equals(sqlType)
		);
		
		return result;
	}

	@Check(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean check_rdbms() {
		boolean result = true;
		
		simpleUml.Class c = this.getC();
		result &= c.getAttribute().stream().anyMatch(a ->
			a.getName().equals(an) && (a.getType() instanceof PrimitiveDataType) && a.getName().equals(pn)
		);
		
		
		return result;
	}

	@CheckWhere(simpleUml.SimpleUmlFactory.class)
	public boolean checkWhere_uml() {
	    //  cn = if (prefix = '') then an else prefix+'_'+an endif;
	    //  sqltype = PrimitiveTypeToSqlType(pn);
		this.cn = "".equals(this.getPrefix()) ? this.an : this.getPrefix()+"_"+an;
		try {
			Relation r = super.findRelation(Uml2Rdbms.rdbmsDomainId, PrimitiveType2SqlType.class, this.pn, this.sqlType);
			
		} catch (RelationNotValidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RelationNotApplicableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return true;
	}

	@CheckWhere(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean checkWhere_rdbms() {

		this.cn = "".equals(this.getPrefix()) ? this.an : this.getPrefix()+"_"+an;

		try {
			Relation r = super.findRelation(Uml2Rdbms.rdbmsDomainId, PrimitiveType2SqlType.class, this.pn, this.sqlType);
			
		} catch (RelationNotValidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RelationNotApplicableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

}