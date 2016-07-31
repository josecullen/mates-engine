package handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class AllGamesHandler {

	public static Handler<RoutingContext> GET = handler ->{
		handler.vertx().eventBus().send("find-all", "game", ar ->{
			if(ar.succeeded()){
				handler.response().end(ar.result().body().toString());
			}else{
				handler.response().end("Ocurrió un problema. . .");
			}
		});
	};

	public static Handler<RoutingContext> DELETE = handler ->{
		handler.response().end("Elimina la lista de Juegos");
	};
}
