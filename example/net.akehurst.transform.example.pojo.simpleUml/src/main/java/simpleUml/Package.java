package simpleUml;

import java.util.List;

public class Package extends UMLModelElement {

	List<PackageElement> element;
	public List<PackageElement> getElement() {
		return element;
	}
	public void setElement(List<PackageElement> element) {
		this.element = element;
	}
}
