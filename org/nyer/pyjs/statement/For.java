package org.nyer.pyjs.statement;

import org.nyer.pyjs.ElementVisitor;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.AbstractFun;
import org.nyer.pyjs.primitive.type.PjUndefined;

public class For extends AbstractFun {
	private IFun first;
	private IFun second;
	private IFun third;
	private IFun body;
	public For(IFun first, IFun second, IFun third, IFun body) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.body = body;
	}

	@Override
	public IFun invoke(Env env) throws Exception {
		if (first != null)
			first.invoke(env);
		
		boolean condition = true;
		if (second != null) 
			condition = checkBoolOperand(second.invoke(env));

		IFun ret = new PjUndefined();
		while (condition) {
			if (body != null)
				ret = body.invoke(env);
			
			if (third != null)
				third.invoke(env);
			
			if (second != null) 
				condition = checkBoolOperand(second.invoke(env));
		}
		
		return ret;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}
}
