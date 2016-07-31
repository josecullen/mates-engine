package math.logic.operation;

import math.core.operation.Operation;

public class AndOperation extends Operation<Boolean>{

	@Override
	public Boolean operate(Boolean left, Boolean right) {
		return getSign() ? !(left && right) : left && right;
	}

	@Override
	public String getExpression() {
		return "and";
	}

	@Override
	public String getLaTex() {
		return "\\land";
	}
	
}
