package math.arithmetic.broadcast;

import java.util.ArrayList;
import java.util.List;

import math.arithmetic.operand.ArithmeticVariable;
import math.arithmetic.operation.OperationUtil;
import math.arithmetic.operation.OperationUtil.RandomOperationBuilder;
import math.arithmetic.tree.ArithmeticOperandNode;
import math.core.tree.MathNode;
import math.core.tree.broadcast.BroadcastAction;
import math.core.tree.broadcast.BroadcastType;

public class ArithmeticBroadcastUtil {
	MathNode<Double> tree;
	
	public ArithmeticBroadcastUtil(MathNode<Double> tree) {
		this.tree = tree;
	}
	
	public List<ArithmeticVariable> getVariablesBroadcast(){		
		BroadcastAction<List<ArithmeticVariable>> getVariables = new BroadcastAction<List<ArithmeticVariable>>(BroadcastType.OPERANDS);
		
		getVariables.setOperandAction(operandNode ->{
			if(getVariables.getResult() == null)
				getVariables.setResult(new ArrayList<ArithmeticVariable>());			
			return getVariables.getResult().add(
					((ArithmeticOperandNode) operandNode).getVariable()
			);	
		});
		
		return getVariables.runOn(tree);
	}
	
	@SuppressWarnings("unchecked")
	public double getResult(){
		BroadcastAction<Double> broadcastGetResult = new BroadcastAction<Double>();
		broadcastGetResult.setOperandAction(operandNode ->{					
			
			if (operandNode.getVariable().getSign())
				return -((double)operandNode.getVariable().getValue());
			else
				return operandNode.getVariable().getValue();
		});
		
		broadcastGetResult.setOperationAction(operationNode -> {
			return operationNode.getOperation().operate(
					broadcastGetResult.runOn(operationNode.leftNode), 
					broadcastGetResult.runOn(operationNode.rightNode)
			);

		});
		
		return broadcastGetResult.runOn(tree);
	}
	
	public String getExpression(){
		BroadcastAction<String> getExpression = new BroadcastAction<String>();
		
		getExpression.setOperandAction(operandNode ->{
			return operandNode.getVariable().getVariable();
		});
		
		getExpression.setOperationAction(operationNode ->{
			String expression = getExpression.runOn(operationNode.getLeftNode()) +" "+ operationNode.getOperation().getExpression() +" "+ getExpression.runOn(operationNode.getRightNode());
			return operationNode.getOperation().getSign() 
					? "-( "+expression +" )"
					: "( "+expression+" )";
		});	
	
		return getExpression.runOn(tree);
	}
	
	@SuppressWarnings("unchecked")
	public void setVariables(List<ArithmeticVariable> list){
		BroadcastAction<String> setVariables = new BroadcastAction<String>(BroadcastType.OPERANDS);
		
		setVariables.setOperandAction(operandNode ->{
			list.forEach(variable ->{
				if(operandNode.getVariable().getVariable().equals(variable.getVariable())){
					operandNode.setVariable(variable);
				}
			});
			
			return "";
		});
	}
	
	@SuppressWarnings("unchecked")
	public void setOperations(String operationsPattern, double probablySign){
		BroadcastAction<String> setOperations = new BroadcastAction<String>(BroadcastType.OPERATIONS);
		
		setOperations.setOperationAction(operationNode ->{
			operationNode.setOperation(
					new OperationUtil
							.RandomOperationBuilder()
							.buildWithThisOperations(operationsPattern)
							.buildWithProbablySign(probablySign)
							.build());
			
			return "";
		});		
		setOperations.runOn(tree);		
	}
	
	@SuppressWarnings("unchecked")
	public void setOperations(RandomOperationBuilder rob){
		BroadcastAction<String> setOperations = new BroadcastAction<String>(BroadcastType.OPERATIONS);
		
		setOperations.setOperationAction(operationNode ->{
			operationNode.setOperation(rob.build());
			
			return "";
		});		
		setOperations.runOn(tree);		
	}
	

	
	
}
