package org.nyer.pyjs;

import java.util.Arrays;
import java.util.List;

import org.nyer.pyjs.primitive.ControlFun;
import org.nyer.pyjs.primitive.Instrument;
import org.nyer.pyjs.primitive.Return;
import org.nyer.pyjs.primitive.type.ReturnResult;
import org.nyer.pyjs.primitive.type.Void;

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
				Env newEnv = closure.extend(getParameters(), evalArguments(closure, arguments));
				
				Object ret = new Void();
				List<Instrument> instruments = AnonymousFun.this.instruments;
				for (int i = 0, s = instruments.size();i < s;i ++) {
					Instrument instrument = instruments.get(i);
					if (instrument.getFun() instanceof Return) {
						Object returnV = new Void();
						i = i + 1;
						if (i < s) {
							instrument = instruments.get(i);
							if (instrument.getFun() instanceof ControlFun == false)
								returnV = instrument.invoke(newEnv);
						}
						return returnV;
					}
					ret = instrument.invoke(newEnv);
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
