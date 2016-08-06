package com.torbitoinc.mates.engine.server;

import com.torbitoinc.mates.engine.endpoint.server.handler.EquationProblemHandler;
import com.torbitoinc.mates.engine.endpoint.server.handler.LogicProblemHandler;
import com.torbitoinc.mates.engine.endpoint.server.handler.SimpleModuleHandler;
import com.torbitoinc.mates.engine.endpoint.server.handler.SimpleProblemHandler;
import com.torbitoinc.mates.engine.endpoint.server.handler.SystemEquationProblemHandler;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class Server {
	private static final int port = 9090;
	private static final SimpleProblemHandler SIMPLE_PROBLEM_HANDLER = new SimpleProblemHandler();
	private static final EquationProblemHandler EQUATION_PROBLEM_HANDLER = new EquationProblemHandler();
	private static final SimpleModuleHandler SIMPLE_MODULE_HANDLER = new SimpleModuleHandler();
	private static final SystemEquationProblemHandler SYSTEM_EQUATION_PROBLEM_HANDLER = new SystemEquationProblemHandler();
	private static final LogicProblemHandler LOGIC_PROBLEM_HANDLER = new LogicProblemHandler();
	
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();

		HttpServerOptions options = new HttpServerOptions().setLogActivity(true);
		HttpServer server = vertx.createHttpServer(options);
		
		Router router = Router.router(vertx);
		
		router.post("/v1/aritmetic/*").handler(BodyHandler.create());
		
		router.post("/v1/aritmetic/simple-problem").handler(SIMPLE_PROBLEM_HANDLER);
		router.post("/v1/aritmetic/equation-problem").handler(EQUATION_PROBLEM_HANDLER);
		router.post("/v1/aritmetic/simple-module-problem").handler(SIMPLE_MODULE_HANDLER);
		router.post("/v1/aritmetic/system-equation-problem").handler(SYSTEM_EQUATION_PROBLEM_HANDLER);
		router.post("/v1/logic/logic-problem").handler(LOGIC_PROBLEM_HANDLER);
		
		server.requestHandler(router::accept).listen(port, handler ->{
			if(handler.succeeded())
				System.out.println("Listening on port "+port);
		});
	}

}
