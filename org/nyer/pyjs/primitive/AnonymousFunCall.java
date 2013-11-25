package org.nyer.pyjs.primitive;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.type.Void;
import org.nyer.pyjs.statement.Return;

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
			ret = instrument.invoke(env);
			if (instrument.getFun() instanceof Return) {
				return instrument.invoke(env);
			}
			
		}
		return ret;
	}

}
