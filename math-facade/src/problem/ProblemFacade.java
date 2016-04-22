package problem;

import math.arithmetic.problem.EquationLevel;
import math.arithmetic.problem.SimpleProblem;
import math.core.problem.Problem;
import builders.ArithmeticVariableBuilder;
import builders.EquationBuilder;
import builders.OperationBuilder;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class ProblemFacade {
	
	enum Param{
		// JsonObjects
		A("a"),
		X1("x"),
		X2("x2"),
		X3("x3"),
		
		// Params
		MAX("max"),
		MIN("min"),
		SIGN("sign"),
		DIV("div"),
		REP("rep"),
		OPERATIONS("operations"),
		FORM("form"),
		
		// Params Types
		TYPE("type"),
		SIMPLE("simple"),
		EQUATION_1("1"),
		EQUATION_2("2"),
		EQUATION_3("3");
		
		String text = "";
		private Param(String text) {
			this.text = text;
		}
		
		@Override
		public String toString() {		
			return super.toString();
		}
		
	}
	
	public static JsonObject getProblem(MultiMap params){
		JsonObject problem = new JsonObject();		
		Param type = Param.valueOf(params.get(Param.TYPE.name()));
		switch (type) {
			
			case EQUATION_1 :
			case EQUATION_2 :
			case EQUATION_3 :
				JsonObject a = new JsonObject(params.get("a"));
				JsonObject x1 = new JsonObject(params.get("x1"));
				JsonObject x2 = new JsonObject(params.get("x2"));
				JsonObject x3 = new JsonObject(params.get("x3"));
				
				
				EquationBuilder equationBuilder = new EquationBuilder()
					.buildWithLevel(EquationLevel.getInstance(Integer.valueOf(type.text)))
					.buildWithA(parseToArithmeticVariableBuilder(a))
					.buildWithX1(parseToArithmeticVariableBuilder(x1))
					.buildWithX2(parseToArithmeticVariableBuilder(x2))
					.buildWithX3(parseToArithmeticVariableBuilder(x3));
				
				problem = parseProblemToJson(equationBuilder.build());
				
				break;
			
			case SIMPLE:
				
				String formOfExpression = params.get(Param.FORM.name()); 
				
				SimpleProblem simpleProblem = new SimpleProblem(
						formOfExpression,
						parseToArithmeticVariableBuilder(params),
						parseToOperationBuilder(params)								
					);
				simpleProblem.renew();
				problem = parseProblemToJson( simpleProblem );
				
				break;
			default:
				break;
		
		}
		
		return problem;
	}
	
	/**
	 * 
	 * @param params debe contener los valores SIGN y OPERATIONS del enum Params
	 * @return
	 */
	private static OperationBuilder parseToOperationBuilder(MultiMap params){
		OperationBuilder operationBuilder =
				new OperationBuilder()
					.buildWithProbablySign(Double.valueOf(params.get(Param.SIGN.name())))
					.buildWithThisOperations(params.get(Param.OPERATIONS.name()));
		
		return operationBuilder;
	}
	
	
	/**
	 * 
	 * @param params debe contener los valores MIN, MAX, SIGN y DIV del enum Params
	 * @return
	 */
	private static ArithmeticVariableBuilder parseToArithmeticVariableBuilder(MultiMap params){
		
		ArithmeticVariableBuilder builder = new ArithmeticVariableBuilder()
			.min(Double.valueOf(params.get(Param.MIN.name())))
			.max(Double.valueOf(params.get(Param.MAX.name())))
			.probablySign(Double.valueOf(params.get(Param.SIGN.name())))
			.divisionFactor(Integer.valueOf(params.get(Param.DIV.name())));
			
		return builder;
	}	
	
	/**
	 * 
	 * @param conf debe contener los valores MIN, MAX, SIGN y DIV del enum Params
	 * @return
	 */
	private static ArithmeticVariableBuilder parseToArithmeticVariableBuilder(JsonObject conf){
		ArithmeticVariableBuilder builder = new ArithmeticVariableBuilder()
			.min(conf.getDouble("min"))
			.max(conf.getDouble("max"))
			.probablySign(conf.getDouble("sign"))
			.divisionFactor(conf.getInteger("div"));
		return builder;
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
