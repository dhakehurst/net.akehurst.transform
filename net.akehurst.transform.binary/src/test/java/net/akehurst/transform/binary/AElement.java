package net.akehurst.transform.binary;

public class AElement {

	public AElement(final int value) {
		this.value = value;
	}

	private final int value;

	public int getValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		return this.value;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof AElement) {
			final AElement other = (AElement) obj;
			return other.value == this.value;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "A{" + this.value + "}";
	}

}
