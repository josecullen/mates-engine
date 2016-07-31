package math.arithmetic.operand;

import math.core.operand.Variable;
import math.core.problem.Problem;

public class ArithmeticVariable implements Variable<Double> {
	double value;
	String variable; 
	boolean sign;
	Problem problem;
	@Override
	public Double getValue() {
		return sign ? -value : value;
	}

	@Override
	public void setValue(Double value) {
		this.value = value;
	}
	
	public void setVariable(String variable){
		this.variable = variable;
	}

	@Override
	public String getVariable() {
		return variable;
	}

	@Override
	public boolean getSign() {
		return sign;
	}

	@Override
	public void set(Variable<Double> variable) {
		this.variable = variable.getVariable();
		this.value = variable.getValue();
		this.sign = variable.getSign();
	}
	
	public ArithmeticVariable() {}
	
	public ArithmeticVariable(String variable){
		this.variable = variable;
	}
	
	public ArithmeticVariable(String variable, double value, boolean sign){
		this.value = value;
		this.variable = variable;
		this.sign = sign;
	}	
	
	@Override
	public void setSign(boolean sign) {
		this.sign = sign;
	}
	
	
}
