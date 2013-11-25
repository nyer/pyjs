package org.nyer.pyjs.primitive.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;

public class PjMap extends Value {

	public PjMap() {
		super("map expr", null);
	}
	
	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (int i = 0, s= arguments.size();i < s;i += 2) {
			map.put(arguments.get(i), arguments.get(i + 1));
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
