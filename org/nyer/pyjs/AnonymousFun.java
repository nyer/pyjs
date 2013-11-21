package org.nyer.pyjs;

import java.util.Arrays;
import java.util.List;

import org.nyer.pyjs.primitive.Instrument;
import org.nyer.pyjs.primitive.Void;

public class AnonymousFun extends DefFun {

	public AnonymousFun(String[] parameters,
			List<Instrument> instruments) {
		super("__anonymous", parameters, instruments);
	}

	@Override
	public Object invoke(final Env closure, Object[] arguments) throws Exception {
		IFun fun = new AbstractFun(getName(), getParameters()) {
			
			@Override
			public Object invoke(Env env, Object[] arguments) throws Exception {
				Env newEnv = closure.extend(getParameters(), arguments);
				
				Object ret = new Void();
				List<Instrument> instruments = AnonymousFun.this.instruments;
				for (int i = 0, s = instruments.size();i < s;i ++) {
					ret = instruments.get(i).invoke(newEnv);
				}
				return ret;
			}
		};
		
		return fun;
	}

	@Override
	public String toString() {
		return "AnonymousFun [parameters=" + Arrays.toString(parameters) + "]";
	}
}
