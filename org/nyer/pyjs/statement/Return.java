package org.nyer.pyjs.statement;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.primitive.AbstractFun;
import org.nyer.pyjs.primitive.Instrument;
import org.nyer.pyjs.primitive.type.Void;

public class Return extends AbstractFun {
	private Instrument expression;
	
	public Return(Instrument expression) {
		super("return", new String[] {"one or zero value"});
		this.expression = expression;
	}

	@Override
	public Object invoke(Env env, Object[] arguments) throws Exception {
		Object ret = new Void();
		if (expression != null)
			ret = eval(env, expression);
		
		return ret;
	}

}
