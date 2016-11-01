package math.arithmetic.tree;

import math.core.operation.Operation;
import math.core.tree.MathNode;
import math.core.tree.OperationNode;

public class ArithmeticOperationNode extends OperationNode<Double>{
	Operation<Double> operation;
	
	public ArithmeticOperationNode() {}	
	public ArithmeticOperationNode(Operation<Double> operation){
		this.operation = operation;
	}
	public ArithmeticOperationNode(Operation<Double> operation, MathNode<Double> left, MathNode<Double> right){
		this.operation = operation;
		setNodes(left, right);
	}
	
	public Operation<Double> getOperation(){
		return operation;
	}
	
	public void setOperation(Operation<Double> operation){
		this.operation = operation;
	}
	
	public void setNodes(MathNode<Double> left, MathNode<Double> right){
		this.leftNode = left;
		this.rightNode = right;
	}
	
	
	
	
	
	

}
