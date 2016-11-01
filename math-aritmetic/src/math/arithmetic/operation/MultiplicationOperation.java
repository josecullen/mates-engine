package math.arithmetic.operation;

import math.core.operation.Operation;

public class MultiplicationOperation extends Operation<Double> {

	@Override
	public Double operate(Double left, Double right) {
		return getSign() ? -(left * right) : (left * right) ;
	}

	@Override
	public String getExpression() {
		return "*";
	}

	@Override
	public String getLaTex() {
		return "\\centerdot";
	}

}
