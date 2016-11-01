package com.torbitoinc.mates.engine.endpoint.server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.torbitoinc.mates.engine.endpoint.exception.MatesBadRequest;
import com.torbitoinc.mates.engine.endpoint.model.LogicProblemConfig;
import com.torbitoinc.mates.engine.endpoint.model.Problem;
import com.torbitoinc.mates.engine.endpoint.validation.RequestValidation;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import math.logic.problem.LogicProblem;

public class LogicProblemHandler implements Handler<RoutingContext>{
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void handle(RoutingContext context) {
		context.vertx().executeBlocking(future -> {

			BodyChecker bodyChecker;
			
			try {
				bodyChecker = new BodyChecker(context.getBodyAsJson());
				
				List<Problem> problems = new ArrayList<>();
				for(int i = 0; i < bodyChecker.getLogicProblemConfig().getRepetitions(); i++){
					
					math.core.problem.Problem problemGen = new LogicProblem(
						bodyChecker.getLogicProblemConfig().getExpression(),
						bodyChecker.getLogicProblemConfig().getProbablySign(),
						bodyChecker.getLogicProblemConfig().getOperations()
					);
					
					problems.add(Problem.create(problemGen));
						
				}
				future.complete(problems);
				
			} 
			catch (MatesBadRequest e){				
				future.fail(e);
			}
			catch (Exception e) {
				e.printStackTrace();
				future.fail(MatesBadRequest.DEFAULT);
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
		private LogicProblemConfig logicProblemConfig;
		
		public BodyChecker(JsonObject body) throws JsonParseException, JsonMappingException, IOException {
			logicProblemConfig = mapper.readValue(body.toString(), LogicProblemConfig.class);
			
			List<String> errors = RequestValidation.validate(logicProblemConfig);
			
			if(!errors.isEmpty()){
				throw new MatesBadRequest(errors);
			}
		}

		public LogicProblemConfig getLogicProblemConfig(){
			return logicProblemConfig;
		}

	}

	
	

}
