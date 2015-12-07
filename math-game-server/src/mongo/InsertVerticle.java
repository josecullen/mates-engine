package mongo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;;

public class InsertVerticle extends AbstractVerticle {
	@Override
	public void start() throws Exception {		
		super.start();
		System.out.println("verticle InsertVerticle started");
		
		JsonObject mongoConfig = new JsonObject().put("db_name", "mathGame").put("host", "localhost").put("port", 27017);
		
		MongoClient client = MongoClient.createShared(vertx, mongoConfig);
		
//		MessageConsumer<String> insertGameBus = vertx.eventBus().consumer("insert-game");
//		
//		insertGameBus.handler(message -> {
//			JsonObject game = new JsonObject(message.body());
//			
//			client.insert("game", game, res ->{
//				  if (res.succeeded()) {
//					  message.reply("Game saved! "+res.result());				    
//				  } else {
//					  System.out.println(res.cause());
//					  message.reply(res.cause());
//				  }
//			});
//		});
		
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


	}
}
