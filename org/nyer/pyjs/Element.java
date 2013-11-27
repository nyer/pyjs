package org.nyer.pyjs;

public interface Element {
	void accept(ElementVisitor visitor);
}
