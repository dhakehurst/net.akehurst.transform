package net.akehurst.transfomation.relations.example.uml2rdbms.rule;

import java.util.Arrays;

import net.akehurst.transformation.relations.AbstractRelation;
import net.akehurst.transformation.relations.Relation;
import net.akehurst.transformation.relations.Relation2;
import net.akehurst.transformation.relations.RelationNotValidException;
import net.akehurst.transformation.relations.Transformer;
import net.akehurst.transformation.relations.annotations.Check;
import net.akehurst.transformation.relations.annotations.Domain;
import net.akehurst.transformation.relations.annotations.When;
import net.akehurst.transformation.relations.annotations.CheckWhere;

/*

 */
public class Attribute2Column extends AbstractRelation implements Relation2<simpleUml.Class, simpleRdbms.Table> {
	public Attribute2Column(Transformer transformer) {
		super(transformer);
	}

	simpleUml.Class c;

	simpleRdbms.Table t;

	@When(simpleUml.SimpleUmlFactory.class)
	public boolean when1(simpleUml.Class c, simpleRdbms.Table t, Transformer tr) {
		boolean result = true;
		return result;
	}

	@When(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean when2(simpleUml.Class c, simpleRdbms.Table t, Transformer tr) {
		boolean result = true;
		return result;
	}

	@Check(simpleUml.SimpleUmlFactory.class)
	public boolean check1(simpleUml.Class c, simpleRdbms.Table t, Transformer tr) {
		boolean result = true;
		return result;
	}

	@Check(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean check2(simpleUml.Class c, simpleRdbms.Table t, Transformer tr) {
		boolean result = true;
		return result;
	}

	@CheckWhere(simpleUml.SimpleUmlFactory.class)
	public boolean where1(simpleUml.Class c, simpleRdbms.Table t, Transformer tr) {
		return true;
	}

	@CheckWhere(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean where2(simpleUml.Class c, simpleRdbms.Table t, Transformer tr) {
		return true;
	}

}