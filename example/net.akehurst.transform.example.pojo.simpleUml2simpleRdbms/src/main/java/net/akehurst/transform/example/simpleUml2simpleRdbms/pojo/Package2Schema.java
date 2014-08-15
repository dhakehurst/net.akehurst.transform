package net.akehurst.transform.example.simpleUml2simpleRdbms.pojo;

import simpleRdbms.Schema;
import simpleUml.Package;
import net.akehurst.transform.binary.Relation;
import net.akehurst.transform.binary.Transformer;

public class Package2Schema implements Relation<simpleUml.Package, simpleRdbms.Schema>{

	public boolean isValidForLeft2Right(Package left) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isValidForRight2Left(Schema right) {
		// TODO Auto-generated method stub
		return false;
	}

	public Schema constructLeft2Right(Package left, Transformer transformer) {
		// TODO Auto-generated method stub
		return null;
	}

	public Package constructRight2Left(Schema right, Transformer transformer) {
		// TODO Auto-generated method stub
		return null;
	}

	public void configureLeft2Right(Package left, Schema right,
			Transformer transformer) {
		// TODO Auto-generated method stub
		
	}

	public void configureRight2Left(Package left, Schema right,
			Transformer transformer) {
		// TODO Auto-generated method stub
		
	}

}
