package server;

import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import math.arithmetic.operand.ArithmeticVariable.RandomVariableInstancer;
import math.arithmetic.operation.OperationUtil.RandomOperationBuilder;
import math.arithmetic.problem.Problem;
import service.facade.ExerciseFacade;
import service.facade.OperationFacade;
import service.facade.ProblemFacade;
import factory.OperationFactory;
import io.vertx.core.Vertx;

public class Application {
	static int PORT = 8080;
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		HttpServer server = vertx.createHttpServer();
		
		Router router = Router.router(vertx);

		router.route("/").handler(handler ->{
			handler.response().sendFile("webroot/index.html");
		});
		
		router.route("/exercise").handler(handler ->{
			String expression = handler.request().getParam("expression");
			handler.response().end(ExerciseFacade.getExercise(expression).encode());
		});
		
		router.route("/expression").handler(handler ->{
			String expression = handler.request().getParam("expression");
			handler.response().end(OperationFacade.createJsonFromExpression(expression).encode());
		});
		
		router.route("/newExpression").handler(handler ->{
			String expression = handler.request().getParam("expression");
			int signProbability = Integer.parseInt(handler.request().getParam("signProbability"));
			handler.response().end(OperationFacade.getJsonOperation(expression, signProbability).encode());
		});
		
		router.route("/exercise").handler(handler ->{
			String expression = handler.request().getParam("expression");
			handler.response().end(ExerciseFacade.getExercise(expression).encode());
		});
		
		router.route("/arithmetic/v1/problem").handler(handler ->{
			String form = handler.request().getParam("form");
			String operations = handler.request().getParam("operations");
			double max = Double.parseDouble(handler.request().getParam("max"));
			double min = Double.parseDouble(handler.request().getParam("min"));
			double probablySign = Double.parseDouble(handler.request().getParam("probablySign"));			
			RandomVariableInstancer randomVariableInstancer = new RandomVariableInstancer().max(max).min(min).probablySign(probablySign);
			RandomOperationBuilder randomOperationBuilder = new RandomOperationBuilder().buildWithProbablySign(probablySign).buildWithThisOperations(operations);
			Problem problem = new Problem(form, randomVariableInstancer, randomOperationBuilder);
			problem.renew();
			
			handler.response().end(ProblemFacade.parseProblemToJson(problem).encode());
		});
		
		router.route("/*").handler(StaticHandler.create());
		
		server.requestHandler(router::accept).listen(PORT);
		System.out.println("Server open in port "+PORT);
	}

}
