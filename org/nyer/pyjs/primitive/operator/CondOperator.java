/**
 *   Copyright (c) Lei Ting. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *          the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package org.nyer.pyjs.primitive.operator;

import org.nyer.pyjs.ElementVisitor;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.AbstractFun;

public class CondOperator extends AbstractFun {
	private IFun condition;
	private IFun trueBody;
	private IFun falseBody;
	
	public CondOperator(IFun condition, IFun trueBody, IFun falseBody) {
		this.condition = condition;
		this.trueBody = trueBody;
		this.falseBody = falseBody;
	}

	@Override
	public IFun invoke(Env env) throws Exception {
		Boolean arg = checkBoolOperand(condition.invoke(env));
		if (arg)
			return trueBody.invoke(env);
		else 
			return falseBody.invoke(env);
	}

	
	public IFun getFalseBody() {
		return falseBody;
	}

	public IFun getCondition() {
		return condition;
	}

	public IFun getTrueBody() {
		return trueBody;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}
}
