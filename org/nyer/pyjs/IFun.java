package org.nyer.pyjs;

import java.util.List;


public interface IFun {
	public IFun invoke(Env env, List<IFun> arguments) throws Exception;
	public String[] getParameters();
	public String getTypeStr(Env env) throws Exception;
}
