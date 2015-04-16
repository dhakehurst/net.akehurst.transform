package simpleUml;

public class Model {

	public Model() {
		this.packages = new java.util.HashSet<simpleUml.Package>();
	}
	
	String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	java.util.Set<simpleUml.Package> packages;
	public java.util.Set<simpleUml.Package> getPackages() {
		return packages;
	}
	public void setPackages(java.util.Set<simpleUml.Package> value) {
		this.packages = value;
	}
	
}
