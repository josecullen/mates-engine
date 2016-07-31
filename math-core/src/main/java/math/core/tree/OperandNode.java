package math.core.tree;

import math.core.operand.Variable;

public abstract class OperandNode<R> implements MathNode<R> {
	Variable<R> variable;	
	
	public abstract Variable<R> getVariable();
	public abstract void setVariable(Variable<R> variable);

}
