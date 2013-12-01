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
import org.nyer.pyjs.primitive.type.PjBoolean;

public class Or extends AbstractFun {
	private IFun expr1;
	private IFun expr2;
	
	public Or(IFun expr1, IFun expr2) {
		this.expr1 = expr1;
		this.expr2 = expr2;
	}

	@Override
	public IFun invoke(Env env) throws Exception {
		boolean v = checkBoolOperand(expr1.invoke(env));
		if (v == false) {
			v = checkBoolOperand(expr2.invoke(env));
			if (v == false) {
				return PjBoolean.False;
			}
		}
		return PjBoolean.True;
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}
}
