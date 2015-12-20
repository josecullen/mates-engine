package server;

import handlers.ProblemHandler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class Application {
	private static String PATH = "/math/test/v1/";
	private static String PATH_PROBLEM = PATH+"problem";
	
	static int PORT = 8082;
	
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		HttpServer server = vertx.createHttpServer();		
	
		Router router = Router.router(vertx);

		router.route("/").handler(handler ->{
			handler.response().sendFile("webroot/index.html");
		});
		
		router.get(PATH_PROBLEM).handler(ProblemHandler.GET);

		
		
		
		router.route("/*").handler(StaticHandler.create());
		
		server.requestHandler(router::accept).listen(PORT);
		System.out.println("Server open in port "+PORT);
	}

}
