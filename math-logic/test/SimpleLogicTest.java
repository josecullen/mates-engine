import org.junit.Assert;
import org.junit.Test;

import logic.OperandNode;
import logic.Operation;
import logic.OperatorNode;

public class SimpleLogicTest {

	
	@Test
	public void simpleLogicAnd(){
		OperatorNode operator = new OperatorNode(Operation.AND);		
		OperandNode left1 = new OperandNode(true);
		OperandNode right1 = new OperandNode(true);
		operator.setLeft(left1);
		operator.setRight(right1);
		
		Assert.assertTrue(operator.getResult());
		
		left1.setValue(false);
		Assert.assertFalse(operator.getResult());
		
		left1.setValue(true);
		right1.setValue(false);
		Assert.assertFalse(operator.getResult());
		
		
		left1.setValue(false);
		right1.setValue(false);
		Assert.assertFalse(operator.getResult());
	}
	
	@Test
	public void simpleLogicOr(){
		OperatorNode operator = new OperatorNode(Operation.OR);		
		OperandNode left1 = new OperandNode(true);
		OperandNode right1 = new OperandNode(true);
		operator.setLeft(left1);
		operator.setRight(right1);
		
		Assert.assertTrue(operator.getResult());
		
		left1.setValue(false);
		Assert.assertTrue(operator.getResult());
		
		left1.setValue(true);
		right1.setValue(false);
		Assert.assertTrue(operator.getResult());
		
		
		left1.setValue(false);
		right1.setValue(false);
		Assert.assertFalse(operator.getResult());
	}
	
	@Test
	public void simpleLogicThen(){
		OperatorNode operator = new OperatorNode(Operation.THEN);		
		OperandNode left1 = new OperandNode(true);
		OperandNode right1 = new OperandNode(true);
		operator.setLeft(left1);
		operator.setRight(right1);
		
		Assert.assertTrue(operator.getResult());
		
		left1.setValue(false);
		Assert.assertTrue(operator.getResult());
		
		left1.setValue(true);
		right1.setValue(false);
		Assert.assertFalse(operator.getResult());
		
		
		left1.setValue(false);
		right1.setValue(false);
		Assert.assertTrue(operator.getResult());
	}
	
	@Test
	public void simpleSignLogic(){
		OperatorNode operator = new OperatorNode(Operation.AND);		
		OperandNode left = new OperandNode(true);
		OperandNode right = new OperandNode(true);
		
		operator.setOperands(left, right);
		
		Assert.assertTrue(operator.getResult());
		
		left.setSign(true);
		Assert.assertFalse(operator.getResult());		
	}
		

}
