package org.nyer.pyjs;

import java.util.HashMap;
import java.util.Map;

import org.nyer.pyjs.primitive.Identifier;

public class Env {
	private Map<String, Object> map = new HashMap<String, Object>();
	protected Env parent = null;
	
	public Env() {
	}
	
	public Env(Env parent) {
		this.parent = parent;
	}
	
	public void put(String identifier, Object value) {
		map.put(identifier, value);
	}
	
	public void put(IFun fun) {
		map.put(fun.getName(), fun);
	}
	
	public Env extend(String[] identifiers, Object[] values) {
		Env env = new Env(this);
		env.map = new HashMap<String, Object>();
		if (identifiers != null) {
			for (int i = 0, s = identifiers.length;i < s;i ++) {
				env.put(identifiers[i], values[i]);
			}
		}
		
		return env;
	}
	
	public Object lookUp(String identifier) {
		Object v = map.get(identifier);
		if (v == null && this.parent != null)
			return this.parent.lookUp(identifier);
		else
			return v;
	}
}
