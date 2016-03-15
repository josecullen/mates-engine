package handlers;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.headius.invokebinder.transform.Catch;
import com.mchange.v2.sql.filter.SynchronizedFilterDataSource;

import builders.ArithmeticVariableBuilder;
import builders.EquationBuilder;
import builders.OperationBuilder;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import math.arithmetic.problem.EquationLevel;
import math.arithmetic.problem.SimpleProblem;
import math.db.model.game.config.GameConfig;
import math.db.model.game.instance.GameInstance;
import math.db.model.player.InstanceGameData;
import service.facade.ProblemFacade;

public class OneInstanceHandler {

	/**
	 * Crea una instancia de un juego
	 */
	public static Handler<RoutingContext> GET = handler -> {
		System.out.println("OneInstanceHandler GET");

		String instanceId = handler.request().params().get("instanceId");
		String gameId = handler.request().params().get("gameId");
		
		Morphia morphia = new Morphia();
		morphia.map(GameConfig.class);
		Datastore ds = morphia.createDatastore(new com.mongodb.MongoClient(), "mathGame");

		GameConfig gameConfig = ds.get(GameConfig.class, new ObjectId(gameId));

		GameInstance gameInstance = new GameInstance(instanceId, gameId, GameCreator.createLevels(gameConfig));

		try {
			handler.response().end(new ObjectMapper().writeValueAsString(gameInstance));
		} catch (IOException ex) {
			handler.response().end(ex.getMessage());
		}

	};

	public static Handler<RoutingContext> POST = handler -> {
		handler.request().bodyHandler(data -> {

			JsonObject requestJson = new JsonObject(data.toString());

			InstanceGameData instanceGameData = new InstanceGameData(
					requestJson.getString("instanceName"), 
					requestJson.getString("gameId"));
			
			Morphia morphia = new Morphia();
			morphia.map(InstanceGameData.class);
			Datastore ds = morphia.createDatastore(new com.mongodb.MongoClient(), "mathGame");

			ds.save(instanceGameData);				

		});
	};

	public static Handler<RoutingContext> PUT = handler -> {

		handler.request().bodyHandler(data -> {
			System.out.println("PUT oneInstanceHandler data" + data.toString());
			JsonObject instance = new JsonObject(data.toString()).put("collection", "instance");

			handler.vertx().eventBus().send("save", instance.encode(), ar -> {
				if (ar.succeeded()) {
					handler.response().end(ar.result().body().toString());
				} else {
					System.out.println("problem . . . " + ar.cause());
					handler.response().end(ar.result().body().toString());
				}
			});

		});

	};

	public static Handler<RoutingContext> DELETE = handler -> {
		String instanceId = handler.request().params().get("instanceId");

		JsonObject deleteRequest = new JsonObject().put("collection", "instance").put("query",
				new JsonObject().put("_id", instanceId));

		handler.vertx().eventBus().send("remove", deleteRequest.encode(), ar -> {
			if (ar.succeeded()) {
				handler.response().end(ar.result().body().toString());
			}
		});

	};
	
	
	private static final String SIMPLE = "SIMPLE", EQUATION_1 = "EQUATION_1", EQUATION_2 = "EQUATION_2",
			EQUATION_3 = "EQUATION_3";

	private static JsonArray createInstanceLevels(JsonArray gameLevels) {
		JsonArray instanceLevels = new JsonArray();

		gameLevels.forEach(value -> {

			JsonObject level = (JsonObject) value;

			switch (level.getString("type")) {
			case SIMPLE:
				instanceLevels.add(createSimpleLevelProblems(level));
				// instanceLevels.add(createSimpleLevelProblems(level.getJsonObject("problem")));

				break;
			case EQUATION_1:
				instanceLevels.add(createEquationLevelProblems(level, 1));
				break;
			case EQUATION_2:
				instanceLevels.add(createEquationLevelProblems(level, 2));
				break;
			case EQUATION_3:
				instanceLevels.add(createEquationLevelProblems(level, 3));
				break;
			default:
				// instanceLevels.add(createSimpleLevelProblems(level));
				instanceLevels.add(createSimpleLevelProblems(level.getJsonObject("problem")));

			}
		});

		return instanceLevels;
	}

	private static JsonArray createSimpleLevelProblems(JsonObject level) {
		JsonArray instanceProblems = new JsonArray();

		ArithmeticVariableBuilder rvi = new ArithmeticVariableBuilder()
				.divisionFactor(level.getInteger("divisionFactor")).max(level.getDouble("max"))
				.min(level.getDouble("min")).probablySign(level.getDouble("probablySign"));

		OperationBuilder rob = new OperationBuilder().buildWithProbablySign(level.getDouble("probablySign"))
				// .buildWithProbablySign(level.getDouble("sign"))
				.buildWithThisOperations(level.getString("operations"));

		SimpleProblem problem = new SimpleProblem(level.getString("form"), rvi, rob);

		for (int i = 0; i < level.getInteger("repetitions"); i++) {
			problem.renew();
			instanceProblems.add(ProblemFacade.parseProblemToJson(problem));
		}

		return instanceProblems;
	}

	private static JsonArray createEquationLevelProblems(JsonObject level, int equationGrade) {
		JsonArray instanceProblems = new JsonArray();

		JsonObject a = level.getJsonObject("a");
		JsonObject x1 = level.getJsonObject("x1");
		JsonObject x2 = level.getJsonObject("x2");
		JsonObject x3 = level.getJsonObject("x3");

		EquationLevel eqLevel = null;

		switch (equationGrade) {
		case 1:
			eqLevel = EquationLevel.LEVEL_1;
			break;
		case 2:
			eqLevel = EquationLevel.LEVEL_2;
			break;
		case 3:
			eqLevel = EquationLevel.LEVEL_3;
			break;
		}

		EquationBuilder equationBuilder = new EquationBuilder().buildWithLevel(eqLevel)
				.buildWithA(parseToArithmeticVariableBuilder(a)).buildWithX1(parseToArithmeticVariableBuilder(x1))
				.buildWithX2(parseToArithmeticVariableBuilder(x2)).buildWithX3(parseToArithmeticVariableBuilder(x3));

		for (int i = 0; i < level.getInteger("repetitions"); i++) {
			instanceProblems.add(ProblemFacade.parseProblemToJson(equationBuilder.build()));
		}

		return instanceProblems;
	}

	private static ArithmeticVariableBuilder parseToArithmeticVariableBuilder(JsonObject conf) {
		ArithmeticVariableBuilder builder = new ArithmeticVariableBuilder().min(conf.getDouble("min"))
				.max(conf.getDouble("max")).probablySign(conf.getDouble("sign")).divisionFactor(conf.getInteger("div"));
		return builder;
	}

	
}
