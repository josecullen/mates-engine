package handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class OneGameHandler {
	
	public static Handler<RoutingContext> GET = handler ->{
//		handler.response().end("Devuelve el Juego correspondiente al id enviado");
		
		String gameId = handler.request().params().get("gameId");
		
		JsonObject mongoRequest = new JsonObject()
			.put("query", new JsonObject().put("_id", gameId))
			.put("collection", "game");
		
		handler.vertx().eventBus().send("find-one", mongoRequest.encode(), ar ->{
	    	 if (ar.succeeded()) {
	    		handler.response().end(ar.result().body().toString());		    		    
	    	 }else{
	    		 System.out.println("problem . . . "+ar.cause());
	    		 handler.response().end("Ha ocurrido un inconveniente. . . "+ar.cause());
	    	 }
	    });

		
		
	};
	
	public static Handler<RoutingContext> POST = handler ->{
		
		handler.request().bodyHandler(data ->{
			System.out.println("data" +data.toString());
			JsonObject game = new JsonObject(data.toString()).put("collection", "game");
			
			handler.vertx().eventBus().send("insert", game.encode(), ar ->{
		    	 if (ar.succeeded()) {
		    		handler.response().end("Se ha guardado el juego satisfactoriamente: "+ ar.result().body().toString());		    		    
		    	 }else{
		    		 System.out.println("problem . . . "+ar.cause());
		    		 handler.response().end("Ha ocurrido un inconveniente. . . "+ar.cause());
		    	 }
		    });
			
		});
	};
	
	public static Handler<RoutingContext> PUT = handler ->{
//		handler.response().end("Actualiza un Juego");
		
		handler.request().bodyHandler(data ->{
			System.out.println("data" +data.toString());
			JsonObject game = new JsonObject(data.toString()).put("collection", "game");
			
			handler.vertx().eventBus().send("save", game.encode(), ar ->{
		    	 if (ar.succeeded()) {
		    		handler.response().end("Se ha actualizado el juego satisfactoriamente: "+ ar.result().body().toString());		    		    
		    	 }else{
		    		 System.out.println("problem . . . "+ar.cause());
		    		 handler.response().end("Ha ocurrido un inconveniente. . . "+ar.cause());
		    	 }
		    });
			
		});
		
	};
	
	public static Handler<RoutingContext> DELETE = handler ->{
		String gameId = handler.request().params().get("gameId");
		
		handler.vertx().eventBus().send("del-one-game", gameId, ar ->{
			if(ar.succeeded()){
				handler.response().end(ar.result().body().toString());
			}
		});
		
		
	};
}