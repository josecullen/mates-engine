package math.arithmetic.broadcast;

import static org.junit.Assert.assertEquals;
import math.arithmetic.operation.PowOperation;
import math.arithmetic.tree.ArithmeticOperandNode;
import math.arithmetic.tree.ArithmeticOperationNode;

import org.junit.Test;

public class PowOperandTest {

	@Test
	public void test() {
		ArithmeticOperationNode rootNode = new ArithmeticOperationNode(new PowOperation());
		rootNode.setLeftNode(new ArithmeticOperandNode("a", 2, false));
		rootNode.setRightNode(new ArithmeticOperandNode("b", 3, false));
		
		System.out.println(rootNode.getOperation().getExpression());
		System.out.println(rootNode.getOperation().getLaTex());
		
		ArithmeticBroadcastUtil br = new ArithmeticBroadcastUtil(rootNode);
		System.out.println(br.getResult());
		System.out.println(br.getExpression());
		
		assertEquals(8.0, br.getResult(), 0.0d);
		assertEquals("( a ^ b )", br.getExpression());
		
	}

}
