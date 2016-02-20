package handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class AllPlayersHandler {
	public static Handler<RoutingContext> GET = handler ->{
		handler.response().end("Devuelve la lista de Juegos");
		
	};
	
	public static Handler<RoutingContext> DELETE = handler ->{
		handler.response().end("Elimina la lista de Juegos");
	};
}
