package mongo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class DeleteVerticle extends AbstractVerticle {
	@Override
	public void start() throws Exception {
		super.start();

		System.out.println("verticle DeleteVerticle started");

		JsonObject mongoConfig = new JsonObject().put("db_name", "mathGame").put("host", "localhost").put("port", 27017);

		MongoClient client = MongoClient.createShared(vertx, mongoConfig);

		MessageConsumer<String> delOneGame = vertx.eventBus().consumer("del-one-game");

		delOneGame.handler(message -> {
			System.out.println("DeleteVerticle del-one-game");			
			
			client.remove("game", new JsonObject().put("_id", message.body() ), res -> {
				if (res.succeeded()) {
					message.reply("game removed");
				} else {
					message.reply(res.cause());
				}
			});

		});

	}
}
