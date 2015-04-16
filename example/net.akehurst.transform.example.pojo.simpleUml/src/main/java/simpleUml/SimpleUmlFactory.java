package simpleUml;

public class SimpleUmlFactory {

	public static Package createPackage() {
		return new Package();
	}

	public static Class createClass() {
		return new Class();
	}

	public static Model createModel() {
		return new Model();
	}

}
