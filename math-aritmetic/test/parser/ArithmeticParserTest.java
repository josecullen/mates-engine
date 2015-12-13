package parser;

import org.junit.Test;

import math.arithmetic.broadcast.ArithmeticBroadcastUtil;
import math.arithmetic.operand.ArithmeticVariable;
import math.arithmetic.operation.OperationUtil;
import math.arithmetic.parser.ArithmeticParser;
import math.arithmetic.tree.ArithmeticOperandNode;
import math.arithmetic.tree.ArithmeticOperationNode;
import math.core.parser.OperationParser;
import math.core.tree.OperationNode;

public class ArithmeticParserTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testNormal() {
		
		OperationNode<Double> operation = (OperationNode<Double>)ArithmeticParser.getInstance().parse("(a + b) + (5 + 2)");
		
		ArithmeticBroadcastUtil br = new ArithmeticBroadcastUtil(operation);
		System.out.println(br.getExpression());
	}
	/**
	 * Error cuando la variable está pegada al operador
	 */
	@Test
	public void test() {
		
		OperationNode<Double> operation = (OperationNode<Double>)ArithmeticParser.getInstance().parse("(a + b) + c");
		
		ArithmeticBroadcastUtil br = new ArithmeticBroadcastUtil(operation);
		System.out.println(br.getExpression());
	}

}
