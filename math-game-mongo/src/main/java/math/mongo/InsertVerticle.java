package math.mongo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class InsertVerticle extends AbstractVerticle {
	MongoClient client;
	
	@Override
	public void start() throws Exception {		
		super.start();
		System.out.println("verticle InsertVerticle started");
		
		JsonObject mongoConfig = new JsonObject().put("db_name", "mathGame").put("host", "localhost").put("port", 27017);
		
		client = MongoClient.createShared(vertx, mongoConfig);
		
		vertx.eventBus().consumer("insert-instanceData").handler(insertInstanceData);

		
		MessageConsumer<String> insert = vertx.eventBus().consumer("insert");
		
		insert.handler(message -> {
			JsonObject insertJson = new JsonObject(message.body().toString());
			String collection = insertJson.getString("collection");
			insertJson.remove("collection");
			
			client.insert(collection, insertJson, res ->{
				  if (res.succeeded()) {
					  message.reply(collection+" saved! "+res.result());				    
				  } else {
					  System.out.println(res.cause());
					  message.reply(res.cause());
				  }
			});
		});


		MessageConsumer<String> insertGame = vertx.eventBus().consumer("insert-game");
		
		insertGame.handler(message -> {
			String jsonString = message.body().toString();
			client.insert("gameConfig", new JsonObject(jsonString), res->{
				if(res.succeeded()){
					message.reply("Se ha guardado la instancia satisfactoriamente: "+ jsonString);
				}else{
					message.reply("Ha ocurrido un inconveniente. . . "+ res.cause());
				}
			});
			
		});
	
		
	}
	
	Handler<Message<Object>> insertInstanceData = message ->{
		String jsonString = message.body().toString();
		client.insert("instanceGameData", new JsonObject(jsonString), res->{
			if(res.succeeded()){
				message.reply("Se ha guardado la instancia satisfactoriamente: "+ jsonString);
			}else{
				message.reply("Ha ocurrido un inconveniente. . . "+ res.cause());
			}
		});
		/*
		vertx.executeBlocking(future ->{
			ObjectMapper mapper = new ObjectMapper();
			InstanceGameData instanceGameData = null;
			try {
				JsonObject instanceJson = new JsonObject(message.body().toString());
				instanceJson.put("gameId", instanceJson.getJsonObject("gameId").getString("$oid"));
				
				if(instanceJson.getJsonObject("_id") != null){
					instanceJson.put("_id", instanceJson.getJsonObject("_id").getString("$oid"));
				}

				instanceGameData = mapper.readValue(instanceJson.encode(), InstanceGameData.class);
				Morphia morphia = new Morphia();
				morphia.map(InstanceGameData.class);
				Datastore ds = morphia.createDatastore(new com.mongodb.MongoClient(), "mathGame");
				ds.save(instanceGameData);
				future.complete("Se ha guardado la instancia satisfactoriamente: "+ instanceGameData);				
			} catch (Exception e) {
				e.printStackTrace();
				future.complete("Ha ocurrido un inconveniente. . . "+e.getMessage());
			}				

		}, false, res ->{
			message.reply(res.result().toString());					
		});	
		*/		
	};
	
	
}
