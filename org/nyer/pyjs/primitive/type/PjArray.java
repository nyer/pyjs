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

import java.util.Arrays;

import org.nyer.pyjs.ElementVisitor;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;

public class PjArray extends Value {

	public PjArray(IFun[] arguments) {
		super(arguments);
	}

	@Override
	public Object toValue(Env env, Object rawValue) throws Exception {
		return evalArguments(env, (IFun[]) rawValue);
	}
	
	@Override
	public String getTypeStr(Env env) throws Exception {
		return "array";
	}
	
	@Override
	public IFun[] getValue() {
		return (IFun[]) super.getValue();
	}
	
	@Override
	public String toString() {
		try {
			return getTypeStr(null) + Arrays.toString(getValue());
		} catch (Exception e) {
		}
		return "";
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}
}
