package net.akehurst.transform.binary;

public class BElement {
	public BElement(final int value) {
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
		if (obj instanceof BElement) {
			final BElement other = (BElement) obj;
			return other.value == this.value;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "B{" + this.value + "}";
	}
}
