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

public class PjString extends Value {

	public PjString(String value) {
		super(value);
		this.value = value;
	}
	
	@Override
	public String getTypeStr(Env env) {
		return "String";
	}
	
	@Override
	public Object toValue(Env env, Object rawValue) throws Exception {
		return rawValue;
	}
	
	@Override
	public String getValue() {
		return (String) super.getValue();
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}
}
