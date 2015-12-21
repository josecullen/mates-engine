package handlers;

import problem.ProblemFacade;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ProblemHandler {
	public static Handler<RoutingContext> GET = routeContext ->{
		MultiMap params = routeContext.request().params();
		
		JsonObject problem = ProblemFacade.getProblem(params);
		
		routeContext.response().end(problem.encode());	
	};

}
