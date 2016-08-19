package com.torbitoinc.mates.engine.endpoint.validation;

import java.util.ArrayList;
import java.util.List;

import com.torbitoinc.mates.engine.endpoint.model.AritmeticVariableConfig;
import com.torbitoinc.mates.engine.endpoint.model.EquationProblemConfig;
import com.torbitoinc.mates.engine.endpoint.model.LogicProblemConfig;
import com.torbitoinc.mates.engine.endpoint.model.OperationConfig;
import com.torbitoinc.mates.engine.endpoint.model.SimpleModuleConfig;
import com.torbitoinc.mates.engine.endpoint.model.SimpleProblemConfig;
import com.torbitoinc.mates.engine.endpoint.model.SystemEquationConfig;

public class RequestValidation {
	private static final String MIN_GREATER_THAN_MAX = "min must be less than max."; 
	private static final String MIN_LESS_THAN_ZERO = "min must be greater than 0.";
	private static final String SIGN_OUTSIDE_BOUNDS = "probablySign must contains values in [0 - 1]";
	
	
	public static List<String> validate(SimpleProblemConfig simpleProblem){
		List<String> errors = new ArrayList<String>();
		
		errors.addAll(validate(simpleProblem.getAritmeticVariableConfig()));
		errors.addAll(validate(simpleProblem.getOperationConfig()));
		
		return errors;
	}
	
	public static List<String> validate(EquationProblemConfig equationProblem){
		List<String> errors = new ArrayList<String>();
		
		errors.addAll(validate(equationProblem.getA()));
		errors.addAll(validate(equationProblem.getX1()));
		errors.addAll(validate(equationProblem.getX2()));
		errors.addAll(validate(equationProblem.getX3()));
	
		return errors;
	}
	
	public static List<String> validate(LogicProblemConfig logicProblem){
		List<String> errors = new ArrayList<String>();
		
		errors.addAll(validateProbablySign(logicProblem.getProbablySign()));
	
		return errors;
	}
	
	public static List<String> validate(SimpleModuleConfig simpleModuleConfig) {
		List<String> errors = new ArrayList<String>();
		
		errors.addAll(validate(simpleModuleConfig.getA()));
		errors.addAll(validate(simpleModuleConfig.getMod()));
		errors.addAll(validate(simpleModuleConfig.getPow()));
		
		return errors;
	}
	
	public static List<String> validate(SystemEquationConfig simpleEquationConfig) {
		List<String> errors = new ArrayList<String>();
		
		errors.addAll(validate(simpleEquationConfig.getOperationConfig()));
		errors.addAll(validate(simpleEquationConfig.getX()));
		
		return errors;
	}


	public static List<String> validate(AritmeticVariableConfig aritmeticVariable){
		List<String> errors = new ArrayList<>();
		
		double min = aritmeticVariable.getMin();
		double max = aritmeticVariable.getMax();
		
		
		if(min > max){
			errors.add(MIN_GREATER_THAN_MAX);
		}
		
		if(min < 0){
			errors.add(MIN_LESS_THAN_ZERO);
		}
		
		errors.addAll(validateProbablySign(aritmeticVariable.getProbablySign()));		
		
		return errors;
	}
	
	private static List<String> validate(OperationConfig operationConfig){
		List<String> errors = new ArrayList<>();
		
		errors.addAll(validateProbablySign(operationConfig.getProbablySign()));
				
		return errors;		
	}
	
	
	private static List<String> validateProbablySign(double probablySign){
		List<String> errors = new ArrayList<>();
		
		if(probablySign < 0 || probablySign > 1){
			errors.add(SIGN_OUTSIDE_BOUNDS);
		}		
				
		return errors;
	}

	
	
	
	
	
}
