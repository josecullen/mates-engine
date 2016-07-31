package math.arithmetic.operation;

import math.core.operation.Operation;


public class PowOperation extends Operation<Double> {

	@Override
	public Double operate(Double left, Double right) {
		return getSign() ? -(Math.pow(left, right)) : Math.pow(left, right);
	}

	@Override
	public String getExpression() {
		return "^";
	}

	@Override
	public String getLaTex() {
		return "^";
	}
}
