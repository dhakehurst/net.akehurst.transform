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
package net.akehurst.transfomation.relations.example.pojo.uml2rdbms.check;

import java.util.Arrays;
import java.util.List;

import net.akehurst.transfomation.relations.example.pojo.uml2rdbms.AbstractTransformTest;
import net.akehurst.transfomation.relations.example.uml2rdbms.Uml2Rdbms;

import org.junit.Test;

public class Uml2Rdbms_Class2Table_Test extends AbstractTransformTest {

	@Test
	public void check_uml__class_table_true() {
		simpleUml.Package p = simpleUml.SimpleUmlFactory.createPackage();
		p.setName("addressBook");
		simpleUml.Class c1 = simpleUml.SimpleUmlFactory.createClass();
		c1.setName("Contact");
		c1.setKind("persistent");
		p.getElements().add(c1);

		simpleRdbms.Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("addressBook");
		simpleRdbms.Table t1 = simpleRdbms.SimpleRdbmsFactory.createTable();
		t1.setName("Contact");
		s.getTables().add(t1);

		List<? extends Object> uml = Arrays.asList(p, c1);
		List<? extends Object> rdbms = Arrays.asList(s, t1);


		this.testCheck(Uml2Rdbms.umlDomainId, uml, rdbms, true);
		
	}

	@Test
	public void check_rdbms__class_table_true() {
		simpleUml.Package p = simpleUml.SimpleUmlFactory.createPackage();
		p.setName("addressBook");
		simpleUml.Class c1 = simpleUml.SimpleUmlFactory.createClass();
		c1.setName("Contact");
		c1.setKind("persistent");
		c1.setNamespace(p);
		p.getElements().add(c1);

		simpleRdbms.Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("addressBook");
		simpleRdbms.Table t1 = simpleRdbms.SimpleRdbmsFactory.createTable();
		t1.setSchema(s);
		t1.setName("Contact");
		s.getTables().add(t1);

		simpleRdbms.Column col1 = simpleRdbms.SimpleRdbmsFactory.createColumn();
		col1.setName(t1.getName() + "_tid");
		col1.setType("NUMBER");
		t1.getColumn().add(col1);

		List<? extends Object> uml = Arrays.asList(p, c1);
		List<? extends Object> rdbms = Arrays.asList(s, t1);

		this.testCheck(Uml2Rdbms.rdbmsDomainId, uml, rdbms, true);
	}

	@Test
	public void check_rdbms__class_table_false() {
		simpleUml.Package p = simpleUml.SimpleUmlFactory.createPackage();
		p.setName("addressBook");
		simpleUml.Class c1 = simpleUml.SimpleUmlFactory.createClass();
		c1.setName("Contact");
		c1.setKind("persistent");
		c1.setNamespace(p);
		p.getElements().add(c1);

		simpleRdbms.Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("addressBook");
		simpleRdbms.Table t1 = simpleRdbms.SimpleRdbmsFactory.createTable();
		t1.setSchema(s);
		t1.setName("Contact");
		s.getTables().add(t1);

		// simpleRdbms.Column col1 =
		// simpleRdbms.SimpleRdbmsFactory.createColumn();
		// col1.setName(t1.getName()+"_tid");
		// col1.setType("NUMBER");
		// t1.getColumn().add(col1);

		List<? extends Object> uml = Arrays.asList(p, c1);
		List<? extends Object> rdbms = Arrays.asList(s, t1);

		this.testCheck(Uml2Rdbms.rdbmsDomainId, uml, rdbms, false);
	}
}
