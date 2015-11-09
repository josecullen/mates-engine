import org.junit.Assert;
import org.junit.Test;

import logic.OperandNode;
import logic.Operation;
import logic.OperatorNode;

public class ExpressionTest {
	
	
	@Test
	public void simpleExpressionTest(){
		OperandNode left = new OperandNode("p", true);
		OperandNode right = new OperandNode("q", false);
		
		OperatorNode operator = new OperatorNode(Operation.AND);
		operator.setOperands(left, right);
		
		Assert.assertEquals("p and q", operator.getExpression());
		
		operator = new OperatorNode(Operation.OR);
		operator.setOperands(left, right);
			
		Assert.assertEquals("p or q", operator.getExpression());
		
		operator = new OperatorNode(Operation.THEN);
		operator.setOperands(left, right);
			
		Assert.assertEquals("p then q", operator.getExpression());		
	}
	
	
	@Test
	public void complexExpressionTest(){
		OperandNode left = new OperandNode("p", true);
		OperandNode rightLeft = new OperandNode("q", false);
		OperandNode rightRight = new OperandNode("r", false);
		
		OperatorNode subOperator = new OperatorNode(Operation.OR);
		subOperator.setOperands(rightLeft,rightRight);
		
		OperatorNode operator = new OperatorNode(Operation.AND);
		operator.setOperands(left, subOperator);
		
		Assert.assertEquals("p and ( q or r )", operator.getExpression());
		
		operator.setOperands(subOperator, left);
		
		Assert.assertEquals("( q or r ) and p", operator.getExpression());		
	}
	
	@Test
	public void simpleSignExpressionTest(){
		OperandNode left = new OperandNode("p", true);
		OperandNode right = new OperandNode("q", false);
		left.setSign(true);
		
		OperatorNode operator = new OperatorNode(Operation.AND);
		operator.setOperands(left, right);
		
		Assert.assertEquals("-p and q", operator.getExpression());
	}
	
	// para -(-q or s) que lo mostraba --q or s
	@Test
	public void doubleSignTest(){
		OperandNode left = new OperandNode("p", true);
		OperandNode right = new OperandNode("q", false);
		left.setSign(true);
		
		OperatorNode operator = new OperatorNode(Operation.AND);
		operator.setSign(true);
		operator.setOperands(left, right);
		
		Assert.assertEquals("-( -p and q )", operator.getExpression());

	}
	
	
	
}
