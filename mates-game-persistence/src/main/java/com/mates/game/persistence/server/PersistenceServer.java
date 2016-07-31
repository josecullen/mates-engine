package com.mates.game.persistence.server;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.impl.CorsHandlerImpl;
import io.vertx.ext.web.impl.RouterImpl;
import io.vertx.rxjava.ext.web.handler.CorsHandler;
import math.db.model.game.config.GameConfig;
import math.db.model.game.instance.GameInstance;

public class PersistenceServer {
	private static Logger logger = LoggerFactory.getLogger(PersistenceServer.class);
	private static Morphia morphia = new Morphia();
	private static ObjectMapper mapper = new ObjectMapper();
	private static int PORT = 10999;
	
	public static void main(String[] args) {
//		morphia.map(GameConfig.class);
		morphia.map(GameInstance.class);

		Vertx vertx = Vertx.factory.vertx();
		HttpServer server = vertx.createHttpServer();
		
		JsonObject mongoConfig = new JsonObject()
				.put("db_name", "mathGame")
				.put("host", "localhost")
				.put("port", 27017);

		
		MongoClient mongoClient = MongoClient.createShared(vertx, mongoConfig);

		Router router = new RouterImpl(vertx);
		
//		router.route().handler(CorsHandler.create("vertx\\.io"));

		router.route().handler(new CorsHandlerImpl("*").allowedMethod(HttpMethod.GET));

//		router.route().handler(
//				CorsHandler.create("*")
//					.allowedMethod(HttpMethod.GET));
		
		router.post("/game-config").handler(context ->{
			logger.info("POST /game-config");
			
			context.request().bodyHandler(bodyHandler ->{
	
				Datastore ds = morphia.createDatastore(new com.mongodb.MongoClient(), "mathGame");		
				try {
					ds.save(mapper.readValue(bodyHandler.toString(), GameConfig.class));
					context.response().end("ok");
				} catch (Exception e) {
					logger.error("Error al intentar guardar configuración del juego", e);
					context.response().end("error");
				}
				
			});		
		});
		
		router.get("/game-config").handler(context ->{
			logger.info("GET /game-config");

			Datastore ds = morphia.createDatastore(new com.mongodb.MongoClient(), "mathGame");		
			String id = context.request().getParam("id");			
			GameConfig gameConfig = ds.get(GameConfig.class, id);
			
			try {
				String gameConfigJson = mapper.writeValueAsString(gameConfig);
				logger.info(gameConfigJson);
				context.response().end(gameConfigJson);
			} catch (Exception e) {
				logger.error("Se produjo un error al convertir el GameConfig en String", e);
			}
		});
		
		router.get("/game-instance").handler(context ->{
			logger.info("GET /game-instance");
			
			Datastore ds = morphia.createDatastore(new com.mongodb.MongoClient(), "mathGame");		
			String id = context.request().getParam("id");			
			GameInstance gameInstance = ds.get(GameInstance.class, id);
			
			try {
				String gameInstanceJson = mapper.writeValueAsString(gameInstance);
				logger.info(gameInstanceJson);
				context.response().end(gameInstanceJson);
			} catch (Exception e) {
				logger.error("Se produjo un error al convertir el GameConfig en String", e);
			}
		});
		
		server.requestHandler(router::accept).listen(PORT);

		logger.info("Server open in port " + PORT);
	}
	
	
	
}
