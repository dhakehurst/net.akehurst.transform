package net.akehurst.transfomation.relations.example.uml2rdbms.rule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.akehurst.transfomation.relations.example.uml2rdbms.Uml2Rdbms;
import net.akehurst.transformation.relations.AbstractRelation;
import net.akehurst.transformation.relations.DomainModelItentifier;
import net.akehurst.transformation.relations.Relation;
import net.akehurst.transformation.relations.Relation2;
import net.akehurst.transformation.relations.RelationNotApplicableException;
import net.akehurst.transformation.relations.RelationNotValidException;
import net.akehurst.transformation.relations.Transformer;
import net.akehurst.transformation.relations.annotations.Check;
import net.akehurst.transformation.relations.annotations.Domain;
import net.akehurst.transformation.relations.annotations.When;
import net.akehurst.transformation.relations.annotations.CheckWhere;

/*
 // map each persistent class to a table

 top relation ClassToTable   // map each persistent class to a table
 {
	cn, prefix: String;
	
	checkonly domain uml c:Class {
	 	namespace=p:Package {},
	 	kind='Persistent', name=cn
	};

 	enforce domain rdbms t:Table {
		 schema=s:Schema {}, name=cn,
		 column=cl:Column {name=cn+'_tid', type='NUMBER'},
		 key=k:Key {name=cn+'_pk', column=cl}
	};

 	when {
 		PackageToSchema(p, s);
 	}

 	where {
 		prefix = '';
 		AttributeToColumn(c, t, prefix);
 	}
 }
 */
public class Class2Table extends AbstractRelation implements Relation2<simpleUml.Class, simpleRdbms.Table> {

	public Class2Table(Transformer tr) {
		super(tr);
	}

	simpleUml.Class c;

	@Domain(simpleUml.SimpleUmlFactory.class)
	public simpleUml.Class getC() {
		return this.c;
	}

	@Domain(simpleUml.SimpleUmlFactory.class)
	public void setC(simpleUml.Class value) {
		this.c = value;
	}

	simpleRdbms.Table t;

	@Domain(simpleRdbms.SimpleRdbmsFactory.class)
	public simpleRdbms.Table getT() {
		return this.t;
	}

	@Domain(simpleRdbms.SimpleRdbmsFactory.class)
	public void setT(simpleRdbms.Table value) {
		this.t = value;
	}

	@When(simpleUml.SimpleUmlFactory.class)
	public boolean when1() {
		boolean result = true;

		simpleUml.Package p = c.getNamespace();

		try {
			Map<DomainModelItentifier, Iterable<?>> domainArgs = new HashMap<DomainModelItentifier, Iterable<?>>();
			domainArgs.put(Uml2Rdbms.umlDomainId, Arrays.asList(p));
			domainArgs.put(Uml2Rdbms.rdbmsDomainId, Arrays.asList(t.getSchema()));
			Package2Schema r = (Package2Schema)this.getTransformer().findMatch(Uml2Rdbms.rdbmsDomainId, Package2Schema.class, domainArgs);

			result = result && null != r.getS();
		} catch (RelationNotValidException ex) {
			return false;
		} catch (RelationNotApplicableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@When(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean when2() {
		boolean result = true;

		simpleRdbms.Schema s = t.getSchema();

		try {
			Map<DomainModelItentifier, Iterable<?>> domainArgs = new HashMap<DomainModelItentifier, Iterable<?>>();
			domainArgs.put(Uml2Rdbms.umlDomainId, Arrays.asList(c.getNamespace()));
			domainArgs.put(Uml2Rdbms.rdbmsDomainId, Arrays.asList(s));
			Package2Schema r = (Package2Schema)this.getTransformer().findMatch(Uml2Rdbms.umlDomainId, Package2Schema.class, domainArgs);

			result = result && null != r.getP();
		} catch (RelationNotValidException ex) {
			return false;
		} catch (RelationNotApplicableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Check(simpleUml.SimpleUmlFactory.class)
	public boolean check1() {

		String cn = t.getName();

		boolean result = true;

		result = result && c.getKind().equals("persistent");
		result = result && c.getName().equals(cn);

		return result;

	}

	@Check(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean check2() {

		String cn = c.getName();

		boolean result = true;

		result = result && t.getName().equals(cn);
		boolean exists = false;
		for (simpleRdbms.Column cl : t.getColumn()) {
			boolean v = true;
			v = v && (cn + "_tid").equals(cl.getName());
			v = v && "NUMBER".equals(cl.getType());
			if (v) {
				exists = true;
				break;
			}
		}
		result = result && exists;
		return result;

	}

	@CheckWhere(simpleUml.SimpleUmlFactory.class)
	public boolean where1() {
		String prefix = "";
		// this.getTransformer().checkMatchType(Attribute2Column.class, c, t,
		// prefix);

		return false;
	}

	@CheckWhere(simpleRdbms.SimpleRdbmsFactory.class)
	public boolean where2() {
		String prefix = "";
		// this.getTransformer().checkMatchType(Attribute2Column.class, c, t,
		// prefix);

		return false;
	}

}
