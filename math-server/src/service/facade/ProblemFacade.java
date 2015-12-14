package service.facade;


import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import math.arithmetic.problem.SimpleProblem;
import builders.ArithmeticVariableBuilder;
import builders.OperationBuilder;

public class ProblemFacade {
	
	public static JsonObject getProblemJson(String form, ArithmeticVariableBuilder rvi, OperationBuilder rob){
		SimpleProblem problem = new SimpleProblem(form, rvi, rob);
		problem.renew();
		
		return null;
	}
	
	public static JsonObject parseProblemToJson(SimpleProblem problem){
//		System.out.println(problem);
		JsonObject jsonProblem = new JsonObject();
		
		JsonArray answerOptions = new JsonArray();
		for(String answer : problem.getAnswerOptions()){
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
