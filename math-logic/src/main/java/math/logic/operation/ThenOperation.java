package math.logic.operation;

import math.core.operation.Operation;

public class ThenOperation extends Operation<Boolean>{

	@Override
	public Boolean operate(Boolean left, Boolean right) {
		return left && !right ? false : true;
	}

	@Override
	public String getExpression() {
		return "then";
	}

	@Override
	public String getLaTex() {
		return "\\Rightarrow";
	}

}
