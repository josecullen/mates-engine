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
	public void test() {
//		OperationParser.setNewOperandFunction(expression ->{
//			ArithmeticOperandNode node = new ArithmeticOperandNode();
//			ArithmeticVariable variable = new ArithmeticVariable(expression);
//			node.setVariable(variable);
//			return node;
//		});
//		
//		OperationParser.setNewOperationFunction(expression ->{
//			ArithmeticOperationNode node = new ArithmeticOperationNode(OperationUtil.getInstanceFromExpression(expression));
//			return node;
//		});
		
		OperationNode<Double> operation = (OperationNode<Double>)ArithmeticParser.getInstance().parse("(a + b) + (5 + 2)");
		
//		OperationNode<Double> operation = (OperationNode<Double>)OperationParser.parse("(a + b) + (5 + 2)");
		ArithmeticBroadcastUtil br = new ArithmeticBroadcastUtil(operation);
		System.out.println(br.getExpression());
	}

}
