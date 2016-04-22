package math.arithmetic.operation;

import java.util.HashMap;
import java.util.Map;

import math.core.operation.Operation;

public class OperationUtil {		
	
	public static Map<String, Operation<Double>> getOperationMap(){
		return getOperationMap("-+*/^");
	}
	
	public static Map<String, Operation<Double>> getOperationMap(String operationsPattern){
		HashMap<String, Operation<Double>> operations = new HashMap<String, Operation<Double>>();
		if("+".matches("["+operationsPattern+"]"))
			operations.put("+", new AdditionOperation());
		if("-".matches("["+operationsPattern+"]"))
			operations.put("-", new SubstractionOperation());
		if("*".matches("["+operationsPattern+"]"))
			operations.put("*", new MultiplicationOperation());
		if("/".matches("["+operationsPattern+"]"))
			operations.put("/", new DivisionOperation());
		if("^".matches("["+operationsPattern+"]"))
			operations.put("^", new PowOperation());
		
		return operations;
	}
	
	public static Operation<Double> getInstanceFromExpression(String expression){
		return getOperationMap().get(expression);
	}
	
	
}
