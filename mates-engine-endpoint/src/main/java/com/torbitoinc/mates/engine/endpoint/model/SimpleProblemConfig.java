package com.torbitoinc.mates.engine.endpoint.model;

public class SimpleProblemConfig {
	private AritmeticVariableConfig aritmeticVariableConfig;
	private OperationConfig operationConfig;
	private String expression;
	
	public AritmeticVariableConfig getAritmeticVariableConfig() {
		return aritmeticVariableConfig;
	}
	public void setAritmeticVariableConfig(AritmeticVariableConfig aritmeticVariableConfig) {
		this.aritmeticVariableConfig = aritmeticVariableConfig;
	}
	public OperationConfig getOperationConfig() {
		return operationConfig;
	}
	public void setOperationConfig(OperationConfig operationConfig) {
		this.operationConfig = operationConfig;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}

	
	
}
