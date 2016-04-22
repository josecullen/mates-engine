package math.logic.parser;

import java.util.function.Function;

import math.core.operation.Operation;
import math.core.parser.OperationParser;
import math.core.tree.OperandNode;
import math.core.tree.OperationNode;
import math.logic.operand.LogicVariable;
import math.logic.operation.LogicOperationUtil;
import math.logic.tree.LogicOperandNode;
import math.logic.tree.LogicOperationNode;

public class LogicParser {
	private static double probablySign_ = 0;
	
	
	public static OperationParser getInstance(){
		return new OperationParser(newOperandInstance, newOperationInstance);
	}
	
	public static OperationParser getInstance(double probablySign){
		probablySign_ = probablySign;
		return new OperationParser(newOperandInstance, newOperationInstance);
	}
	
	protected static Function<String, OperandNode> newOperandInstance = expression ->{
		LogicOperandNode node = new LogicOperandNode();
		LogicVariable variable = new LogicVariable(expression, probablySign_);
		node.setVariable(variable);
		return node;
	};
	
	protected static Function<String, OperationNode> newOperationInstance = expression ->{
		LogicOperationNode node = null;
		Operation<Boolean> operation = LogicOperationUtil.getInstanceFromExpression(expression);
		
		if(operation != null){
			operation.setSign(probablySign_ >= Math.random());
			node = new LogicOperationNode(operation);	
		}
		
		return node;
	};
}
