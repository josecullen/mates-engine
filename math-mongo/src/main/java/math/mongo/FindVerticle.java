package math.mongo;


import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import math.db.model.game.config.GameConfig;

public class FindVerticle extends AbstractVerticle {
	MongoClient client;
	
	@Override
	public void start() throws Exception {
		super.start();
		System.out.println("verticle FindVerticle started");
		
		JsonObject mongoConfig = new JsonObject().put("db_name", "mathGame").put("host", "localhost").put("port", 27017);
		
		client = MongoClient.createShared(vertx, mongoConfig);
		
		MessageConsumer<String> findAll = vertx.eventBus().consumer("find-all");	
		findAll.handler(findAllHandler);
	
		MessageConsumer<String> findOne = vertx.eventBus().consumer("find-one");
		
		findOne.handler(message -> {
			JsonObject jsonQuery = new JsonObject(message.body());		
			
			client.findOne(jsonQuery.getString("collection"), jsonQuery.getJsonObject("query"), new JsonObject(), res ->{
				 if (res.succeeded()) {				  
					  message.reply(res.result());				    
				  } else {			
					  message.reply(res.cause());
				  }
			});			
		});
		
		
		MessageConsumer<String> runCommand = vertx.eventBus().consumer("run-command");
		
		runCommand.handler(message ->{
			JsonObject jsonQuery = new JsonObject(message.body());		
			System.out.println(jsonQuery);
			client.runCommand("aggregate", jsonQuery , res->{
				 if (res.succeeded()) {		
					 System.out.println("\n");
					 System.out.println(res.result());
					 System.out.println(res.result().getJsonArray("result"));
					  message.reply(res.result().getJsonArray("result").toString());				    
				  } else {			
					  message.reply(res.cause());
				  }
			});
		});
		
		
		
		
		MessageConsumer<String> findAllGames = vertx.eventBus().consumer("find-all-games");
		
		findAllGames.handler(message -> {
			vertx.executeBlocking(future ->{
				Morphia morphia = new Morphia();	
				morphia.map(GameConfig.class);
				Datastore ds = morphia.createDatastore(new com.mongodb.MongoClient(), "mathGame");
				Query<GameConfig> query = ds.find(GameConfig.class);
				future.complete(query.asList());
				
			}, false, res-> message.reply(res.result())	);
			
		});
		
		
		
		
		
	}
	
	Handler<Message<String>> findAllHandler = message ->{
		System.out.println("FindVerticle "+message.body());
		client.find(message.body(),new JsonObject(), res ->{
			  if (res.succeeded()) {
				  System.out.println(res.result());
				  JsonArray result = new JsonArray();
				  res.result().forEach(game ->{
					  result.add(game);
				  });
				  
				  message.reply(result);				    
			  } else {			
				  message.reply(res.cause());
			  }
		});
	};
	
	
	
	
	
}
