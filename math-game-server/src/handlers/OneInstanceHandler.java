package handlers;


import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import math.arithmetic.operand.ArithmeticVariable.RandomVariableInstancer;
import math.arithmetic.operation.OperationUtil.RandomOperationBuilder;
import math.arithmetic.problem.Problem;
import service.facade.ProblemFacade;

public class OneInstanceHandler {
	public static Handler<RoutingContext> GET = handler ->{
		
	};
	
	public static Handler<RoutingContext> POST = handler ->{
		handler.request().bodyHandler(data ->{
			
			System.out.println("data" +data.toString());			 
			
			JsonObject gameJson = new JsonObject(data.toString());
			JsonObject gameInstance = new JsonObject()
				.put("gameId", gameJson.getString("_id"))
				.put("gameName", gameJson.getString("name"));
			
			
			JsonArray gameLevels = gameJson.getJsonArray("levels");
			JsonArray instanceLevels = new JsonArray();

			gameLevels.forEach(value ->{
				JsonArray instanceProblems = new JsonArray();

				JsonObject level = (JsonObject)value;
				RandomVariableInstancer rvi = 
						new RandomVariableInstancer()
							.divisionFactor(level.getInteger("divisionFactor"))
							.max(level.getDouble("max"))
							.min(level.getDouble("min"))
							.probablySign(level.getDouble("probablySign"));
				RandomOperationBuilder rob =
						new RandomOperationBuilder()
							.buildWithProbablySign(level.getDouble("probablySign"))
							.buildWithThisOperations(level.getString("operations"));
				
				Problem problem = new Problem(level.getString("form"), rvi, rob);
				for(int i = 0; i < level.getInteger("repetitions"); i++){
					problem.renew();
					instanceProblems.add(ProblemFacade.parseProblemToJson(problem));
				}
				instanceLevels.add(instanceProblems);
			});
			
			gameInstance.put("levels", instanceLevels);
			gameInstance.put("collection", "instance");
			
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
	
	public static Handler<RoutingContext> PUT = handler ->{
		
		handler.request().bodyHandler(data ->{
			JsonObject gameJson = new JsonObject(data.toString());

		});

	};
	
	public static Handler<RoutingContext> DELETE = handler ->{

	};
}
