package factory;

import logic.LogicNode;
import logic.OperandNode;
import logic.Operation;
import logic.OperatorNode;

public class OperationExpression {
	public static LogicNode createFromExpression(String expression){
		
		boolean hasSign = hasSign(expression);
		if(isOperand(expression)){
			return new OperandNode(expression.trim());
		}else{
			OperatorNode operator;
			expression = stripOuterBraces(expression);
			String values[] = analyzeMorf(expression);
			
			operator = new OperatorNode(Operation.getOperation(values[1]));
			operator.setSign(hasSign);
			operator.setOperands(getLogicFromString(values[0]), getLogicFromString(values[2]));
			return operator;
		}
		
	}
	
	public static LogicNode getLogicFromString(String value){
		value = value.trim();
		if(value.length() > 2){
			return createFromExpression(value);
		}else{
			return new OperandNode(value);
		}
	}
	
	public static boolean isOperand(String expression){
		expression = removeSign(expression);			
		return expression.trim().length() == 1;
	}
	
	public static String removeSign(String expression){
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
	public static boolean hasSign(String expression){

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
	public static String stripOuterBraces(String expression){
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
	
	public static String getNextLogicFromExpression(String expression){
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
	
	public static String[] analyzeMorf(String expression){
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
	
	
	public static int findRBrace(String expression, int lBrace){
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
	
	public static void main(String[] args) {
		analyzeMorf("(p o q) or (p y r)");
		LogicNode lnode = createFromExpression("((p or q) then s)");		
		System.out.println(lnode.getExpression());
		lnode = createFromExpression("(-(p or q) then s)");
		System.out.println(lnode.getExpression());
		lnode = createFromExpression("((p or -q) and s)");
		System.out.println(lnode.getExpression());
		System.out.println(createFromExpression("((p or q) then -s)").getExpression());
	}
}
