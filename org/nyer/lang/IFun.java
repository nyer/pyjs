package org.nyer.lang;

public interface IFun {
	public Object invoke(Env env, Object[] arguments) throws Exception;
	public String getName();
	public String[] getParameters();
}
