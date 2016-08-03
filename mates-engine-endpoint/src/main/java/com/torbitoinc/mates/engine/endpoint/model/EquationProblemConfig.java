package com.torbitoinc.mates.engine.endpoint.model;

import math.arithmetic.problem.EquationLevel;

public class EquationProblemConfig {
	private AritmeticVariableConfig a, x1, x2, x3;
	private EquationLevel equationLevel;
	
	public AritmeticVariableConfig getA() {
		return a;
	}
	public void setA(AritmeticVariableConfig a) {
		this.a = a;
	}
	public AritmeticVariableConfig getX1() {
		return x1;
	}
	public void setX1(AritmeticVariableConfig x1) {
		this.x1 = x1;
	}
	public AritmeticVariableConfig getX2() {
		return x2;
	}
	public void setX2(AritmeticVariableConfig x2) {
		this.x2 = x2;
	}
	public AritmeticVariableConfig getX3() {
		return x3;
	}
	public void setX3(AritmeticVariableConfig x3) {
		this.x3 = x3;
	}
	public EquationLevel getEquationLevel() {
		return equationLevel;
	}
	public void setEquationLevel(EquationLevel equationLevel) {
		this.equationLevel = equationLevel;
	}
	
	
	
}
