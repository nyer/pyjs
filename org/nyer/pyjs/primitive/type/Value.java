package org.nyer.pyjs.primitive.type;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.AbstractFun;

public abstract class Value extends AbstractFun {
	protected Object value;
	
	public Value(String parameter, Object value) {
		super(new String[] {parameter});
		this.value = value;
	}
	
	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		return this;
	}
	
	public Object getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		try {
			return "" + getValue();
		} catch (Exception e) {
		}
		return "";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Value other = (Value) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
