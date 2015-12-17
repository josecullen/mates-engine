package service.facade;


import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import math.arithmetic.problem.Problem;
import math.arithmetic.problem.SimpleProblem;
import builders.ArithmeticVariableBuilder;
import builders.OperationBuilder;

public class ProblemFacade {
	
	public static JsonObject getProblemJson(String form, ArithmeticVariableBuilder rvi, OperationBuilder rob){
		SimpleProblem problem = new SimpleProblem(form, rvi, rob);
		problem.renew();
		
		return null;
	}
	
	public static JsonObject parseProblemToJson(Problem problem){
		JsonObject jsonProblem = new JsonObject();
		
		JsonArray answerOptions = new JsonArray();
		for(String answer : problem.getAnswerOptions(5)){
			answerOptions.add(answer);
		}		
		
		JsonArray answers = new JsonArray();
		for(String answer : problem.getAnswer()){
			answers.add(answer);
		}	
		
		jsonProblem
			.put("problemExpression", problem.getProblemExpression())
			.put("solvedExpression", problem.getSolvedExpression())
			.put("correctAnswer", answers)
			.put("answerOptions", answerOptions);	
		
		return jsonProblem;
	}
	
	
}
