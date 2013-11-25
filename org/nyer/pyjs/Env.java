package org.nyer.pyjs;

import java.util.HashMap;
import java.util.List;
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
	
	public Env extend(String[] identifiers, List<IFun> values) {
		Env env = new Env(this);
		env.map = new HashMap<String, IFun>();
		if (identifiers != null) {
			for (int i = 0, s = identifiers.length;i < s;i ++) {
				env.put(identifiers[i], values.get(i));
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
