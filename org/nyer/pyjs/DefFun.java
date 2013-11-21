package org.nyer.pyjs;

import java.util.List;

import org.nyer.pyjs.primitive.Instrument;
import org.nyer.pyjs.primitive.Void;

public class DefFun extends AbstractFun {
	protected List<Instrument> instruments;
	public DefFun(String name, String[] parameters, List<Instrument> instruments) {
		super(name, parameters);
		this.instruments = instruments;
	}
	
	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		Object func = new AbstractFun(getName(), getParameters()) {
			
			@Override
			public Object invoke(Env env, Object[] arguments) throws Exception {
				env = env.extend(getParameters(), arguments);
				Object ret = new Void();
				List<Instrument> instruments = DefFun.this.instruments;
				for (int i = 0, s = instruments.size();i < s;i ++) {
					ret = instruments.get(i).invoke(env);
				}
				return ret;
			}
		};
		env.put(getName(), func);
		
		return func;
	}
	
	public List<Instrument> getInstruments() {
		return instruments;
	}
}
