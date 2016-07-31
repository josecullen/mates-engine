package server;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.impl.StaticHandlerImpl;
import io.vertx.ext.web.impl.RouterImpl;

public class ApplicationServer {
	private static Logger logger = LoggerFactory.getLogger(ApplicationServer.class);
	private static int PORT = 10002;
	
	public static void main(String[] args) {

		Vertx vertx = Vertx.factory.vertx();
		HttpServer server = vertx.createHttpServer();
		
	
		Router router = new RouterImpl(vertx);
		
		router.route("/").handler(handler -> {
			handler.response().sendFile("webroot/dist/index.html");
		});
				
		StaticHandlerImpl shi = new StaticHandlerImpl();
		shi.setCachingEnabled(false);
		shi.setMaxAgeSeconds(StaticHandlerImpl.DEFAULT_MAX_AGE_SECONDS);

		router.route("/*").handler(shi);

		
		
		server.requestHandler(router::accept).listen(PORT);

		logger.info("Server open in port " + PORT);
	}

}
