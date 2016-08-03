package com.torbitoinc.mates.engine.endpoint.model;

public class LogicProblemConfig {
	private String expression;
	private double probablySign;
	private String operations;
	
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expressionForm) {
		this.expression = expressionForm;
	}
	public double getProbablySign() {
		return probablySign;
	}
	public void setProbablySign(double probablySign) {
		this.probablySign = probablySign;
	}
	public String getOperations() {
		return operations;
	}
	public void setOperations(String operations) {
		this.operations = operations;
	}
	
	
}
