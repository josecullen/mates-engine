package handlers;

import problem.ProblemFacade;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.RoutingContext;

public class ProblemHandler {
	public static Handler<RoutingContext> GET = routeContext ->{
		MultiMap params = routeContext.request().params();
		
		routeContext.response().end(ProblemFacade.getProblem(params).encode());	
	};

}
