package simpleRdbms;

public class Database {

	public Database() {
		this.schemas = new java.util.HashSet<Schema>();
	}
	
	String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	java.util.Set<Schema> schemas;
	public java.util.Set<Schema> getSchemas() {
		return schemas;
	}
	public void setSchemas(java.util.Set<Schema> value) {
		this.schemas = value;
	}
	
}
