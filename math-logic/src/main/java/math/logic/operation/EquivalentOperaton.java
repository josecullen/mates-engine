package math.logic.operation;

import math.core.operation.Operation;

public class EquivalentOperaton extends Operation<Boolean> {

	@Override
	public Boolean operate(Boolean left, Boolean right) {
		return (left && !right) || (!left && right) ? false : true;
	}

	@Override
	public String getExpression() {
		return "eq";
	}

	@Override
	public String getLaTex() {
		return "\\Leftrightarrow";
	}

}
