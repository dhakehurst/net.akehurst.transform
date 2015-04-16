package net.akehurst.transfomation.relations.example.pojo.uml2rdbms.check;

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

	void testCheck(DomainModelItentifier domainId, List<?> uml, List<?> rdbms, boolean expected) {
		Map<DomainModelItentifier, Iterable<?>> domains = new HashMap<DomainModelItentifier, Iterable<?>>();
		domains.put(Uml2Rdbms.umlDomainId, uml);
		domains.put(Uml2Rdbms.rdbmsDomainId, rdbms);

		Transformer transformation = new Uml2Rdbms();
		try {
			
			boolean match = transformation.check(domainId, domains);
			
			Assert.assertEquals(expected, match);
			
		} catch (RelationNotValidException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void check_rdbms__2package_1schema() {		
		Package p1 = simpleUml.SimpleUmlFactory.createPackage();
		p1.setName("addressBook");

		Package p2 = simpleUml.SimpleUmlFactory.createPackage();
		p2.setName("library");

		Model model = simpleUml.SimpleUmlFactory.createModel();
		model.getPackages().add(p1);
		model.getPackages().add(p2);
		
		Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("addressBook");

		List<Model> uml = Arrays.asList(model);
		List<Schema> rdbms = Arrays.asList(s);

		this.testCheck(Uml2Rdbms.rdbmsDomainId, uml, rdbms, true);

	}

	@Test
	public void check_uml__2package_1schema() {
		Package p1 = simpleUml.SimpleUmlFactory.createPackage();
		p1.setName("addressBook");

		Package p2 = simpleUml.SimpleUmlFactory.createPackage();
		p2.setName("library");

		Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("addressBook");

		List<Package> uml = Arrays.asList(p1, p2);
		List<Schema> rdbms = Arrays.asList(s);

		this.testCheck(Uml2Rdbms.umlDomainId, uml, rdbms, false);
	}

	@Test
	public void check_rdbms__2package_1schema_2() {
		Package p1 = simpleUml.SimpleUmlFactory.createPackage();
		p1.setName("library");

		Package p2 = simpleUml.SimpleUmlFactory.createPackage();
		p2.setName("addressBook");

		Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("addressBook");

		List<Package> uml = Arrays.asList(p1, p2);
		List<Schema> rdbms = Arrays.asList(s);

		this.testCheck(Uml2Rdbms.rdbmsDomainId, uml, rdbms, true);

	}

	@Test
	public void check2__2package_1schema_2() {
		Package p1 = simpleUml.SimpleUmlFactory.createPackage();
		p1.setName("library");

		Package p2 = simpleUml.SimpleUmlFactory.createPackage();
		p2.setName("addressBook");

		Schema s = simpleRdbms.SimpleRdbmsFactory.createSchema();
		s.setName("addressBook");

		List<Package> uml = Arrays.asList(p1, p2);
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
