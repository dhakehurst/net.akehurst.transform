package net.akehurst.transfomation.relations.example.uml2rdbms;

import java.util.List;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAttribute;

import net.akehurst.transfomation.relations.example.uml2rdbms.rule.Class2Table;
import net.akehurst.transfomation.relations.example.uml2rdbms.rule.Package2Schema;
import net.akehurst.transformation.relations.AbstractTransformer;
import net.akehurst.transformation.relations.DomainModelItentifier;
import net.akehurst.transformation.relations.Relation;
import net.akehurst.transformation.relations.Transformer;
import net.akehurst.transformation.relations.annotations.Domain;

/*
 * transformation umlToRdbms(uml:SimpleUML, rdbms:SimpleRDBMS)
 {
 key Table (name, schema);
 key Column (name, owner);   // owner:Table opposite column:Column
 key Key (name, owner); // key of class ëKeyí;
 // owner:Table opposite key:Key
 */

public class Uml2Rdbms extends AbstractTransformer {

	public static final DomainModelItentifier umlDomainId = new DomainModelItentifier(simpleUml.SimpleUmlFactory.class);
	public static final DomainModelItentifier rdbmsDomainId = new DomainModelItentifier(simpleRdbms.SimpleRdbmsFactory.class);
	
	public Uml2Rdbms() {

	}

	@Override
	public List<Class<? extends Relation>> getTopRelationType() {
		List<Class<? extends Relation>> result = new Vector<Class<? extends Relation>>();

		result.add(Package2Schema.class);
		// result.add(Class2Table.class);

		return result;
	}

}
