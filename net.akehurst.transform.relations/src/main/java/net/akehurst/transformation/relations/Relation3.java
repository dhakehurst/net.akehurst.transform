package net.akehurst.transformation.relations;


public interface Relation3<DT1, DT2> {

	Transformer getTransformer();
	
	Class<?> getDomain1Type();
	Class<?> getDomain2Type();
	
	DT1 getDomain1();
	DT2 getDomain2();
	
	boolean check1();
	boolean check2();
	
	boolean when1();
	boolean when2();

	boolean where1();
	boolean where2();
}
