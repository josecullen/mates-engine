package com.torbitoinc.mates.engine.endpoint.model;

import math.arithmetic.problem.EquationLevel;

public class SystemEquationConfig extends CommonProblemConfig{
	private EquationLevel equationLevel;
	private AritmeticVariableConfig constant, x;
	private OperationConfig operationConfig;
	
	public EquationLevel getEquationLevel() {
		return equationLevel;
	}
	public void setEquationLevel(EquationLevel equationLevel) {
		this.equationLevel = equationLevel;
	}
	public AritmeticVariableConfig getConstant() {
		return constant;
	}
	public void setConstant(AritmeticVariableConfig constant) {
		this.constant = constant;
	}
	public AritmeticVariableConfig getX() {
		return x;
	}
	public void setX(AritmeticVariableConfig x) {
		this.x = x;
	}
	public OperationConfig getOperationConfig() {
		return operationConfig;
	}
	public void setOperationConfig(OperationConfig operationConfig) {
		this.operationConfig = operationConfig;
	}
	
	
	
}

