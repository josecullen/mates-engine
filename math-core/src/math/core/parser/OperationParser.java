package math.core.parser;

import java.util.function.Function;

import math.core.tree.MathNode;
import math.core.tree.OperandNode;
import math.core.tree.OperationNode;

public class OperationParser {
	
	
	public OperationParser(Function<String, OperandNode> operandInstancer, Function<String, OperationNode> operationInstancer) {
		newOperandInstance = operandInstancer;
		newOperationInstance = operationInstancer;
	}
	
	
	private Function<String, OperandNode> newOperandInstance;
	private Function<String, OperationNode> newOperationInstance;

	
	public MathNode parse(String expression){
		
		boolean hasSign = hasSign(expression);
		if(isOperand(expression)){
			return newOperandInstance.apply(expression.trim());
		}else{
			OperationNode operation;
			expression = stripOuterBraces(expression);
			String values[] = analyzeMorf(expression);
			
			operation = newOperationInstance.apply(values[1]);
			operation.getOperation().setSign(hasSign);
			operation.setOperands(getLogicFromString(values[0]), getLogicFromString(values[2]));
			return operation;
		}
		
	}
	
	private <T> MathNode<T> getLogicFromString(String value){
		value = value.trim();
		if(value.length() > 2){
			return parse(value);
		}else{
			return newOperandInstance.apply(value);
		}
	}
	
	private boolean isOperand(String expression){
		expression = removeSign(expression);			
		return expression.trim().length() == 1;
	}
	
	private String removeSign(String expression){
		expression = expression.trim();
		if(expression.startsWith("-")){
			expression = expression.substring(1);
			return expression;
		}
		return expression;
	}
	
	/**
	 * Detecta si la opación padre de la expresión tiene signo.
	 * Ejemplo
	 * <ul>
	 * 	<li><p>-(p or q) or p </p><p>No tiene signo, ya que la operación padre es el último 'or'</p></li>
	 * <li><p>-((p or q) or p) </p><p>Si tiene signo</p></li>
	 * </ul>
	 * @param expression
	 * @return
	 */
	private boolean hasSign(String expression){

		expression = expression.trim();
		if(expression.startsWith("-")){
			expression = expression.substring(1);
			
			/*
			 * Luego de quitar el '-' Quedaría
			 * ( ... ) or (...) si el '-' no corresponde a la operación padre y
			 * ((...) or ( ...)) si el '-' corresponde a la operación padre.
			 * 
			 *  Si llamo a stripOuterBraces y me quita los paréntesis, quiere decir que el '-' que quité era de la operación padre.
			 */
			return expression.equals(stripOuterBraces(expression)) ? false : true;
		}
		return false;
	}
	
	
	/**
	 * Quita los paréntesis que estén de más.
	 * Si tiene signo la operación, también quita los paréntesis y el signo. 
	 * @param expression
	 * @return
	 */
	private String stripOuterBraces(String expression){
		expression = removeSign(expression);
		
		if(expression.startsWith("(") && expression.endsWith(")")){
			int lBraces = 1;
			for(int i = 1; i < expression.length(); i++){
				if(lBraces == 0 && i != expression.length()-1){
					return expression;
				}
				if(expression.charAt(i) == '('){
					lBraces++;
				}else if(expression.charAt(i) == ')'){
					lBraces--;
				}
			}
			return expression.substring(1, expression.length()-1).trim();			
		}		
		return expression;
	}
	
	private String getNextLogicFromExpression(String expression){
		String logic = ""; 
		
		if(expression.charAt(0) == '('){
			logic = expression.substring(0, findRBrace(expression, 0));
		}else if(expression.charAt(0) == '-' && expression.charAt(1) == '('){
			logic = expression.substring(0,findRBrace(expression, 1)+1);
		}else if(expression.charAt(0) == '-'){
			logic = expression.substring(0,2);
		}else{
			logic = expression.substring(0,1);
		}
		
		return logic;
	}
	
	private String[] analyzeMorf(String expression){
		String values[] = new String[3];
		expression = expression.trim();
		
		values[0] = getNextLogicFromExpression(expression);
		
		expression = expression.substring(values[0].length());
				
		expression = expression.trim();		
		values[1] = expression.split(" ")[0];
		
		expression = expression.substring(values[1].length());
				
		expression = expression.trim();

		values[2] = getNextLogicFromExpression(expression);
		
		return values;
	}
	
	
	private int findRBrace(String expression, int lBrace){
		int lBraces = 1;
		for(int i = 1; i < expression.length(); i++){
			if(lBraces == 0){
				return i;
			}
			if(expression.charAt(i+lBrace) == '('){
				lBraces++;
			}else if(expression.charAt(i+lBrace) == ')'){
				lBraces--;
			}
		}
		return expression.length();
	}
	
	
	
}
