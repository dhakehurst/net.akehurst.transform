package net.akehurst.transfomation.relations.example.pojo.uml2rdbms.enforce;

import static org.junit.Assert.*;

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
import simpleUml.Model;
import simpleUml.Package;

public class Uml2Rdbms_Model2Database_Test {

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
	public void enforce_uml__2package_1schema() {
		Package p1 = simpleUml.SimpleUmlFactory.createPackage();
		p1.setName("addressBook");

		Package p2 = simpleUml.SimpleUmlFactory.createPackage();
		p2.setName("library");

		Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("addressBook");

		List<Package> uml = Arrays.asList(p1, p2);
		List<Schema> rdbms = Arrays.asList(s);

		testEnforce(Uml2Rdbms.umlDomainId, uml, rdbms, false, true);
	}

	@Test
	public void enforce_rdbms__2package_1schema() {
		Package p1 = simpleUml.SimpleUmlFactory.createPackage();
		p1.setName("addressBook");

		Package p2 = simpleUml.SimpleUmlFactory.createPackage();
		p2.setName("library");

		Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("addressBook");

		List<Package> uml = Arrays.asList(p1, p2);
		List<Schema> rdbms = Arrays.asList(s);

		testEnforce(Uml2Rdbms.rdbmsDomainId, uml, rdbms, true, true);
		
	}

	@Test
	public void enforec_uml__2package_1schema_2() {
		Package p1 = simpleUml.SimpleUmlFactory.createPackage();
		p1.setName("library");

		Package p2 = simpleUml.SimpleUmlFactory.createPackage();
		p2.setName("addressBook");

		Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("addressBook");

		List<Package> uml = Arrays.asList(p1, p2);
		List<Schema> rdbms = Arrays.asList(s);

		testEnforce(Uml2Rdbms.umlDomainId, uml, rdbms, false, true);
	}

	@Test
	public void enforce_rdbms__2package_1schema_2() {
		Package p1 = simpleUml.SimpleUmlFactory.createPackage();
		p1.setName("library");

		Package p2 = simpleUml.SimpleUmlFactory.createPackage();
		p2.setName("addressBook");

		Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("addressBook");

		List<Package> uml = Arrays.asList(p1, p2);
		List<Schema> rdbms = Arrays.asList(s);

		testEnforce(Uml2Rdbms.rdbmsDomainId, uml, rdbms, true, true);

	}

}
