package server;


import handlers.AllGamesHandler;
import handlers.AllInstanceHandler;
import handlers.AllPlayersHandler;
import handlers.OneGameHandler;
import handlers.OneInstanceHandler;
import handlers.OnePlayerHandler;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.impl.StaticHandlerImpl;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;
import mongo.DeleteVerticle;
import mongo.FindVerticle;
import mongo.InsertVerticle;
import mongo.UpdateVerticle;

public class ApplicationServer {
	static int PORT = 8081;
	
	private static String PATH = "/arithmetic/v1/";
	private static String PATH_ALL = PATH+"all";
	private static String PATH_ONE = PATH+"one";
	private static String PATH_PLAYER_ALL = PATH+"player/all";
	private static String PATH_PLAYER_ONE = PATH+"player/one";
	private static String PATH_INSTANCE_ALL = PATH+"instance/all";
	private static String PATH_INSTANCE_ONE = PATH+"instance/one";

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		HttpServer server = vertx.createHttpServer();
		JsonObject mongoConfig = new JsonObject().put("db_name", "mathGame").put("host", "localhost").put("port", 27017);
		MongoClient client = MongoClient.createShared(vertx, mongoConfig);
		EventBus eb = vertx.eventBus();

		vertx.deployVerticle(new InsertVerticle(), res ->{
			if (res.succeeded()) {
			    System.out.println("Deployment id is: " + res.result());
			} else {
			    System.out.println("Deployment failed!");
			}
		});
		
		vertx.deployVerticle(new FindVerticle(), res ->{
			if (res.succeeded()) {
			    System.out.println("Deployment id is: " + res.result());
			} else {
			    System.out.println("Deployment failed!");
			}
		});
		
		vertx.deployVerticle(new DeleteVerticle(), res ->{
			if (res.succeeded()) {
			    System.out.println("Deployment id is: " + res.result());
			} else {
			    System.out.println("Deployment failed!");
			}
		});
		
		vertx.deployVerticle(new UpdateVerticle(), res ->{
			if (res.succeeded()) {
			    System.out.println("Deployment id is: " + res.result());
			} else {
			    System.out.println("Deployment failed!");
			}
		});
				
				
		
		
		Router router = Router.router(vertx);
		
//		router.route().handler(io.vertx.ext.web.handler.CorsHandler.create("/").allowedMethod(HttpMethod.GET));
		

		router.route("/").handler(handler ->{
			handler.response().sendFile("webroot/index.html");
		});
		
		router.get(PATH_ALL).handler(AllGamesHandler.GET);
		router.delete(PATH_ALL).handler(AllGamesHandler.DELETE);
		
		router.get(PATH_ONE).handler(OneGameHandler.GET);
		router.post(PATH_ONE).handler(OneGameHandler.POST);
		router.put(PATH_ONE).handler(OneGameHandler.PUT);
		router.delete(PATH_ONE).handler(OneGameHandler.DELETE);
		
		router.get(PATH_PLAYER_ALL).handler(AllPlayersHandler.GET);
		router.delete(PATH_PLAYER_ALL).handler(AllPlayersHandler.DELETE);
		
		router.get(PATH_PLAYER_ONE).handler(OnePlayerHandler.GET);
		router.post(PATH_PLAYER_ONE).handler(OnePlayerHandler.POST);
		router.put(PATH_PLAYER_ONE).handler(OnePlayerHandler.PUT);
		router.delete(PATH_PLAYER_ONE).handler(OnePlayerHandler.DELETE);

		router.get(PATH_INSTANCE_ALL).handler(AllInstanceHandler.GET);
		router.delete(PATH_INSTANCE_ALL).handler(AllInstanceHandler.DELETE);
		
		router.get(PATH_INSTANCE_ONE).handler(OneInstanceHandler.GET);
		router.post(PATH_INSTANCE_ONE).handler(OneInstanceHandler.POST);
		router.put(PATH_INSTANCE_ONE).handler(OneInstanceHandler.PUT);
		router.delete(PATH_INSTANCE_ONE).handler(OneInstanceHandler.DELETE);
		
		
		
		
		router.route("/arithmetic/*").handler(handler ->{			
			handler.response().end("path: "+handler.request().absoluteURI()+ "\t method : "+handler.request().method());
		});
		


		
		SockJSHandlerOptions options = new SockJSHandlerOptions().setHeartbeatInterval(2000);
		SockJSHandler sockJSHandler = SockJSHandler.create(vertx, options); 
		
		sockJSHandler.socketHandler(sockJSSocket ->{

			
			Handler<Buffer> handler = buffer->{			
				String instanceId = buffer.toString();
			
				JsonObject query = new JsonObject()
					.put("collection", "instance")
					.put("query", new JsonObject().put("_id", instanceId));
				
				System.out.println("Creando eventBus consumer "+instanceId);
				
				MessageConsumer<String> consumer = eb.consumer(instanceId);
				
				consumer.handler(message -> {
				  System.out.println("I have received a message: " + message.body());
				  
					vertx.eventBus().send("find-one", query.encode(), ar ->{
						if(ar.succeeded()){
							sockJSSocket.write(Buffer.buffer(ar.result().body().toString()));
						}else{
							sockJSSocket.write(Buffer.buffer("query error"));
						}
					});
				  
				});
				
				
				vertx.eventBus().publish(instanceId, "update");

				
			};

			sockJSSocket.handler(handler);

		});
		
		router.route("/socketjs/*").handler(sockJSHandler);
		
		
		StaticHandlerImpl shi = new StaticHandlerImpl();
		shi.setAllowRootFileSystemAccess(true);
		shi.setCachingEnabled(false);
		shi.setMaxAgeSeconds(StaticHandlerImpl.DEFAULT_MAX_AGE_SECONDS);
		
		
		router.route("/*").handler(shi);
		
		server.requestHandler(router::accept).listen(PORT);
		System.out.println("Server open in port "+PORT);
	}

}
