package org.nyer.pyjs;

import java.util.List;

public interface Assignable {
	public IFun assign(Env env, List<IFun> arguments) throws Exception;
}
