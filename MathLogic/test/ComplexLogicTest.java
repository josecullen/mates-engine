import org.junit.Assert;
import org.junit.Test;

import logic.OperandNode;
import logic.Operation;
import logic.OperatorNode;

public class ComplexLogicTest {
	
	@Test
	public void simpleAndComplex(){
		OperatorNode operator = new OperatorNode(Operation.AND);		
		OperandNode left1 = new OperandNode(true);
		OperatorNode right1 = new OperatorNode(Operation.AND);
		
		right1.setLeft(new OperandNode(true));
		right1.setRight(new OperandNode(true));
		
		operator.setLeft(left1);
		operator.setRight(right1);
		
		Assert.assertTrue(operator.getResult());
	}
	
	
}
