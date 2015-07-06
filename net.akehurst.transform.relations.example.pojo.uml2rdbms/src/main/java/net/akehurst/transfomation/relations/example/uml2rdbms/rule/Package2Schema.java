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

import net.akehurst.transformation.relations.AbstractRelation;
import net.akehurst.transformation.relations.Relation2;
import net.akehurst.transformation.relations.Transformer;
import net.akehurst.transformation.relations.annotations.Check;
import net.akehurst.transformation.relations.annotations.CheckWhere;
import net.akehurst.transformation.relations.annotations.Domain;
import net.akehurst.transformation.relations.annotations.Enforce;
import net.akehurst.transformation.relations.annotations.EnforceWhere;
import net.akehurst.transformation.relations.annotations.When;

/*
 top relation PackageToSchema   // map each package to a schema
 {
 pn: String;
 checkonly domain uml p:Package {name=pn};
 enforce domain rdbms s:Schema {name=pn};
 }
 */
public class Package2Schema extends AbstractRelation implements Relation2<simpleUml.Package, simpleRdbms.Schema> {

	public Package2Schema(Transformer tr) {
		super(tr);
	}

	//region Domain Variables
	simpleUml.Package p;

	@Domain(simpleUml.SimpleUmlFactory.class)
	public simpleUml.Package getP() {
		return this.p;
	}

	@Domain(simpleUml.SimpleUmlFactory.class)
	public void setP(simpleUml.Package value) {
		this.p = value;
	}

	simpleRdbms.Schema s;

	@Domain(simpleRdbms.SimpleRdbmsFactory.class)
	public simpleRdbms.Schema getS() {
		return this.s;
	}

	@Domain(simpleRdbms.SimpleRdbmsFactory.class)
	public void setS(simpleRdbms.Schema value) {
		this.s = value;
	}

	

	@When(simpleUml.SimpleUmlFactory.class)
	public boolean when1() {
		return true;
	}

	@When(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean when2() {
		return true;
	}
	
	
	@Check(simpleUml.SimpleUmlFactory.class)
	public boolean check1() {
		if (null==this.getP()) {
			return null==this.getS();
		}
		String pn = this.getS().getName();

		boolean result = true;

		result = result && this.getP().getName().equals(pn);

		return result;
	}

	@Check(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean check2() {
		if (null==this.getS() ) {
			return null==this.getP();
		}
		String pn = this.getP().getName();

		boolean result = true;

		result = result && this.getS().getName().equals(pn);
		
		return result;
	}

	@CheckWhere(simpleUml.SimpleUmlFactory.class)
	public boolean checkWhere1() {
		return true;
	}

	@CheckWhere(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean checkWhere2() {
		return true;
	}

	@Enforce(simpleRdbms.SimpleRdbmsFactory.class)
	public void enforce1() {
		if (null==this.getS()) {
			this.setS( simpleRdbms.SimpleRdbmsFactory.createSchema() );
		}
		String pn = this.getP().getName();

		this.getS().setName(pn);
	}

	@Enforce(simpleUml.SimpleUmlFactory.class)
	public void enforce2() {
		if (null==this.getP() ) {
			this.setP( simpleUml.SimpleUmlFactory.createPackage() );
		}
		String pn = this.getS().getName();

		this.getP().setName(pn);
		
		return ;
	}

	@EnforceWhere(simpleUml.SimpleUmlFactory.class)
	public void enforceWhere1() {
		return ;
	}

	@EnforceWhere(simpleRdbms.SimpleRdbmsFactory.class)
	public void enforceWhere2() {
		return ;
	}
}
