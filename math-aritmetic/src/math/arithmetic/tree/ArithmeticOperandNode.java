package math.arithmetic.tree;

import math.arithmetic.operand.ArithmeticVariable;
import math.core.operand.Variable;
import math.core.tree.OperandNode;

public class ArithmeticOperandNode extends OperandNode<Double> {
	ArithmeticVariable variable;
	
	public ArithmeticOperandNode() {	}
	
	public ArithmeticOperandNode(String variable, double value, boolean sign){
		this.variable = new ArithmeticVariable(variable, value, sign);
	}	
	
	public ArithmeticVariable getVariable(){
		return variable;
	}
	
	@Override
	public void setVariable(Variable<Double> variable){
		this.variable = (ArithmeticVariable) variable;
	}
	
	

	
}
