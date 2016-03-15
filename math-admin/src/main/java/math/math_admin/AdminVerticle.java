package math.math_admin;

import org.bson.types.ObjectId;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.impl.StaticHandlerImpl;
import io.vertx.ext.web.impl.RouterImpl;
import math.mongo.DeleteVerticle;
import math.mongo.FindVerticle;
import math.mongo.InsertVerticle;
import math.mongo.UpdateVerticle;

public class AdminVerticle extends AbstractVerticle {
	Logger logger = LoggerFactory.getLogger(AdminVerticle.class);
	private static final int PORT = 10001;

	@Override
	public void start() throws Exception {
		super.start();

		vertx.deployVerticle(new InsertVerticle(), res -> {
			if (res.succeeded()) {
				System.out.println("Deployment id is: " + res.result());
			} else {
				System.out.println("Deployment failed!");
			}
		});
		
		vertx.deployVerticle(new FindVerticle(), res -> {
			if (res.succeeded()) {
				System.out.println("Deployment id is: " + res.result());
			} else {
				System.out.println("Deployment failed!");
			}
		});

		vertx.deployVerticle(new UpdateVerticle(), res -> {
			if (res.succeeded()) {
				System.out.println("Deployment id is: " + res.result());
			} else {
				System.out.println("Deployment failed!");
			}
		});

		vertx.deployVerticle(new DeleteVerticle(), res -> {
			if (res.succeeded()) {
				System.out.println("Deployment id is: " + res.result());
			} else {
				System.out.println("Deployment failed!");
			}
		});

		
		
		Router router = new RouterImpl(vertx);

		router.route("/").handler(handler -> {
			logger.debug("route /");
			handler.response().sendFile("webroot/index.html");
		});
		
		router.post("/arithmetic/v2/game").handler(context ->{
			logger.debug("post /arithmetic/v2/game");
			
			context.request().bodyHandler(data ->{
				logger.info("data : "+data.toString());				
				vertx.eventBus().send("insert-game", data.toString(), res->{
					if(res.succeeded()){
						context.response().end(RestResponse.createSucess("Se ha guardado el juego satisfactoriamente", data.toString()) );
					}else{
						context.response().end(RestResponse.createError(ErrorType.INSERT_DB_ERROR, "Ha ocurrido un inconveniente. . . "+res.cause()));
					}
				});				
			});
			
		});		
		
		router.put("/arithmetic/v2/game").handler(context->{
			context.request().bodyHandler(data ->{
				JsonObject gameConfig = new JsonObject(data.toString()).put("collection", "gameConfig");

				context.vertx().eventBus().send("save", gameConfig.encode(), ar ->{
			    	 if (ar.succeeded()) {
			    		 context.response().end(RestResponse.createSucess("Se ha actualizado el juego satisfactoriamente: ", ar.result().body().toString()));		    		    
			    	 }else{
			    		 context.response().end(RestResponse.createError(ErrorType.INSERT_DB_ERROR, "Ha ocurrido un inconveniente. . . "+ar.cause()));
			    	 }
			    });				
			});			
		});


		
		router.get("/arithmetic/v2/games").handler(context ->{
			logger.debug("get /arithmetic/v2/games");
			
			vertx.eventBus().send("find-all", "gameConfig", res->{					
				logger.info("result find-all :"+res.result().body().toString());
				JsonArray gameConfigs = (JsonArray)res.result().body();
				context.response().end(gameConfigs.encode());
			});
			
		});		
		
		router.post("/arithmetic/v2/instanceGameData").handler(context ->{
			logger.debug("/arithmetic/v2/instanceData");

			context.request().bodyHandler(data ->{
				logger.info("data : "+data.toString());		
				JsonObject instanceGameData = new JsonObject(data.toString()).put("collection", "instanceGameData");
				
				vertx.eventBus().send("insert-instanceData",instanceGameData, res->{
					if(res.succeeded()){						
						context.response().end(RestResponse.createSucess("Se ha guardado el juego satisfactoriamente", data.toString()) );
					}else{
						context.response().end(RestResponse.createError(ErrorType.INSERT_DB_ERROR, "Ha ocurrido un inconveniente. . . "+res.cause()));
					}
				});				
			});
		});
		
		router.put("/arithmetic/v2/instanceGameData").handler(context->{
			context.request().bodyHandler(data ->{
				JsonObject instanceGameData = new JsonObject(data.toString()).put("collection", "instanceGameData");

				context.vertx().eventBus().send("save", instanceGameData.encode(), ar ->{
			    	 if (ar.succeeded()) {
			    		 context.response().end(RestResponse.createSucess("Se ha actualizado la instancia satisfactoriamente: ", ar.result().body().toString()));		    		    
			    	 }else{
			    		 context.response().end(RestResponse.createError(ErrorType.INSERT_DB_ERROR, "Ha ocurrido un inconveniente. . . "+ar.cause()));
			    	 }
			    });				
			});			
		});
		
		router.get("/arithmetic/v2/instanceGameData").handler(context ->{
			logger.debug("get /arithmetic/v2/instanceGameData");
			
			vertx.eventBus().send("find-all", "instanceGameData", res->{					
				logger.info("result find-all :"+res.result().body().toString());
				JsonArray instanceGameDatas = (JsonArray)res.result().body();
				context.response().end(instanceGameDatas.encode());
			});
			
		});
		
		router.delete("/arithmetic/v2/instanceGameData").handler(context ->{
			vertx.eventBus().send("remove", 
				new JsonObject()
					.put("collection", "instanceGameData")
					.put("query", new JsonObject()
							.put("_id", context.request().params().get("_id"))).encode(), 
				res->{				
					logger.info("result remove :"+res.result().body().toString());
					JsonObject instanceGameData = (JsonObject)res.result().body();
					context.response().end(instanceGameData.encode());
			});
		});
		
		router.delete("/arithmetic/v2/game").handler(context ->{
			String _id = context.request().params().get("_id");
			System.out.println(_id);
			vertx.eventBus().send("remove", 
				new JsonObject()
					.put("collection", "gameConfig")
					.put("query", new JsonObject()
							.put("_id", _id)).encode(), 
				res->{				
					logger.info("result remove :"+res.result().body().toString());
					JsonObject gameConfig = (JsonObject)res.result().body();
					context.response().end(gameConfig.encode());
			});
		});
		
		
		
		
		StaticHandlerImpl shi = new StaticHandlerImpl();
		shi.setCachingEnabled(false);
		shi.setMaxAgeSeconds(StaticHandlerImpl.DEFAULT_MAX_AGE_SECONDS);
		router.route("/*").handler(shi);


		HttpServer server = vertx.createHttpServer();
		server.requestHandler(router::accept).listen(PORT);
		logger.info("Server admin open in port " + PORT);
	}

	public static void main(String[] args) {
		AdminVerticle verticle = new AdminVerticle();
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(verticle);
	}
}
