package com.torbitoinc.mates.engine.endpoint.model;

import builders.ArithmeticVariableBuilder;

/**
 * This class is a request endpoint class to build AritmeticVariables
 * 
 * @author josecullen
 *
 */
public class AritmeticVariableConfig{
	private double min = 0, max = 5, probablySign = 0;

	private int divisionFactor = 1;

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getProbablySign() {
		return probablySign;
	}

	public void setProbablySign(double probablySign) {
		this.probablySign = probablySign;
	}

	public int getDivisionFactor() {
		return divisionFactor;
	}

	public void setDivisionFactor(int divisionFactor) {
		this.divisionFactor = divisionFactor;
	}
	
	public ArithmeticVariableBuilder createBuilder(){
		return new ArithmeticVariableBuilder()
				.divisionFactor(divisionFactor)
				.max(max)
				.min(min)
				.probablySign(probablySign)
				.variableNames("abcdefg");
	}
	
}
