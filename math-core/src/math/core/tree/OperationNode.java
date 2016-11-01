package math.core.tree;

import math.core.operation.Operation;

public abstract class OperationNode<R> implements MathNode<R> {
	public MathNode<R> leftNode, rightNode;

	public MathNode<R> getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(MathNode<R> leftNode) {
		this.leftNode = leftNode;
	}
	
	public void setOperands(MathNode<R> left, MathNode<R> right){
		setLeftNode(left);
		setRightNode(right);
	}

	public MathNode<R> getRightNode() {
		return rightNode;
	}
	public void setRightNode(MathNode<R> rightNode) {
		this.rightNode = rightNode;
	}
	
	public abstract Operation<R> getOperation();
	public abstract void setOperation(Operation<R> operation);
}
