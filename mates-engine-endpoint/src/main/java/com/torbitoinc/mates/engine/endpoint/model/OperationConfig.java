package com.torbitoinc.mates.engine.endpoint.model;

import builders.OperationBuilder;

public class OperationConfig {
	private double probablySign = 0; 
	private String operationsPattern = "+-/*\\^";
	
	public double getProbablySign() {
		return probablySign;
	}
	public void setProbablySign(double probablySign) {
		this.probablySign = probablySign;
	}
	public String getOperationsPattern() {
		return operationsPattern;
	}
	public void setOperationsPattern(String operationsPattern) {
		this.operationsPattern = operationsPattern;
	}
	
	public OperationBuilder createBuilder(){
		return new OperationBuilder()
						.buildWithProbablySign(probablySign)
						.buildWithThisOperations(operationsPattern);
	}

	
}
