package handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class AllPlayersHandler {
	public static Handler<RoutingContext> GET = handler ->{
		
		String instanceId = handler.request().getParam("instanceId");
		
		JsonObject proj1 = new JsonObject().put("$project",	new JsonObject().put("_id", "$_id").put("players", "$players"));
		JsonObject match = new JsonObject().put("$match",	new JsonObject().put("_id", instanceId));
		JsonObject unwind = new JsonObject().put("$unwind", "$players");
		JsonObject proj2 = new JsonObject().put("$project",
				new JsonObject().put("name", "$players.name").put("score", "$players.scoring.score").put("id", "$players.id"));
		JsonObject sort = new JsonObject().put("$sort", new JsonObject().put("score", -1));

		JsonObject command = new JsonObject().put("aggregate", "instanceGameData").put("pipeline",
				new JsonArray().add(proj1).add(match).add(unwind).add(proj2).add(sort));			

		handler.vertx().eventBus().send("run-command", command.encode(), ar -> {
			if (ar.succeeded()) {
				handler.response().end(ar.result().body().toString());			
			} else {
				handler.response().end(ar.cause().getMessage());
			}
		});
		
//		handler.response().end("Devuelve la lista de Juegos");

	};
	
	public static Handler<RoutingContext> DELETE = handler ->{
		handler.response().end("Elimina la lista de Juegos");
	};
}
