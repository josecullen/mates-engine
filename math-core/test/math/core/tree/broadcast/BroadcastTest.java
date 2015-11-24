package math.core.tree.broadcast;

import static org.junit.Assert.*;
import java.util.HashMap;

import org.junit.Test;

public class BroadcastTest {

//	@Test
//	public void test() {
//		ArithmeticOperandNode operandLeft1 = new ArithmeticOperandNode("p", 2, false);
//		ArithmeticOperandNode operandRight1 = new ArithmeticOperandNode("q", 2, false);
//		ArithmeticOperandNode operandLeft2 = new ArithmeticOperandNode("r", 2, false);
//		ArithmeticOperandNode operandRight2 = new ArithmeticOperandNode("s", 2, false);
//		
//		ArithmeticOperationNode operationL = new ArithmeticOperationNode(new AdditionOperation(), operandLeft1, operandRight1);
//		ArithmeticOperationNode operationR = new ArithmeticOperationNode(new AdditionOperation(), operandLeft2, operandRight2);
//
//		ArithmeticOperationNode operationParent = new ArithmeticOperationNode(new AdditionOperation(), operationL, operationR);
//		
//		BroadcastAction<Double> broadcastGetResult = new BroadcastAction<Double>(BroadcastType.OPERATIONS);
//		broadcastGetResult.setOperationAction(operationNode -> {
//			return operationNode.getOperation().operate(operationNode.getLeftNode().getResult(), operationNode.getRightNode().getResult());
//		});
//		
//		BroadcastAction<HashMap<String, Double>> broadcastGetVariables = new BroadcastAction<HashMap<String, Double>>(BroadcastType.OPERANDS);
//		broadcastGetVariables.setOperandAction(
//			operandNode -> {
//				if(broadcastGetVariables.getResult() == null){
//					broadcastGetVariables.setResult(new HashMap<String, Double>());
//				}
//				return broadcastGetVariables.getResult().put(operandNode.getExpression(), (Double) operandNode.getResult());	
//			}
//		);
//		
//		final HashMap<String, Double> map = new HashMap<>();
//		map.put("p", 4d);
//		map.put("q", 2d);
//		map.put("r", 4d);
//		map.put("s", 2d);
//		BroadcastAction<String> setVariablesBroadcast = new BroadcastAction<String>(BroadcastType.OPERANDS);
//		setVariablesBroadcast.setOperandAction(operandNode -> {
//			((ArithmeticOperandNode) operandNode).getVariable().setValue(map.get(((ArithmeticOperandNode) operandNode).getVariable().getVariable()));
//			return " ";
//		});
//		
//	
//		System.out.println(broadcastGetResult.runOn(operationParent));
//		System.out.println(broadcastGetVariables.runOn(operationParent));
//		setVariablesBroadcast.runOn(operationParent);
//		System.out.println(broadcastGetVariables.runOn(operationParent));
//		System.out.println(broadcastGetResult.runOn(operationParent));
//
//	}

}
