package handlers;

import java.io.IOException;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import math.db.model.game.config.GameConfig;
import math.db.model.game.instance.GameInstance;
import math.db.model.player.InstanceGameData;


public class OneInstanceHandler {

	/**
	 * Crea una instancia de un juego
	 */
	public static Handler<RoutingContext> GET = handler ->{
		System.out.println("OneInstanceHandler GET");
		
		String instanceId = handler.request().params().get("instanceId");
	 	String gameId = handler.request().params().get("gameId");
			
	 	System.out.println("instanceId : "+instanceId);
	 	System.out.println("gameId : "+gameId);
	 	
		Morphia morphia = new Morphia();
		morphia.map(GameConfig.class);
		Datastore ds = morphia.createDatastore(new com.mongodb.MongoClient(), "mathGame");

		GameConfig gameConfig = ds.get(GameConfig.class, gameId);

		GameInstance gameInstance = new GameInstance(instanceId, gameId, GameCreator.createLevels(gameConfig));

		try {
			String gameInstanceString = new ObjectMapper().writeValueAsString(gameInstance);
			System.out.println(new JsonObject(gameInstanceString).encodePrettily());
			handler.response().end(new JsonObject(gameInstanceString).encode());
		} catch (IOException ex) {
			handler.response().end(ex.getMessage());
		}					
	
	};
	
	public static Handler<RoutingContext> POST = handler ->{
		handler.request().bodyHandler(data ->{
			
			System.out.println("data" +data.toString());			 
			
			JsonObject requestJson = new JsonObject(data.toString());
			
			InstanceGameData instanceGameData = new InstanceGameData(
					requestJson.getString("instanceName"), 
					requestJson.getString("gameId"));
				
			Morphia morphia = new Morphia();
			morphia.map(InstanceGameData.class);
			Datastore ds = morphia.createDatastore(new com.mongodb.MongoClient(), "mathGame");
			
			ds.save(instanceGameData);				
			
		});
	};
	
	public static Handler<RoutingContext> PUT = handler ->{
		
		handler.request().bodyHandler(data ->{
			System.out.println("PUT oneInstanceHandler data" + data.toString());
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
		
		JsonObject deleteRequest = new JsonObject().put("collection", "instance").put("query",
				new JsonObject().put("_id", instanceId));
		
		handler.vertx().eventBus().send("remove", deleteRequest.encode(), ar ->{
			if(ar.succeeded()){
				handler.response().end(ar.result().body().toString());
			}
		});		
		
	};
	
	

	
}
