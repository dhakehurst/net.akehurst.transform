package net.akehurst.transfomation.relations.example.uml2rdbms.rule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simpleRdbms.Schema;
import simpleUml.Package;
import net.akehurst.transfomation.relations.example.uml2rdbms.Uml2Rdbms;
import net.akehurst.transformation.relations.AbstractRelation;
import net.akehurst.transformation.relations.DomainModelItentifier;
import net.akehurst.transformation.relations.Relation;
import net.akehurst.transformation.relations.Relation2;
import net.akehurst.transformation.relations.Transformer;
import net.akehurst.transformation.relations.annotations.Check;
import net.akehurst.transformation.relations.annotations.Domain;
import net.akehurst.transformation.relations.annotations.Enforce;
import net.akehurst.transformation.relations.annotations.EnforceWhere;
import net.akehurst.transformation.relations.annotations.When;
import net.akehurst.transformation.relations.annotations.CheckWhere;

/*
 top relation PackageToSchema   // map each package to a schema
 {
 pn: String;
 checkonly domain uml p:Package {name=pn};
 enforce domain rdbms s:Schema {name=pn};
 }
 */
public class Package2Schema extends AbstractRelation implements Relation2<simpleUml.Package, simpleRdbms.Schema> {

	public Package2Schema(Transformer tr) {
		super(tr);
	}

	//region Domain Variables
	simpleUml.Package p;

	@Domain(simpleUml.SimpleUmlFactory.class)
	public simpleUml.Package getP() {
		return this.p;
	}

	@Domain(simpleUml.SimpleUmlFactory.class)
	public void setP(simpleUml.Package value) {
		this.p = value;
	}

	simpleRdbms.Schema s;

	@Domain(simpleRdbms.SimpleRdbmsFactory.class)
	public simpleRdbms.Schema getS() {
		return this.s;
	}

	@Domain(simpleRdbms.SimpleRdbmsFactory.class)
	public void setS(simpleRdbms.Schema value) {
		this.s = value;
	}

	

	@When(simpleUml.SimpleUmlFactory.class)
	public boolean when1() {
		return true;
	}

	@When(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean when2() {
		return true;
	}
	
	
	@Check(simpleUml.SimpleUmlFactory.class)
	public boolean check1() {
		if (null==this.getP()) {
			return null==this.getS();
		}
		String pn = this.getS().getName();

		boolean result = true;

		result = result && this.getP().getName().equals(pn);

		return result;
	}

	@Check(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean check2() {
		if (null==this.getS() ) {
			return null==this.getP();
		}
		String pn = this.getP().getName();

		boolean result = true;

		result = result && this.getS().getName().equals(pn);
		
		return result;
	}

	@CheckWhere(simpleUml.SimpleUmlFactory.class)
	public boolean checkWhere1() {
		return true;
	}

	@CheckWhere(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean checkWhere2() {
		return true;
	}

	@Enforce(simpleRdbms.SimpleRdbmsFactory.class)
	public void enforce1() {
		if (null==this.getS()) {
			this.setS( simpleRdbms.SimpleRdbmsFactory.createSchema() );
		}
		String pn = this.getP().getName();

		this.getS().setName(pn);
	}

	@Enforce(simpleUml.SimpleUmlFactory.class)
	public void enforce2() {
		if (null==this.getP() ) {
			this.setP( simpleUml.SimpleUmlFactory.createPackage() );
		}
		String pn = this.getS().getName();

		this.getP().setName(pn);
		
		return ;
	}

	@EnforceWhere(simpleUml.SimpleUmlFactory.class)
	public void enforceWhere1() {
		return ;
	}

	@EnforceWhere(simpleRdbms.SimpleRdbmsFactory.class)
	public void enforceWhere2() {
		return ;
	}
}
