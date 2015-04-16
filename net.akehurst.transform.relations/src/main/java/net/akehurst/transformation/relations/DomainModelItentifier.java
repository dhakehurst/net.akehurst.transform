package net.akehurst.transformation.relations;

public class DomainModelItentifier {

	public DomainModelItentifier(Class<?> idObject) {
		this.idObject = idObject;
	}
	Class<?> idObject;
	
	@Override
	public boolean equals(Object obj) {
		return this.idObject.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return this.idObject.hashCode();
	}
	
	@Override
	public String toString() {
		return this.idObject.toString();
	}
}
