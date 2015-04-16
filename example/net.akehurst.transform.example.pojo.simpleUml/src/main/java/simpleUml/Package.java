package simpleUml;

import java.util.List;

public class Package extends UMLModelElement {

	List<PackageElement> elements;
	public List<PackageElement> getElements() {
		return elements;
	}
	public void setElements(List<PackageElement> element) {
		this.elements = element;
	}
	
	@Override
	public String toString() {
		return "Package { name='"+this.getName()+"' }";
	}
}
