package handlers;


import builders.ArithmeticVariableBuilder;
import builders.EquationBuilder;
import builders.OperationBuilder;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import math.arithmetic.problem.EquationLevel;
import math.arithmetic.problem.SimpleProblem;
import service.facade.ProblemFacade;

public class OneInstanceHandler {
	public static Handler<RoutingContext> GET = handler ->{
		System.out.println("OneInstanceHandler GET");
		
		String type = handler.request().params().get("type");
		String instanceId = handler.request().params().get("instanceId");
		
		if("RANDOM".equals(type)){
			String gameId = handler.request().params().get("gameId");
			
			JsonObject dbRequest = new JsonObject()
				.put("collection", "game")
				.put("query", new JsonObject().put("_id", gameId));
			
			handler.vertx().eventBus().send("find-one", dbRequest.encode(), ar ->{
				if(ar.succeeded()){
					
					JsonObject game = new JsonObject(ar.result().body().toString());
					
					JsonObject response = new JsonObject()
						.put("_id", instanceId)
						.put("gameId", gameId)
						.put("type", type)
						.put("levels", createInstanceLevels(game.getJsonArray("levels")));
					
					handler.response().end(response.encode());
				}
					
			});			
		}else{
			JsonObject dbRequest = new JsonObject()
			.put("collection", "instance")
			.put("query", new JsonObject().put("_id", instanceId));
			
			handler.vertx().eventBus().send("find-one", dbRequest.encode(), ar ->{
				if(ar.succeeded()){
					
					JsonObject instance = new JsonObject(ar.result().body().toString());						
					handler.response().end(instance.encode());
				}
					
			});			
			
//			handler.response().end(":(");
	
		};
	};
	
	public static Handler<RoutingContext> POST = handler ->{
		handler.request().bodyHandler(data ->{
			
			System.out.println("data" +data.toString());			 
			
			JsonObject requestJson = new JsonObject(data.toString());
			
			JsonObject gameInstance = new JsonObject()
				.put("collection", "instance")	
				.put("gameId", requestJson.getString("_id"))
				.put("gameName", requestJson.getString("name"))
				.put("type", requestJson.getString("type"));
				
			if("ONE_INSTANCE_GAME".equals(gameInstance.getString("type"))){
				gameInstance.put("levels", createInstanceLevels(requestJson.getJsonArray("levels")));
			}
			
			handler.vertx().eventBus().send("insert", gameInstance.encode(), ar ->{
		    	 if (ar.succeeded()) {
		    		handler.response().end("Se ha guardado la instancia satisfactoriamente: "+ ar.result().body().toString());		    		    
		    	 }else{
		    		 System.out.println("problem . . . "+ar.cause());
		    		 handler.response().end("Ha ocurrido un inconveniente. . . "+ar.cause());
		    	 }
		    });
			
		});
	};
	
	private static final String 
		SIMPLE = "SIMPLE",
		EQUATION_1 = "EQUATION_1",
		EQUATION_2 = "EQUATION_2",
		EQUATION_3 = "EQUATION_3";
	
	private static JsonArray createInstanceLevels(JsonArray gameLevels){
		JsonArray instanceLevels = new JsonArray();

		gameLevels.forEach(value ->{

			JsonObject level = (JsonObject)value;
			
			switch (level.getString("type")) {
				case SIMPLE:
					instanceLevels.add(createSimpleLevelProblems(level));
					break;
				case EQUATION_1:
					instanceLevels.add(createEquationLevelProblems(level, 1));
					break;
				case EQUATION_2:
					instanceLevels.add(createEquationLevelProblems(level, 2));
					break;
				case EQUATION_3:
					instanceLevels.add(createEquationLevelProblems(level, 3));
					break;

			}
			
			
			
			
		});
		
		return instanceLevels;
	}
	
	private static JsonArray createSimpleLevelProblems(JsonObject level){
		JsonArray instanceProblems = new JsonArray();

		
		ArithmeticVariableBuilder rvi = 
				new ArithmeticVariableBuilder()
					.divisionFactor(level.getInteger("divisionFactor"))
					.max(level.getDouble("max"))
					.min(level.getDouble("min"))
					.probablySign(level.getDouble("probablySign"));
		
		OperationBuilder rob =
				new OperationBuilder()
					.buildWithProbablySign(level.getDouble("probablySign"))
					.buildWithThisOperations(level.getString("operations"));
		
		SimpleProblem problem = new SimpleProblem(level.getString("form"), rvi, rob);
		
		for(int i = 0; i < level.getInteger("repetitions"); i++){
			problem.renew();
			instanceProblems.add(ProblemFacade.parseProblemToJson(problem));
		}
		
		return instanceProblems;
	}
	
	private static JsonArray createEquationLevelProblems(JsonObject level, int equationGrade){
		JsonArray instanceProblems = new JsonArray();
		
		JsonObject a = level.getJsonObject("a");
		JsonObject x1 = level.getJsonObject("x1");
		JsonObject x2 = level.getJsonObject("x2");
		JsonObject x3 = level.getJsonObject("x3");
		
		EquationLevel eqLevel = null;
		
		switch (equationGrade) {
			case 1:
				eqLevel = EquationLevel.LEVEL_1;
				break;
			case 2:
				eqLevel = EquationLevel.LEVEL_2;
				break;
			case 3:
				eqLevel = EquationLevel.LEVEL_3;
				break;
		}
		
		EquationBuilder equationBuilder = new EquationBuilder()
			.buildWithLevel(eqLevel)
			.buildWithA(parseToArithmeticVariableBuilder(a))
			.buildWithX1(parseToArithmeticVariableBuilder(x1))
			.buildWithX2(parseToArithmeticVariableBuilder(x2))
			.buildWithX3(parseToArithmeticVariableBuilder(x3));
		
		for(int i = 0; i < level.getInteger("repetitions"); i++){
			instanceProblems.add(ProblemFacade.parseProblemToJson(equationBuilder.build()));
		}
		
		return instanceProblems;
	}
	
	private static ArithmeticVariableBuilder parseToArithmeticVariableBuilder(JsonObject conf){
		ArithmeticVariableBuilder builder = new ArithmeticVariableBuilder()
			.min(conf.getDouble("min"))
			.max(conf.getDouble("max"))
			.probablySign(conf.getDouble("sign"))
			.divisionFactor(conf.getInteger("div"));
		return builder;
	}
	
	
	public static Handler<RoutingContext> PUT = handler ->{
		
		handler.request().bodyHandler(data ->{
			System.out.println("data" +data.toString());
			JsonObject instance = new JsonObject(data.toString()).put("collection", "instance");
			
			handler.vertx().eventBus().send("save", instance.encode(), ar ->{
		    	 if (ar.succeeded()) {
		    		handler.response().end(ar.result().body().toString());		    		    
		    	 }else{
		    		 System.out.println("problem . . . "+ar.cause());
		    		 handler.response().end(ar.result().body().toString());
		    	 }
		    });
			
		});


	};
	
	public static Handler<RoutingContext> DELETE = handler ->{
		String instanceId = handler.request().params().get("instanceId");
		
		JsonObject deleteRequest = new JsonObject()
			.put("collection", "instance")
			.put("query", new JsonObject().put("_id", instanceId));
		
		handler.vertx().eventBus().send("remove", deleteRequest.encode(), ar ->{
			if(ar.succeeded()){
				handler.response().end(ar.result().body().toString());
			}
		});		
		
	};
}
