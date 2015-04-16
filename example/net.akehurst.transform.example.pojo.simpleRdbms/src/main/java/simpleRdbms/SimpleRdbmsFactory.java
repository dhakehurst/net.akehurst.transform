package simpleRdbms;

public class SimpleRdbmsFactory {

	public static Schema createSchema() {
		return new Schema();
	}

	public static Table createTable() {
		return new Table();
	}

	public static Column createColumn() {
		return new Column();
	}

}
