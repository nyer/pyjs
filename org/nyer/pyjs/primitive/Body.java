package org.nyer.pyjs.primitive;

import java.util.List;

import org.nyer.pyjs.ElementVisitor;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.PjUndefined;

public class Body extends AbstractFun {
	private IFun[] body;
	public Body(IFun[] body) {
		this.body = body;
	}
	
	public Body(List<IFun> body) {
		this.body = body.toArray(new IFun[body.size()]);
	}

	@Override
	public IFun invoke(Env env) throws Exception {
		IFun ret = new PjUndefined();
		for (int i = 0, s = body.length; i < s; i ++)
			ret = body[i].invoke(env);
		
		return ret;
	}


	@Override
	public void accept(ElementVisitor visitor) {
	}

}
