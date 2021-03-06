/**
 *   Copyright (c) Lei Ting. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *          the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package org.nyer.pyjs.statement;

import org.nyer.pyjs.ElementVisitor;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.AbstractFun;
import org.nyer.pyjs.primitive.type.PjUndefined;

public class While extends AbstractFun {
	private IFun condition;
	private IFun trueBody;
	
	public While(IFun condition, IFun trueBody) {
		this.condition = condition;
		this.trueBody = trueBody;
	}

	@Override
	public IFun invoke(Env env) throws Exception {
		boolean cond = checkBoolOperand(condition.invoke(env));

		IFun ret = new PjUndefined();
		while (cond) {
			ret = trueBody.invoke(env);
			cond = checkBoolOperand(condition.invoke(env));
		}
		
		return ret;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

	public IFun getCondition() {
		return condition;
	}

	public IFun getTrueBody() {
		return trueBody;
	}
}
