package math.game.admin;

import com.fasterxml.jackson.databind.ObjectMapper;

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
import math.core.problem.Problem;
import math.db.model.game.config.LevelConfig;
import math.db.model.game.config.ProblemType;
import math.game.facade.problem.ProblemCreator;
import math.mongo.DeleteVerticle;
import math.mongo.FindVerticle;
import math.mongo.InsertVerticle;
import math.mongo.UpdateVerticle;

public class AdminVerticle extends AbstractVerticle {
	private static final String ARITHMETIC_V2_PROBLEM = "/arithmetic/v2/problem";
	private static final String ARITHMETIC_V2_INSTANCE_GAME_DATA = "/arithmetic/v2/instanceGameData";
	private static final String ARITHMETIC_V2_GAME = "/arithmetic/v2/game";
	Logger logger = LoggerFactory.getLogger(AdminVerticle.class);
	private static final int PORT = 10001;

	public static void main(String[] args) {
		AdminVerticle verticle = new AdminVerticle();
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(verticle);
	}
	


	
	@Override
	public void start() throws Exception {
		super.start();
		System.out.println("Iniciando...");
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

		router.post(ARITHMETIC_V2_GAME).handler(context ->{
			logger.info("post /arithmetic/v2/game");

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

		router.put(ARITHMETIC_V2_GAME).handler(context->{
			logger.info("put "+ ARITHMETIC_V2_GAME);

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
			logger.info("get /arithmetic/v2/games");

			vertx.eventBus().send("find-all", "gameConfig", res->{
				logger.info("result find-all :"+res.result().body().toString());
				JsonArray gameConfigs = (JsonArray)res.result().body();
				context.response().end(gameConfigs.encode());
			});

		});

		router.post(ARITHMETIC_V2_INSTANCE_GAME_DATA).handler(context ->{
			logger.info("/arithmetic/v2/instanceData");
			System.out.println(ARITHMETIC_V2_INSTANCE_GAME_DATA);
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

		router.put(ARITHMETIC_V2_INSTANCE_GAME_DATA).handler(context->{
			logger.info(ARITHMETIC_V2_INSTANCE_GAME_DATA);
			System.out.println(ARITHMETIC_V2_INSTANCE_GAME_DATA);
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

		router.get(ARITHMETIC_V2_INSTANCE_GAME_DATA).handler(context ->{
			logger.info("get /arithmetic/v2/instanceGameData");

			vertx.eventBus().send("find-all", "instanceGameData", res->{
				logger.info("result find-all :"+res.result().body().toString());
				JsonArray instanceGameDatas = (JsonArray)res.result().body();
				context.response().end(instanceGameDatas.encode());
			});

		});

		router.delete(ARITHMETIC_V2_INSTANCE_GAME_DATA).handler(context ->{
			logger.info(ARITHMETIC_V2_INSTANCE_GAME_DATA);

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

		router.delete(ARITHMETIC_V2_GAME).handler(context ->{
			logger.info(ARITHMETIC_V2_GAME);

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

		router.post(ARITHMETIC_V2_PROBLEM).handler(context ->{
			logger.debug(ARITHMETIC_V2_PROBLEM);

			context.request().bodyHandler(data ->{
				logger.info("data : "+data.toString());
				JsonObject requestData = new JsonObject(data.toString());
				requestData.getJsonObject("levelConfig").encode();
				try {
					LevelConfig levelConfig = new ObjectMapper().readValue(requestData.getJsonObject("levelConfig").encode(), LevelConfig.class);
					ProblemType problemType = ProblemType.valueOf(requestData.getString("problemType"));
					
					Problem problem = ProblemCreator.createProblem(levelConfig.getProblemConfig(), problemType);
					context.response().end(new ObjectMapper().writeValueAsString(problem));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
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

}
