/**
 *   Copyright (c) Lei Ting. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *          the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package org.nyer.pyjs.primitive.type;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.AbstractFun;

public abstract class Value extends AbstractFun {
	protected Object value;
	protected Object rawValue;
	
	public Value(Object rawValue) {
		this.rawValue = rawValue;
	}
	
	public Value() {}
	
	@Override
	public IFun invoke(Env env) throws Exception {
		if (value == null)
			this.value = toValue(env, rawValue);
		
		return this;
	}
	
	public abstract Object toValue(Env env, Object rawValue) throws Exception;
	
	public Object getValue() {
		return value;
	}
	
	public Object getRawValue() {
		return rawValue;
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
