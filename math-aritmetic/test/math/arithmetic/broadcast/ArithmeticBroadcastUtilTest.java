package math.arithmetic.broadcast;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import math.arithmetic.broadcast.ArithmeticBroadcastUtil;
import math.arithmetic.operand.ArithmeticVariable;
import math.arithmetic.operation.AdditionOperation;
import math.arithmetic.tree.ArithmeticOperandNode;
import math.arithmetic.tree.ArithmeticOperationNode;

public class ArithmeticBroadcastUtilTest {
	ArithmeticOperandNode operandLeft1 = new ArithmeticOperandNode("p", 2, false);
	ArithmeticOperandNode operandRight1 = new ArithmeticOperandNode("q", 2, false);
	ArithmeticOperandNode operandLeft2 = new ArithmeticOperandNode("r", 2, false);
	ArithmeticOperandNode operandRight2 = new ArithmeticOperandNode("s", 2, false);
	
	ArithmeticOperationNode operationL = new ArithmeticOperationNode(new AdditionOperation(), operandLeft1, operandRight1);
	ArithmeticOperationNode operationR = new ArithmeticOperationNode(new AdditionOperation(), operandLeft2, operandRight2);

	ArithmeticOperationNode operationParent = new ArithmeticOperationNode(new AdditionOperation(), operationL, operationR);

	ArithmeticBroadcastUtil broadcastUtil = new ArithmeticBroadcastUtil(operationParent);
	
	
	@Test
	public void getVariablesTest() {
		List<ArithmeticVariable> list = broadcastUtil.getVariablesBroadcast();
		
		assertEquals(4, list.size());
		assertNotEquals(list.get(0), list.get(1));
		assertNotEquals(list.get(0), list.get(2));
		assertNotEquals(list.get(0), list.get(3));
	}
	
	@Test
	public void setVariablesTest(){
		List<ArithmeticVariable> list = broadcastUtil.getVariablesBroadcast();
		list.get(0).setValue(4d);
		list.get(2).setValue(4d);
		
		broadcastUtil.setVariables(list);
		
		assertEquals(12, broadcastUtil.getResult(), 0.0);		
	}
	
	@Test
	public void getResultTest(){
		assertEquals(8, broadcastUtil.getResult(), 0.0);
	}
	
	@Test
	public void getExpressionTest(){
		String expression = broadcastUtil.getExpression();
		System.out.println(expression);
	}
	
	@Test
	public void testNegativeOperations(){
		operandLeft1.getVariable().setSign(true);
		System.out.println(broadcastUtil.getExpression());
		assertEquals(8, broadcastUtil.getResult(), 0.1);
	}
	

}
