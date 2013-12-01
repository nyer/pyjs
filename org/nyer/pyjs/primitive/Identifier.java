/**
 *   Copyright (c) Lei Ting. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *          the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package org.nyer.pyjs.primitive;

import org.nyer.pyjs.ElementVisitor;
import org.nyer.pyjs.Env;
import org.nyer.pyjs.IFun;
import org.nyer.pyjs.primitive.type.Value;

public class Identifier extends Value implements Assignable {

	public Identifier(String value) {
		super( value);
	}

	@Override
	public IFun invoke(Env env) throws Exception {
		String name = getValue();
		IFun obj = env.lookUp(name);
		if (obj == null)
			throw new Exception("undefined identifier, " + name);
		
		return obj;
	}
	
	@Override
	public String getValue() {
		return (String) super.getValue();
	}
	
	@Override
	public String getTypeStr(Env env) throws Exception {
		if (env == null)
			return "unknow";
		return this.invoke(env).getTypeStr(env);
	}
	
	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public IFun toAssign(IFun value) {
		return new IdentifierAssign(this, value);
	}
}
