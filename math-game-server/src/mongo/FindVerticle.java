package mongo;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class FindVerticle extends AbstractVerticle {
	@Override
	public void start() throws Exception {
		super.start();
		System.out.println("verticle FindVerticle started");
		
		JsonObject mongoConfig = new JsonObject().put("db_name", "mathGame").put("host", "localhost").put("port", 27017);
		
		MongoClient client = MongoClient.createShared(vertx, mongoConfig);
		
		MessageConsumer<String> findAll = vertx.eventBus().consumer("find-all");
		
		findAll.handler(message -> {
			System.out.println("FindVerticle getAllGames");
			client.find(new JsonObject(message.body()).getString("collection"),new JsonObject(), res ->{
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
		});

		
		
		
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
		
		
		
	}
}
