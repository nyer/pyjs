/**
 *   Copyright (c) Lei Ting. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *          the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package org.nyer.pyjs;

import java.util.HashMap;
import java.util.Map;

public class Env {
	private Map<String, IFun> map = new HashMap<String, IFun>();
	protected Env parent = null;
	
	public Env() {
	}
	
	public Env(Env parent) {
		this.parent = parent;
	}
	
	public void put(String identifier, IFun value) {
		map.put(identifier, value);
	}
	
	public Env extend(String[] parameters, IFun[] values)  throws Exception {
		Env env = new Env(this);
		env.map = new HashMap<String, IFun>();
		if (parameters != null) {
			for (int i = 0, s = parameters.length;i < s;i ++) {
				String key = parameters[i];
				env.put(key, values[i]);
			}
		}
		
		return env;
	}
	
	public IFun lookUp(String identifier) {
		IFun v = map.get(identifier);
		if (v == null && this.parent != null)
			return this.parent.lookUp(identifier);
		else
			return v;
	}
}
