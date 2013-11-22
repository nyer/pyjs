package org.nyer.pyjs;

import java.util.List;

import org.nyer.pyjs.primitive.ControlFun;
import org.nyer.pyjs.primitive.Instrument;
import org.nyer.pyjs.primitive.Return;
import org.nyer.pyjs.primitive.type.ReturnResult;
import org.nyer.pyjs.primitive.type.Void;

public class AnonymousFunCall extends AbstractFun {
	private List<Instrument> instruments;
	
	public AnonymousFunCall(DefFun fun) {
		super("anonymouse funcall", fun.getParameters());
		this.instruments = fun.getInstruments();
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		env = env.extend(getParameters(), evalArguments(env, arguments));
		Object ret = new Void();
		for (int i = 0, s = instruments.size();i < s; i ++) {
			Instrument instrument = instruments.get(i);
			if (instrument.getFun() instanceof Return) {
				Object returnV = new Void();
				i = i + 1;
				if (i < s) {
					instrument = instruments.get(i);
					if (instrument.getFun() instanceof ControlFun == false)
						returnV = instrument.invoke(env);
				}
				return returnV;
			}
			
			ret = instrument.invoke(env);
		}
		return ret;
	}

}
