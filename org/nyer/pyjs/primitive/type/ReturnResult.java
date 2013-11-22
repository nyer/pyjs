package org.nyer.pyjs.primitive.type;

public class ReturnResult {
	private Object retValue;
	public ReturnResult(Object retValue) {
		this.retValue = retValue;
	}
	
	public Object getValue() {
		return retValue;
	}
}
