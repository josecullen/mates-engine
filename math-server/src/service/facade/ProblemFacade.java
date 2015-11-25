package service.facade;


import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import math.arithmetic.operand.ArithmeticVariable.RandomVariableInstancer;
import math.arithmetic.operation.OperationUtil.RandomOperationBuilder;
import math.arithmetic.problem.Problem;

public class ProblemFacade {
	
	public static JsonObject getProblemJson(String form, RandomVariableInstancer rvi, RandomOperationBuilder rob){
		Problem problem = new Problem(form, rvi, rob);
		problem.renew();
		
		return null;
	}
	
	public static JsonObject parseProblemToJson(Problem problem){
		JsonObject jsonProblem = new JsonObject();
		
		JsonArray answerOptions = new JsonArray();
		for(Double answer : problem.getAnswerOptions()){
			answerOptions.add(answer);
		}		
		
		jsonProblem
			.put("problemExpression", problem.getProblemExpression())
			.put("solvedExpression", problem.getSolvedExpression())
			.put("correctAnswer", problem.getAnswer())
			.put("answerOptions", answerOptions);	
		
		return jsonProblem;
	}
	
	
}
