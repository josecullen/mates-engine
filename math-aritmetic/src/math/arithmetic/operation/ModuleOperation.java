package math.arithmetic.operation;

import math.core.operation.Operation;

public class ModuleOperation extends Operation<Integer> {

	@Override
	public Integer operate(Integer left, Integer right) {
		return left % right;
	}

	@Override
	public String getExpression() {
		return "%";
	}

	@Override
	public String getLaTex() {
		return "\\equiv";
	}

}
