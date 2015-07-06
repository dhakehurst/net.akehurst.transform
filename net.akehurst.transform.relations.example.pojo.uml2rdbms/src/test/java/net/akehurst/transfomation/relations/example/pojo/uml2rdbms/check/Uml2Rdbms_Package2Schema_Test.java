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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.akehurst.transfomation.relations.example.pojo.uml2rdbms.AbstractTransformTest;
import net.akehurst.transfomation.relations.example.uml2rdbms.Uml2Rdbms;
import net.akehurst.transformation.relations.DomainModelItentifier;
import net.akehurst.transformation.relations.RelationNotValidException;
import net.akehurst.transformation.relations.Transformer;

import org.junit.Assert;
import org.junit.Test;

import simpleRdbms.Schema;
import simpleUml.Package;

public class Uml2Rdbms_Package2Schema_Test extends AbstractTransformTest {
	
	@Test
	public void check_uml__null_null() {
		Package p = null; // simpleUml.SimpleUmlFactory.getSimpleUmlPackage().getPackage().createPackage();
		Schema s = null;

		List<Package> uml = Arrays.asList(p);
		List<Schema> rdbms = Arrays.asList(s);

		this.testCheck(Uml2Rdbms.umlDomainId, uml, rdbms, true);
	
	}

	@Test
	public void check_rdbms__null_null() {
		Package p = null; // simpleUml.SimpleUmlFactory.getSimpleUmlPackage().getPackage().createPackage();
		Schema s = null;

		List<Package> uml = Arrays.asList(p);
		List<Schema> rdbms = Arrays.asList(s);

		this.testCheck(Uml2Rdbms.rdbmsDomainId, uml, rdbms, true);

	}

	@Test
	public void check_uml__empty_empty() {

		List<Package> uml = Arrays.asList();
		List<Schema> rdbms = Arrays.asList();

		this.testCheck(Uml2Rdbms.umlDomainId, uml, rdbms, true);

	}

	@Test
	public void check2__empty_empty() {

		List<Package> uml = Arrays.asList();
		List<Schema> rdbms = Arrays.asList();

		Map<DomainModelItentifier, Iterable<?>> domains = new HashMap<DomainModelItentifier, Iterable<?>>();
		domains.put(Uml2Rdbms.umlDomainId, uml);
		domains.put(Uml2Rdbms.rdbmsDomainId, rdbms);

		Transformer transformation = new Uml2Rdbms();
		Boolean match = null;
		try {
			match = transformation.check(Uml2Rdbms.umlDomainId, domains);
			// for each of nothing there is nothing!
			Assert.assertTrue(match);
		} catch (RelationNotValidException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void check1__package_schema_true() {
		Package p = simpleUml.SimpleUmlFactory.createPackage();
		p.setName("addressBook");

		Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("addressBook");

		List<Package> uml = Arrays.asList(p);
		List<Schema> rdbms = Arrays.asList(s);

		Map<DomainModelItentifier, Iterable<?>> domains = new HashMap<DomainModelItentifier, Iterable<?>>();
		domains.put(Uml2Rdbms.umlDomainId, uml);
		domains.put(Uml2Rdbms.rdbmsDomainId, rdbms);

		Transformer transformation = new Uml2Rdbms();
		try {
			boolean match = transformation.check(Uml2Rdbms.rdbmsDomainId, domains);

			Assert.assertTrue(match);

		} catch (RelationNotValidException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void check2__package_schema_true() {
		Package p = simpleUml.SimpleUmlFactory.createPackage();
		p.setName("addressBook");

		Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("addressBook");

		List<Package> uml = Arrays.asList(p);
		List<Schema> rdbms = Arrays.asList(s);

		Map<DomainModelItentifier, Iterable<?>> domains = new HashMap<DomainModelItentifier, Iterable<?>>();
		domains.put(Uml2Rdbms.umlDomainId, uml);
		domains.put(Uml2Rdbms.rdbmsDomainId, rdbms);

		Transformer transformation = new Uml2Rdbms();
		try {
			boolean match = transformation.check(Uml2Rdbms.umlDomainId, domains);

			Assert.assertTrue(match);
		} catch (RelationNotValidException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void check1__package_schema_false() {
		Package p = simpleUml.SimpleUmlFactory.createPackage();
		p.setName("addressBook");

		Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("library");

		List<Package> uml = Arrays.asList(p);
		List<Schema> rdbms = Arrays.asList(s);

		Map<DomainModelItentifier, Iterable<?>> domains = new HashMap<DomainModelItentifier, Iterable<?>>();
		domains.put(Uml2Rdbms.umlDomainId, uml);
		domains.put(Uml2Rdbms.rdbmsDomainId, rdbms);

		Transformer transformation = new Uml2Rdbms();
		try {
			boolean match = transformation.check(Uml2Rdbms.rdbmsDomainId, domains);

			Assert.assertFalse(match);
		} catch (RelationNotValidException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void check2__package_schema_false() {
		Package p = simpleUml.SimpleUmlFactory.createPackage();
		p.setName("addressBook");

		Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("library");

		List<Package> uml = Arrays.asList(p);
		List<Schema> rdbms = Arrays.asList(s);

		Map<DomainModelItentifier, Iterable<?>> domains = new HashMap<DomainModelItentifier, Iterable<?>>();
		domains.put(Uml2Rdbms.umlDomainId, uml);
		domains.put(Uml2Rdbms.rdbmsDomainId, rdbms);

		Transformer transformation = new Uml2Rdbms();
		try {
			boolean match = transformation.check(Uml2Rdbms.umlDomainId, domains);

			Assert.assertFalse(match);
		} catch (RelationNotValidException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

}
