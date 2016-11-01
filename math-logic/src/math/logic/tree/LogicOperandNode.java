package math.logic.tree;

import math.core.operand.Variable;
import math.core.tree.OperandNode;
import math.logic.operand.LogicVariable;

public class LogicOperandNode extends OperandNode<Boolean>{
	LogicVariable variable;
	
	@Override
	public Variable<Boolean> getVariable() {
		return variable;
	}

	@Override
	public void setVariable(Variable<Boolean> variable) {
		this.variable = new LogicVariable(variable);		
	}

}
