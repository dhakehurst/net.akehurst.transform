package net.akehurst.transfomation.relations.example.pojo.uml2rdbms.enforce;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.akehurst.transfomation.relations.example.uml2rdbms.Uml2Rdbms;
import net.akehurst.transformation.relations.DomainModelItentifier;
import net.akehurst.transformation.relations.RelationNotValidException;
import net.akehurst.transformation.relations.Transformer;

import org.junit.Assert;
import org.junit.Test;

import simpleRdbms.Schema;
import simpleUml.Package;

public class Uml2Rdbms_Package2Schema_Test {

	void testEnforce(DomainModelItentifier targetDomainId, List<Package> uml, List<Schema> rdbms, boolean before, boolean after) {
		Map<DomainModelItentifier, Iterable<?>> domains = new HashMap<DomainModelItentifier, Iterable<?>>();
		domains.put(Uml2Rdbms.umlDomainId, uml);
		domains.put(Uml2Rdbms.rdbmsDomainId, rdbms);

		Transformer transformation = new Uml2Rdbms();
		try {
			boolean match = transformation.check(targetDomainId, domains);
			Assert.assertEquals(before,match);
			
			Map<DomainModelItentifier, Iterable<?>> newDomains = transformation.enforce( targetDomainId, domains);

			match = transformation.check(targetDomainId, newDomains);
			Assert.assertEquals(after,match);

		} catch (RelationNotValidException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

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
