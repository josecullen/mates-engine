package com.torbitoinc.mates.engine.endpoint.server.handler;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.torbitoinc.mates.engine.endpoint.model.MatesBadRequest;
import com.torbitoinc.mates.engine.endpoint.model.Problem;
import com.torbitoinc.mates.engine.endpoint.model.SimpleModuleConfig;
import com.torbitoinc.mates.engine.endpoint.validation.RequestValidation;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import math.arithmetic.problem.SimpleModuleProblem;

public class SimpleModuleHandler implements Handler<RoutingContext>{
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void handle(RoutingContext context) {
		context.vertx().executeBlocking(future -> {

			BodyChecker bodyChecker;
			
			try {
				bodyChecker = new BodyChecker(context.getBodyAsJson());
				
				math.core.problem.Problem problemGen = new SimpleModuleProblem(
					bodyChecker.getSimpleModuleConfig().getA().createBuilder().build(),
					bodyChecker.getSimpleModuleConfig().getMod().createBuilder().build(),
					bodyChecker.getSimpleModuleConfig().getPow().createBuilder().build()
				);
				
				future.complete(Problem.create(problemGen));
				
			}
			catch (MatesBadRequest e){				
				future.fail(e);
			}
			catch (Exception e) {
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
		private SimpleModuleConfig simpleModuleConfig;
		
		public BodyChecker(JsonObject body) throws JsonParseException, JsonMappingException, IOException {
			simpleModuleConfig = mapper.readValue(body.toString(), SimpleModuleConfig.class);
			
			List<String> errors = RequestValidation.validate(simpleModuleConfig);
			
			if(!errors.isEmpty()){
				throw new MatesBadRequest(errors);
			}
		}

		public SimpleModuleConfig getSimpleModuleConfig(){
			return simpleModuleConfig;
		}

	}

	
	

}
