package org.nyer.pyjs.primitive;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.type.Void;
import org.nyer.pyjs.statement.Return;

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
				env = env.extend(getParameters(), evalArguments(env, arguments));
				Object ret = new Void();
				List<Instrument> instruments = DefFun.this.instruments;
				for (int i = 0, s = instruments.size();i < s;i ++) {
					Instrument instrument = instruments.get(i);
					ret = instrument.invoke(env);
					if (instrument.getFun() instanceof Return) {
						return ret;
					}
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
