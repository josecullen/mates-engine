package math.logic.tree;

import math.core.operation.Operation;
import math.core.tree.MathNode;
import math.core.tree.OperationNode;

public class LogicOperationNode extends OperationNode<Boolean> {
	Operation<Boolean> operation;
		
	public LogicOperationNode() {}
	public LogicOperationNode(Operation<Boolean> operation){
		this.operation = operation;
	}
	public LogicOperationNode(Operation<Boolean> operation, MathNode<Boolean> left, MathNode<Boolean> right){
		this.operation = operation;
		setLeftNode(left);
		setRightNode(right);
	}	
	
	
	@Override
	public Operation<Boolean> getOperation() {
		return operation;
	}

	@Override
	public void setOperation(Operation<Boolean> operation) {
		this.operation = operation;
	}
	
	

}
