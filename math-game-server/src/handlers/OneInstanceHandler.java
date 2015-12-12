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
			handler.response().end(":(");
	
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
	
	
	private static JsonArray createInstanceLevels(JsonArray gameLevels){
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
		
		return instanceLevels;
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
