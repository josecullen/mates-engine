package com.torbitoinc.mates.engine.endpoint.server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.torbitoinc.mates.engine.endpoint.exception.MatesBadRequest;
import com.torbitoinc.mates.engine.endpoint.model.Problem;
import com.torbitoinc.mates.engine.endpoint.model.SimpleProblemConfig;
import com.torbitoinc.mates.engine.endpoint.validation.RequestValidation;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import math.arithmetic.problem.SimpleProblem;

public class SimpleProblemHandler implements Handler<RoutingContext> {

	private static final ObjectMapper mapper = new ObjectMapper();


	@Override
	public void handle(RoutingContext context) {
		context.vertx().executeBlocking(future -> {

			BodyChecker bodyChecker;
			
			try {
				bodyChecker = new BodyChecker(context.getBodyAsJson());
				
				List<Problem> problems = new ArrayList<>();
				for(int i = 0; i < bodyChecker.getSimpleProblemConfig().getRepetitions(); i++){
				
					math.core.problem.Problem problemGen = new SimpleProblem(
						bodyChecker.getSimpleProblemConfig().getExpression(),
						bodyChecker.getSimpleProblemConfig().getAritmeticVariableConfig().createBuilder(),
						bodyChecker.getSimpleProblemConfig().getOperationConfig().createBuilder()
					);
					
					problems.add(Problem.create(problemGen));
					
				}
				
				future.complete(problems);
				
			}
			catch (MatesBadRequest e){				
				future.fail(e);
			}
			catch (Exception e) {				
				future.fail(new MatesBadRequest(Arrays.asList("Bad request")));				
			}

		}, false, res -> {
			if(res.failed()){
				MatesBadRequest ex = (MatesBadRequest) res.cause();
				context
					.response()
					.setStatusCode(400)
					.putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(ex.getErrors()));
				
			}else{
				context
					.response()					
					.putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(res.result()));
			}
			
		});

	}
	

	class BodyChecker {
		private SimpleProblemConfig simpleProblemConfig;
		
		public BodyChecker(JsonObject body) throws JsonParseException, JsonMappingException, IOException {
			simpleProblemConfig = mapper.readValue(body.toString(), SimpleProblemConfig.class);
			
			List<String> errors = RequestValidation.validate(simpleProblemConfig);
			System.out.println("repetitions "+simpleProblemConfig.getRepetitions());
			if(!errors.isEmpty()){
				throw new MatesBadRequest(errors);
			}
		}

		public SimpleProblemConfig getSimpleProblemConfig(){
			return simpleProblemConfig;
		}
		
		
	}
	
	
	

}
