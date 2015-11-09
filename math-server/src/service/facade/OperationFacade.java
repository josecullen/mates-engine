package service.facade;

import factory.OperationExpression;
import factory.OperationFactory;
import io.vertx.core.json.JsonObject;
import logic.OperatorNode;

public class OperationFacade {
	public static JsonObject createJsonFromExpression(String expression){
		JsonObject operation = new JsonObject();
		
		OperatorNode operator = (OperatorNode)OperationExpression.createFromExpression(expression);
		operation = getJsonOperator(operator);
		
		return operation;
	}
	
	public static JsonObject getJsonOperation(String expression, int signProbability){
		return getJsonOperator(OperationFactory.getOperation(expression, signProbability));
	}
	
	
	public static JsonObject getJsonOperator(OperatorNode operatorNode){
		JsonObject operator = new JsonObject();
		
		operator.put("expression", operatorNode.getExpression());
		
		return operator;
	}
	
}
