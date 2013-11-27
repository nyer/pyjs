package org.nyer.pyjs.statement;

import java.util.List;

import org.nyer.pyjs.ElementVisitor;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.Instrument;
import org.nyer.pyjs.primitive.operator.ValueOp;
import org.nyer.pyjs.primitive.type.PjUndefined;

public class For extends ValueOp {
	private Instrument[] second;
	private Instrument[] third;
	private Instrument[] body;
	public For(List<Instrument> second, List<Instrument> third, List<Instrument> body) {
		super(null);
		this.second = second.toArray(new Instrument[second.size()]);
		this.third = third.toArray(new Instrument[third.size()]);
		this.body = body.toArray(new Instrument[body.size()]);
	}

	@Override
	public IFun invoke(Env env, IFun[] arguments) throws Exception {
		IFun condRet = new PjUndefined();
		for (int i = 0, s = second.length;i < s;i ++) {
			condRet = second[i].invoke(env);
		}
		boolean condition = checkBoolOperand(env, condRet);

		IFun ret = new PjUndefined();
		while (condition) {
			for (int i = 0, s = body.length;i < s;i ++) {
				ret = body[i].invoke(env);
			}
			
			for (int i = 0, s = third.length;i < s;i ++) {
				third[i].invoke(env);
			}
			
			for (int i = 0, s = second.length;i < s;i ++) {
				condRet = second[i].invoke(env);
			}
			condition = checkBoolOperand(env, condRet);
		}
		
		return ret;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}
}
