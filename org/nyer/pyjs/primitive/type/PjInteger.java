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

import org.nyer.pyjs.ElementVisitor;
import org.nyer.pyjs.Env;


public class PjInteger extends Value  {

	public PjInteger(String value) {
		super(value);
	}
	
	@Override
	public String getTypeStr(Env env) {
		return "integer";
	}
	
	@Override
	public Object toValue(Env env, Object rawValue) throws Exception {
		return Integer.valueOf((String) rawValue);
	}
	
	@Override
	public Integer getValue() {
		return (Integer) super.getValue();
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}
	
	public static PjInteger valueOf(final int v) {
		return new PjInteger(v + "");
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj instanceof Value == false)
			return false;
		Value objValue = (Value) obj;
		return this.value.equals(objValue.getValue());
	}
}
