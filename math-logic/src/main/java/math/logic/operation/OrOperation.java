package math.logic.operation;

import math.core.operation.Operation;

public class OrOperation extends Operation<Boolean>{

	@Override
	public Boolean operate(Boolean left, Boolean right) {
		return getSign() ? !(left || right) : left || right;
	}

	@Override
	public String getExpression() {
		return "or";
	}

	@Override
	public String getLaTex() {
		return "\\lor";
	}

}
