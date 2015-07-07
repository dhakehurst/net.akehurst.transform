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

import java.util.Arrays;

import net.akehurst.transfomation.relations.example.uml2rdbms.Uml2Rdbms;
import net.akehurst.transformation.relations.RelationNotApplicableException;
import net.akehurst.transformation.relations.RelationNotValidException;
import net.akehurst.transformation.relations.Transformer;
import net.akehurst.transformation.relations.annotations.Check;
import net.akehurst.transformation.relations.annotations.CheckWhere;
import net.akehurst.transformation.relations.annotations.Domain;
import net.akehurst.transformation.relations.annotations.Enforce;
import net.akehurst.transformation.relations.annotations.EnforceWhere;
import net.akehurst.transformation.relations.annotations.When;

/*
 // map each persistent class to a table

 top relation ClassToTable   // map each persistent class to a table
 {
	cn, prefix: String;
	
	checkonly domain uml c:Class {
	 	namespace=p:Package {},
	 	kind='Persistent', name=cn
	};

 	enforce domain rdbms t:Table {
		 schema=s:Schema {}, name=cn,
		 column=cl:Column {name=cn+'_tid', type='NUMBER'},
		 key=k:Key {name=cn+'_pk', column=cl}
	};

 	when {
 		PackageToSchema(p, s);
 	}

 	where {
 		prefix = '';
 		AttributeToColumn(c, t, prefix);
 	}
 }
 */
public class Class2Table extends AbstractUml2RdbmsRelation {

	public Class2Table(Transformer tr) {
		super(tr);
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

	@When(simpleUml.SimpleUmlFactory.class)
	public boolean when1() {
		boolean result = true;

		simpleUml.Package p = c.getNamespace();

		try {
			Package2Schema r = this.findMatch(Uml2Rdbms.rdbmsDomainId, Package2Schema.class, Arrays.asList(p), Arrays.asList(t.getSchema()));
			result = result && null != r.getS();
		} catch (RelationNotValidException ex) {
			return false;
		} catch (RelationNotApplicableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@When(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean when2() {
		boolean result = true;

		simpleRdbms.Schema s = t.getSchema();

		try {
			Package2Schema r = this.findMatch(Uml2Rdbms.umlDomainId, Package2Schema.class, Arrays.asList(c.getNamespace()), Arrays.asList(s));
			result = result && null != r.getP();
		} catch (RelationNotValidException ex) {
			return false;
		} catch (RelationNotApplicableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Check(simpleUml.SimpleUmlFactory.class)
	public boolean check1() {

		String cn = t.getName();

		boolean result = true;

		result = result && c.getKind().equals("persistent");
		result = result && c.getName().equals(cn);

		return result;

	}

	@Check(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean check2() {

		String cn = c.getName();

		boolean result = true;

		result = result && t.getName().equals(cn);
		boolean exists = false;
		for (simpleRdbms.Column cl : t.getColumn()) {
			boolean v = true;
			v = v && (cn + "_tid").equals(cl.getName());
			v = v && "NUMBER".equals(cl.getType());
			if (v) {
				exists = true;
				break;
			}
		}
		result = result && exists;
		return result;

	}

	@CheckWhere(simpleUml.SimpleUmlFactory.class)
	public boolean checkWhere_uml() throws RelationNotValidException {
		boolean result = true;

		result &= this.checkMatch(Uml2Rdbms.umlDomainId, Attribute2Column.class, this.getC().getAttribute(), this.getT().getColumn());

		return result;
	}

	@CheckWhere(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean checkWhere_rdbms() throws RelationNotValidException {
		boolean result = true;

		result &= this.checkMatch(Uml2Rdbms.rdbmsDomainId, Attribute2Column.class, this.getC().getAttribute(), this.getT().getColumn());

		return result;
	}

	@Enforce(simpleRdbms.SimpleRdbmsFactory.class)
	public void enforce1() {
		return;
	}

	@Enforce(simpleUml.SimpleUmlFactory.class)
	public void enforce2() {
		return;
	}

	@EnforceWhere(simpleUml.SimpleUmlFactory.class)
	public void enforceWhere1() {
		return;
	}

	@EnforceWhere(simpleRdbms.SimpleRdbmsFactory.class)
	public void enforceWhere2() {
		return;
	}
}
