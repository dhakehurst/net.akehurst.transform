package net.akehurst.transfomation.relations.example.pojo.uml2rdbms.check;

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

public class Uml2Rdbms_Class2Table_Test {

	@Test
	public void check1__class_table_true() {
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
	public void check2__class_table_true() {
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

		simpleRdbms.Column col1 = simpleRdbms.SimpleRdbmsFactory.createColumn();
		col1.setName(t1.getName() + "_tid");
		col1.setType("NUMBER");
		t1.getColumn().add(col1);

		List<? extends Object> uml = Arrays.asList(p, c1);
		List<? extends Object> rdbms = Arrays.asList(s, t1);

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
	public void check2__class_table_false() {
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

		// simpleRdbms.Column col1 =
		// simpleRdbms.SimpleRdbmsFactory.createColumn();
		// col1.setName(t1.getName()+"_tid");
		// col1.setType("NUMBER");
		// t1.getColumn().add(col1);

		List<? extends Object> uml = Arrays.asList(p, c1);
		List<? extends Object> rdbms = Arrays.asList(s, t1);

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
}
