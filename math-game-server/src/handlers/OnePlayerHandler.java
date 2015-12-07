package handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class OnePlayerHandler {
	public static Handler<RoutingContext> GET = handler ->{

		handler.response().end("Devuelve el Juego correspondiente al id enviado");
	};
	
	public static Handler<RoutingContext> POST = handler ->{
		handler.request().bodyHandler(data ->{
			JsonObject params = new JsonObject(data.toString());
			handler.vertx().eventBus().send("push-player", params.encode(), ar ->{
				
				if (ar.succeeded()) {
					JsonObject result = new JsonObject(ar.result().body().toString());

					System.out.println(result.encodePrettily());
		    		handler.response().end(result.encode());
		    	 }
				
				else{
		    		 JsonObject result = new JsonObject()
						.put("status", "FAIL")
						.put("message", "Ha ocurrido un inconveniente. . . "+ar.cause());
		    		 
		    		 System.out.println("problem . . . "+ar.cause());
		    		 handler.response().end("Ha ocurrido un inconveniente. . . "+ar.cause());
		    	 }
				
			});		
		});
		
	};
	
	public static Handler<RoutingContext> PUT = handler ->{
		System.out.println("PUT PLAYER");
		handler.request().bodyHandler(data ->{
			JsonObject requestScore = new JsonObject(data.toString());
			JsonObject scoring = new JsonObject()
					.put("id", requestScore.getString("id"))
					.put("score", requestScore.getInteger("score"))
					.put("corrects", requestScore.getInteger("corrects"))
					.put("incorrects", requestScore.getInteger("incorrects"));

			
			handler.vertx().eventBus().send("update-player-scoring", scoring.encode(), ar ->{
				if (ar.succeeded()) {
					JsonObject result = new JsonObject()
						.put("status", "SUCCESS")
						.put("message", "Se ha actualizado el jugador satisfactoriamente: "+ ar.result().body().toString());
					System.out.println(result);
		    		handler.response().end(result.encodePrettily());
		    	 }else{
		    		 JsonObject result = new JsonObject()
						.put("status", "FAIL")
						.put("message", "Ha ocurrido un inconveniente. . . "+ar.cause());		    		 
		    		 System.out.println("problem . . . "+ar.cause());
		    		 handler.response().end("Ha ocurrido un inconveniente. . . "+ar.cause());
		    	 }
			});		
		});
	};
	
	public static Handler<RoutingContext> DELETE = handler ->{
		handler.response().end("Elimina el Juego correspondiente al id enviado");
	};
}
