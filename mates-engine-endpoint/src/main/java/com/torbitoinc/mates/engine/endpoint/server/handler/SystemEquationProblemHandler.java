package com.torbitoinc.mates.engine.endpoint.server.handler;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.torbitoinc.mates.engine.endpoint.model.MatesBadRequest;
import com.torbitoinc.mates.engine.endpoint.model.Problem;
import com.torbitoinc.mates.engine.endpoint.model.SystemEquationConfig;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import math.arithmetic.problem.SystemEquationProblem;

public class SystemEquationProblemHandler implements Handler<RoutingContext>{
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void handle(RoutingContext context) {
		context.vertx().executeBlocking(future -> {

			BodyChecker bodyChecker;
			
			try {
				bodyChecker = new BodyChecker(context.getBodyAsJson());
				
				math.core.problem.Problem problemGen = new SystemEquationProblem(
					bodyChecker.getSimpleModuleConfig().getEquationLevel(),
					bodyChecker.getSimpleModuleConfig().getConstant().createBuilder(),
					bodyChecker.getSimpleModuleConfig().getX().createBuilder(),
					bodyChecker.getSimpleModuleConfig().getOperationConfig().createBuilder()
				);
				
				future.complete(Problem.create(problemGen));
				
			} catch (Exception e) {
				
				future.fail(new MatesBadRequest(
					context.getBodyAsJson(), 
					context.request().path(),
					context.request().method().name(),
					"")
				);
			}

		}, false, res -> {
			if(res.failed()){
				MatesBadRequest ex = (MatesBadRequest) res.cause();
				context
					.response()
					.putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(ex.getErrorResponse()));
				
			}else{
				context
					.response()
					.putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(res.result()));
			}
			
		});

		
	}
	
	
	
	class BodyChecker {
		private SystemEquationConfig simpleEquationConfig;
		
		public BodyChecker(JsonObject body) throws JsonParseException, JsonMappingException, IOException {
			simpleEquationConfig = mapper.readValue(body.toString(), SystemEquationConfig.class);
		}

		public SystemEquationConfig getSimpleModuleConfig(){
			return simpleEquationConfig;
		}

	}

	
	

}
