package com.torbitoinc.mates.engine.endpoint.model;

public class SimpleModuleConfig extends CommonProblemConfig{
	private AritmeticVariableConfig a, pow, mod;

	public AritmeticVariableConfig getA() {
		return a;
	}

	public void setA(AritmeticVariableConfig a) {
		this.a = a;
	}

	public AritmeticVariableConfig getPow() {
		return pow;
	}

	public void setPow(AritmeticVariableConfig pow) {
		this.pow = pow;
	}

	public AritmeticVariableConfig getMod() {
		return mod;
	}

	public void setMod(AritmeticVariableConfig mod) {
		this.mod = mod;
	}
	
	
	
}
