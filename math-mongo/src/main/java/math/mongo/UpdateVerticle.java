package math.mongo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class UpdateVerticle extends AbstractVerticle {
	@Override
	public void start() throws Exception {		
		super.start();
		System.out.println("verticle InsertVerticle started");
		
		JsonObject mongoConfig = new JsonObject().put("db_name", "mathGame").put("host", "localhost").put("port", 27017);
		
		MongoClient client = MongoClient.createShared(vertx, mongoConfig);
		
		
		MessageConsumer<String> save = vertx.eventBus().consumer("save");
		
		save.handler(message ->{
			JsonObject params = new JsonObject(message.body().toString());

			String collection = params.getString("collection");
			
			params.remove("collection");
			
			client.save(collection, params, res->{
				if (res.succeeded()) {
					  
					  JsonObject result = new JsonObject()
								.put("status", "SUCCESS")
								.put("message", "Se ha actualizado el jugador satisfactoriamente");
								

					  message.reply(result.encode());				    
				  } else {
					  System.out.println(res.cause());
					  message.reply(res.cause());
				  }
			});
			
		});
		
		
		
		
		MessageConsumer<String> pushPlayer = vertx.eventBus().consumer("push-player");	
		
		
		pushPlayer.handler(message -> {
			
			JsonObject params = new JsonObject(message.body().toString());
			
			JsonObject player = new JsonObject()
				.put("name", params.getString("playerName"))
				.put("id", getId(params.getString("playerName")));
						
			JsonObject instance = params.getJsonObject("instance");
			JsonObject updateJson = new JsonObject().put("$push", new JsonObject().put("players", player));
			System.out.println(updateJson.encodePrettily());			
			
			client.update("instanceGameData", new JsonObject().put("_id", instance.getString("_id")), updateJson, res ->{
				  if (res.succeeded()) {
					  
					  JsonObject result = new JsonObject()
								.put("status", "SUCCESS")
								.put("message", "Se ha agregado el jugador satisfactoriamente")
								.put("id", player.getString("id"));

					  message.reply(result.encode());				    
				  } else {
					  System.out.println(res.cause());
					  message.reply(res.cause());
				  }
			});			
			
		});
		
		MessageConsumer<String> updatePlayerScoring = vertx.eventBus().consumer("update-player-scoring");
		
		updatePlayerScoring.handler(message -> {
			
			JsonObject scoring = new JsonObject(message.body().toString());
			JsonObject matchJson = new JsonObject()
				.put("players.id", scoring.getString("id"))
				.put("_id", scoring.getString("instanceId"));
			
			scoring.remove("id");
			scoring.remove("instanceId");
			
			JsonObject updateJson = new JsonObject()
					.put("$set", new JsonObject().put("players.$.scoring", scoring));
			
			System.out.println(matchJson.encodePrettily());
			System.out.println(updateJson.encodePrettily());
			
			client.update("instanceGameData", matchJson, updateJson, res ->{
				  if (res.succeeded()) {					  
					  message.reply("OK - pushed player! "+res.result());
					  System.out.println("publishing update: " + matchJson.getString("instanceId"));
					  vertx.eventBus().publish(matchJson.getString("_id"), "update");
				  } else {
					  System.out.println(res.cause());
					  message.reply(res.cause());
				  }
			});			
			
		});


	}
	
	public String getId(String name){
		String[] letters = name.split("");
		StringBuffer id = new StringBuffer();
		
		for(String letter : letters){
			id
				.append(letter)
				.append((int)(Math.random()*999));
		}
		return id.toString();
	}
}
