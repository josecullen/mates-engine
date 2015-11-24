package math.arithmetic.parser;

import java.util.function.Function;

import math.arithmetic.operand.ArithmeticVariable;
import math.arithmetic.operation.OperationUtil;
import math.arithmetic.tree.ArithmeticOperandNode;
import math.arithmetic.tree.ArithmeticOperationNode;
import math.core.parser.OperationParser;
import math.core.tree.OperandNode;
import math.core.tree.OperationNode;

public class ArithmeticParser {

	
	public static OperationParser getInstance(){
		return new OperationParser(newOperandInstance, newOperationInstance);
	}
	
	protected static Function<String, OperandNode> newOperandInstance = expression ->{
		ArithmeticOperandNode node = new ArithmeticOperandNode();
		ArithmeticVariable variable = new ArithmeticVariable(expression);
		node.setVariable(variable);
		return node;
	};
	protected static Function<String, OperationNode> newOperationInstance = expression ->{
		ArithmeticOperationNode node = new ArithmeticOperationNode(OperationUtil.getInstanceFromExpression(expression));
		return node;
	};
	
	
}
