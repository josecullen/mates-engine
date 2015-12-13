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
	
	public static class RandomOperationBuilder{
		private double probablySign = 0; // Por defecto no hay signo
		private String operationsPattern = "+-/*\\^";		
		
		public RandomOperationBuilder() {}
		
		public RandomOperationBuilder buildWithProbablySign(double probablySign){
			this.probablySign = probablySign;
			return this;
		}
		
		public RandomOperationBuilder buildWithThisOperations(String operationsPattern){
			this.operationsPattern = operationsPattern;
			return this;
		}
		
		public Operation<Double> build(){
			Map<String, Operation<Double>> operations = getOperationMap(operationsPattern); //getValidOperations(operationsPattern);
			
			Operation<Double> operation = getRandomOperation(operations);
			operation.setSign(probablySign >= Math.random());
			
			return operation;
		}
			
		private Operation<Double> getRandomOperation(Map<String, Operation<Double>> operations){
			String[] keys = new String[operations.keySet().size()];
			operations.keySet().toArray(keys);
			return operations.get(keys[(int)(Math.random()*keys.length)]);
		}
		
	}
	
	
}
