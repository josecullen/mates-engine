package math.logic.broadcast;

import math.core.operand.Variable;
import math.core.tree.MathNode;
import math.core.tree.broadcast.BroadcastAction;
import math.core.tree.broadcast.BroadcastType;
import math.logic.operand.LogicVariable;
import math.logic.operation.LogicOperationUtil;
import math.core.tree.OperandNode;
import math.core.tree.OperationNode;

public class LogicBroadcastUtil {
	MathNode<Boolean> tree;
	
	public LogicBroadcastUtil(MathNode<Boolean> tree) {
		this.tree = tree;
	}
	
	@SuppressWarnings("unchecked")
	public boolean getResult(){
		BroadcastAction<Boolean> broadcastGetResult = new BroadcastAction<Boolean>();
		broadcastGetResult.setOperandAction(operandNode ->{					
			return ((OperandNode<Boolean>) operandNode).getVariable().getValue();
		});
		
		broadcastGetResult.setOperationAction(operationNode -> {
			return ((OperationNode<Boolean>) operationNode).getOperation().operate(
					broadcastGetResult.runOn(operationNode.leftNode), 
					broadcastGetResult.runOn(operationNode.rightNode)
			);

		});
		
		return broadcastGetResult.runOn(tree);
	}
	public Variable<Boolean> getVariableToShow(){
		return innerStatus.getVariableToShow();
	}
	
	final InnerStatus innerStatus = new InnerStatus();
	public BroadcastAction<String> createProblemExpressionAction(){
		
		BroadcastAction<String> problemExpression = new BroadcastAction<String>();
		
		int variableToShow = (int) (getVariablesCount() * Math.random());		
		
		problemExpression.setOperandAction(operandNode ->{
			String valueString = "";
			boolean sign = operandNode.getVariable().getSign();
			if(sign)
				valueString = "\\sim";	
			
			if(innerStatus.getCount() == variableToShow){
				innerStatus.setVariableToShow(operandNode.getVariable());
				valueString += operandNode.getVariable().getVariable();
			}else{
				valueString += sign ? !(Boolean)operandNode.getVariable().getValue() ? "T" : "F"
									: (Boolean)operandNode.getVariable().getValue() ? "T" : "F";	
			}
			
			innerStatus.addOne();
		
			
			return valueString;
		});
		
		problemExpression.setOperationAction(operationNode ->{
			String expression = ""+problemExpression.runOn(
					operationNode.getLeftNode()) +" "+ 
					operationNode.getOperation().getLaTex()+" "+ 
					problemExpression.runOn(operationNode.getRightNode());
			
			return operationNode.getOperation().getSign() 
					? "\\sim( "+expression +" )"
					: "( "+expression+" )";
		});
		return problemExpression;
	}
	
	private int count = 0;
	public int getVariablesCount(){
		count = 0;
		
		BroadcastAction<Integer> countVariables = new BroadcastAction<Integer>(BroadcastType.OPERANDS);
		countVariables.setOperandAction(operandNode ->	count++);		
		countVariables.runOn(tree);
		
		return count;
	}
	
	public void randomizeOperations() {
		BroadcastAction<String> randomizeOperations = new BroadcastAction<>(BroadcastType.OPERATIONS);
		randomizeOperations.setOperationAction(operationNode ->{
			operationNode.setOperation(LogicOperationUtil.getRandom());
			randomizeOperations.runOn(operationNode.leftNode);
			randomizeOperations.runOn(operationNode.rightNode);
			return "";
		});
			
		randomizeOperations.runOn(tree);		
	}
	
	class InnerStatus{
		private Variable<Boolean> variableToShow;
		public Variable<Boolean> getVariableToShow() {
			return variableToShow;
		}

		public void setVariableToShow(Variable<Boolean> variableToShow) {
			this.variableToShow = variableToShow;
		}

		private int count = 0;
		public void setCount(int count){
			this.count = count;
		}
		
		public void addOne(){
			count++;
		}
		
		public int getCount(){
			return count;
		}
	}

	
	
}
