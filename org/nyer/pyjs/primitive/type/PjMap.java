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

import java.util.HashMap;
import java.util.Map;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;

public class PjMap extends Value {

	public PjMap() {
		super("map expr", null);
	}
	
	@Override
	public IFun invoke(Env env, IFun[] arguments) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (int i = 0, s= arguments.length;i < s;i += 2) {
			map.put(arguments[i], arguments[i + 1]);
		}
		this.value = map;
		
		return this;
	}
	
	@Override
	public String getTypeStr(Env env) throws Exception {
		return "map";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<Value, IFun> getValue() {
		return (Map<Value, IFun>) super.getValue();
	}
}
