package org.nyer.pyjs.statement;

import java.util.List;

import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.Instrument;
import org.nyer.pyjs.primitive.operator.ValueOp;
import org.nyer.pyjs.primitive.type.Void;

public class For extends ValueOp {
	private List<Instrument> second;
	private List<Instrument> third;
	private List<Instrument> body;
	public For(List<Instrument> second, List<Instrument> third, List<Instrument> body) {
		super(null);
		this.second = second;
		this.third = third;
		this.body = body;
	}

	@Override
	public IFun invoke(Env env, List<IFun> arguments) throws Exception {
		IFun condRet = new Void();
		for (int i = 0, s = second.size();i < s;i ++) {
			condRet = second.get(i).invoke(env);
		}
		boolean condition = checkBoolOperand(env, condRet);

		IFun ret = new Void();
		while (condition) {
			for (int i = 0, s = body.size();i < s;i ++) {
				ret = body.get(i).invoke(env);
			}
			
			for (int i = 0, s = third.size();i < s;i ++) {
				third.get(i).invoke(env);
			}
			
			for (int i = 0, s = second.size();i < s;i ++) {
				condRet = second.get(i).invoke(env);
			}
			condition = checkBoolOperand(env, condRet);
		}
		
		return ret;
	}

}
