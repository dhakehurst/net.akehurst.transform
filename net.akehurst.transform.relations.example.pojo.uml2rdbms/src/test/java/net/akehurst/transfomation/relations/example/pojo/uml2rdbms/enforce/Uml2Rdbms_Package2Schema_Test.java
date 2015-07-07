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
package net.akehurst.transfomation.relations.example.pojo.uml2rdbms.enforce;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.akehurst.transfomation.relations.example.pojo.uml2rdbms.AbstractTransformTest;
import net.akehurst.transfomation.relations.example.uml2rdbms.Uml2Rdbms;
import net.akehurst.transformation.relations.DomainModelItentifier;

import org.junit.Test;

import simpleRdbms.Schema;
import simpleUml.Package;

public class Uml2Rdbms_Package2Schema_Test extends AbstractTransformTest {

	@Test
	public void enforce_rdbms__null_null() {
		Package p = null;
		Schema s = null;

		List<Package> uml = Arrays.asList(p);
		List<Schema> rdbms = Arrays.asList(s);

		testEnforce(Uml2Rdbms.rdbmsDomainId, uml, rdbms, true, true);
				
	}

	@Test
	public void enforce_uml__null_null() {
		Package p = null;
		Schema s = null;

		List<Package> uml = Arrays.asList(p);
		List<Schema> rdbms = Arrays.asList(s);

		testEnforce(Uml2Rdbms.umlDomainId, uml, rdbms, true, true);
		
	}

	@Test
	public void enforce_rdbms__empty_empty() {

		List<Package> uml = Arrays.asList();
		List<Schema> rdbms = Arrays.asList();

		testEnforce(Uml2Rdbms.rdbmsDomainId, uml, rdbms, true, true);

	}

	@Test
	public void enforce_uml__empty_empty() {

		List<Package> uml = Arrays.asList();
		List<Schema> rdbms = Arrays.asList();

		Map<DomainModelItentifier, Iterable<?>> domains = new HashMap<DomainModelItentifier, Iterable<?>>();
		domains.put(Uml2Rdbms.umlDomainId, uml);
		domains.put(Uml2Rdbms.rdbmsDomainId, rdbms);

		testEnforce(Uml2Rdbms.umlDomainId, uml, rdbms, true, true);

	}

	@Test
	public void enforce_rdbms__package() {
		Package p = simpleUml.SimpleUmlFactory.createPackage();
		p.setName("addressBook");

		List<Package> uml = Arrays.asList(p);
		List<Schema> rdbms = Arrays.asList();

		testEnforce(Uml2Rdbms.rdbmsDomainId, uml, rdbms, false, true);

	}

	@Test
	public void enforce_uml__schema() {

		Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("addressBook");

		List<Package> uml = Arrays.asList();
		List<Schema> rdbms = Arrays.asList(s);

		testEnforce(Uml2Rdbms.umlDomainId, uml, rdbms, false, true);

	}

	@Test
	public void enforce_uml__package_schema_different() {
		Package p = simpleUml.SimpleUmlFactory.createPackage();
		p.setName("addressBook");

		Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("library");

		List<Package> uml = Arrays.asList(p);
		List<Schema> rdbms = Arrays.asList(s);

		testEnforce(Uml2Rdbms.umlDomainId, uml, rdbms, false, true);

	}

	@Test
	public void enfore_rdbms__package_schema_different() {
		Package p = simpleUml.SimpleUmlFactory.createPackage();
		p.setName("addressBook");

		Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("library");

		List<Package> uml = Arrays.asList(p);
		List<Schema> rdbms = Arrays.asList(s);

		testEnforce(Uml2Rdbms.rdbmsDomainId, uml, rdbms, false, true);

	}


}
