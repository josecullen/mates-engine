package server;

import game.ArithmeticGameMulti;
import game.ArithmeticGameMulti.Player;
import game.ArithmeticGameMulti.State;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

import java.util.ArrayList;
import java.util.List;

import math.arithmetic.problem.SimpleProblem;
import service.facade.ExerciseFacade;
import service.facade.OperationFacade;
import service.facade.ProblemFacade;
import builders.ArithmeticVariableBuilder;
import builders.OperationBuilder;

public class Application {
	static int PORT = 8080;
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		HttpServer server = vertx.createHttpServer();
		
		final List<ArithmeticGameMulti> multiGames = new ArrayList<>(); 
		
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
			int divisionFactor = Integer.parseInt(handler.request().getParam("divisionFactor"));
			
			ArithmeticVariableBuilder randomVariableInstancer = new ArithmeticVariableBuilder().max(max).min(min).probablySign(probablySign).divisionFactor(divisionFactor);
			OperationBuilder randomOperationBuilder = new OperationBuilder().buildWithProbablySign(probablySign).buildWithThisOperations(operations);
			SimpleProblem problem = new SimpleProblem(form, randomVariableInstancer, randomOperationBuilder);
			problem.renew();
			
			
//			System.out.println(ProblemFacade.parseProblemToJson(problem).encode());
			handler.response().end(ProblemFacade.parseProblemToJson(problem).encode());
		});
		
		router.post("/arithmetic/v1/game/multi").handler(handler ->{

			handler.request().bodyHandler(data ->{
//				System.out.println(data);
				JsonObject jsonGame = new JsonObject(data.toString());
				ArithmeticGameMulti gameMulti = new ArithmeticGameMulti(jsonGame);
				multiGames.add(gameMulti);
//				for(Problem problem : gameMulti.problems){
//					System.out.println(problem);
//				}
				handler.response().end(data);
			});
			
		});
		
		router.get("/arithmetic/v1/game/multi/join").handler(handler ->{			
			
			String gameName = handler.request().getParam("gameName");
			String playerName = handler.request().getParam("playerName");
			
			multiGames
				.stream()
				.filter(game -> game.getName().equals(gameName))
				.findFirst()
				.ifPresent(game -> {
					game.addPlayer(playerName);
//					game.players.forEach(player -> System.out.println(player.name));
				});
			
			handler.response().end("ok");

		});
		
		router.put("/arithmetic/v1/game/multi/join").handler(handler ->{			
			
			String gameName = handler.request().getParam("gameName");
			String playerName = handler.request().getParam("playerName");
			
			ArithmeticGameMulti theGame = null;
			
			multiGames
				.stream()
				.filter(game -> game.getName().equals(gameName))
				.findFirst()
				.ifPresent(game -> {
					game.players
						.stream()
						.filter(player -> player.name.equals(playerName))
						.findFirst()
						.ifPresent(player -> player.setReady(true));
					
					if(game.players.stream().allMatch(player -> player.getReady())){
						game.setStatus("STARTED");
					}
//					game.players.forEach(player -> System.out.println(player.name));
				});
			
			handler.response().end("ok");

		});
		
		router.get("/arithmetic/v1/game/multi/start").handler(handler->{
			String gameName = handler.request().getParam("gameName");
			String playerName = handler.request().getParam("playerName");
			multiGames
				.stream()
				.filter(game -> game.getName().equals(gameName))
				.findFirst()
				.ifPresent(game -> {	
					game.getPlayerByName(playerName).setReady(true);
				
					if(game.players.stream().allMatch(player -> player.getReady())){
						game.setStatus("STARTED");
					}
					
					while(!game.getStatus().equals(State.STARTED)){
						try {
							Thread.sleep(500);
						} catch (Exception e) {
							e.printStackTrace();
						}
					};	
					
				});		
			
			JsonArray games = new JsonArray();
			
			multiGames.forEach(game ->{
				games.add(game.toJson());
			});
			
			handler.response().end(games.encode());
		});
		
		
		router.get("/arithmetic/v1/game/multi/sendResult").handler(handler->{
			String gameName = handler.request().getParam("gameName");
			String playerName = handler.request().getParam("playerName");
			boolean isCorrect = handler.request().getParam("isCorrect") == "true";
			int points = Integer.parseInt(handler.request().getParam("points"));
			
			multiGames
				.stream()
				.filter(game -> game.getName().equals(gameName))
				.findFirst()
				.ifPresent(game -> {											
					Player player = game.getPlayerByName(playerName);
					player.updateScoring(isCorrect, points);
					handler.response().end(ProblemFacade.parseProblemToJson(game.problems.get(player.getProblemCount())).encode());
					player.setProblemCount(player.getProblemCount()+1);
				});			
		});
		
		
		

		
		router.get("/arithmetic/v1/game/multi/game").handler(handler ->{
			JsonArray players = new JsonArray();
			List<ArithmeticGameMulti> games = multiGames;
			
			games.forEach(game ->{
				game.players.forEach(player ->{
					players.add(player.name);
				});
			});
			
			handler.response().end(players.encode());
		});
		
		router.put("/arithmetic/v1/game/multi/game").handler(handler ->{
			List<ArithmeticGameMulti> games = multiGames;
			
			games.forEach(game ->{
				game.setStatus(handler.request().getParam("status"));
			});
			JsonArray jsonGames = new JsonArray();

			multiGames.forEach(game ->{
				jsonGames.add(game.toJson());
			});
			
			handler.response().end(jsonGames.encode());
		});
		
		router.get("/arithmetic/v1/game/multi").handler(handler ->{
			JsonArray games = new JsonArray();
			
			multiGames.forEach(game ->{
				games.add(game.toJson());
			});
			
			handler.response().end(games.encode());

		});

		
		
		
		router.route("/*").handler(StaticHandler.create());
		
		server.requestHandler(router::accept).listen(PORT);
		System.out.println("Server open in port "+PORT);
	}

}
