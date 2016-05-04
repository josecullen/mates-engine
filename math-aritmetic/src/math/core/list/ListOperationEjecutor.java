package math.core.list;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.BinaryOperator;

import math.arithmetic.broadcast.PowOperandTest;
import math.arithmetic.operation.AdditionOperation;
import math.arithmetic.operation.DivisionOperation;
import math.arithmetic.operation.MultiplicationOperation;
import math.arithmetic.operation.OperationUtil;
import math.arithmetic.operation.PowOperation;
import math.arithmetic.operation.SubstractionOperation;
import math.core.operation.Operation;

public class ListOperationEjecutor{
	LinkedList<OperationNode> list;
//	public void add(Operation operation, double left, double right){
//		list.add(new OperationNode(operation, left, right));
//	}
	
	public Setter left(double left){
		list = new LinkedList<>();
		list.add(new OperationNode(new AdditionOperation(), 0, left));
		return new Setter();
	}
	
	
	
	public double resolve(){
		double result = 0;
		
		BinaryOperator<OperationNode> b2 = (acc, el) -> {
			if(OperationUtil.isPriority(el.getOperation())){
				acc.setRight(el.getOperation().operate(acc.getRight(), el.getRight())); 
			}else{
				el.setLeft(acc.getOperation().operate(acc.getLeft(), acc.getRight()));
				acc = el;
			}			
			return acc;			
		};
		
		OperationNode lastOperation = list.stream().reduce(
				new OperationNode(new AdditionOperation(), 0, list.getFirst().left), 
				b2
		);
		
		
		return lastOperation.getOperation().operate(lastOperation.getLeft(), lastOperation.getRight());
	}
	
//	private double operate(double acum, Iterator<OperationNode> it){
//		
//		OperationNode operation = it.next();
//		double right = operation.right;
//		//0 + 2 x 3 + 4 x 2
//		if(OperationUtil.isPriority(operation.getOperation())){
//			double parcialResult = operation.getOperation().operate(acum, right);
//			return it.hasNext() 
//					? 	operate(parcialResult, it) 
//					: 	parcialResult;
//		}else{
//			return it.hasNext() 
//					?	operation.getOperation().operate(acum, operate(operation.getOperation().operate(acum, right), it)) 
//					: 	operation.getOperation().operate(acum, right);
//		}
//	}
	
	public class Setter{
		public Setter and(Operation<Double> operation, double right){
			list.add(new OperationNode(operation, list.getLast().getLeft(), right));
			return this;
		}
		
		public Setter add(double right){
			return and(new AdditionOperation(), right);
		}
		
		public Setter substract(double right){
			return and(new SubstractionOperation(), right);
		}
		
		public Setter divide(double right){
			return and(new DivisionOperation(), right);
		}
		
		public Setter multiply(double right){
			return and(new MultiplicationOperation(), right);
		}
		
		public Setter pow(double right){
			return and(new PowOperation(), right);
		}
		
	}
	
}

class OperationNode{
	double left, right;
	Operation<Double> operation;
	
	public OperationNode(Operation operation, double left, double right) {
		this.operation = operation;
		this.left = left;
		this.right = right;
	}

	public double getLeft() {
		return left;
	}

	public void setLeft(double left) {
		this.left = left;
	}

	public double getRight() {
		return right;
	}

	public void setRight(double right) {
		this.right = right;
	}

	public Operation<Double> getOperation() {
		return operation;
	}

	public void setOperation(Operation<Double> operation) {
		this.operation = operation;
	}
	
	
}
