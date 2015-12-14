package math.arithmetic.problem;

import math.arithmetic.operand.ArithmeticVariable;
import math.arithmetic.operand.VariableUtil;

public class EquationProblem implements Problem {
	/**
	 * En este caso las variables son las soluciones.
	 */
	ArithmeticVariable
		a, x1,x2,x3;
	
	String 
		problemExpression,
		solvedExpression,
		answer;
	String[] answerOptions;
	
	EquationLevel level;
	
	public EquationProblem(EquationLevel level, ArithmeticVariable a,ArithmeticVariable x1, ArithmeticVariable x2, ArithmeticVariable x3) {
		this.level = level;
		this.a = a;
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;		
	}

	@Override
	public String getProblemExpression() {
		
		
		switch (level) {
			case LEVEL_1:
				return getLevel1Expression();
			case LEVEL_2:
				return getLevel2Expression();
			case LEVEL3:
				return "";
			default:
				return "";
		}
	}

	@Override
	public String getSolvedExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAnswer() {
		StringBuilder answer = new StringBuilder();
		
		switch (level) {
			case LEVEL_1:
				answer
					.append("[ ")
					.append(VariableUtil.getValueString(x1))
					.append(" ]");				
				return answer.toString();
			case LEVEL_2:
				answer
					.append("[ ")
					.append(VariableUtil.getValueString(x1))
					.append(" ")
					.append(VariableUtil.getValueString(x2))
					.append(" ]");
				return answer.toString();
			case LEVEL3:
				answer
					.append("[ ")
					.append(VariableUtil.getValueString(x1))
					.append(" ")
					.append(VariableUtil.getValueString(x2))
					.append(" ")
					.append(VariableUtil.getValueString(x3))
					.append(" ]");
			return answer.toString();			
			default:
				return "";
		}		
	}

	@Override
	public String[] getAnswerOptions() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private String getLevel1Expression(){
		StringBuilder expression = new StringBuilder();
		
		double b = -1 * a.getValue() * x1.getValue();
		
		expression
			.append(VariableUtil.getValueString(a))
			.append("x")
			.append(" + ")
			.append(VariableUtil.getValueString(b))
			.append(" = 0");		
		
		return expression.toString();
	}
	
	private String getLevel2Expression(){
		StringBuilder expression = new StringBuilder();
		
		double b = -1 * a.getValue() * (x1.getValue() + x2.getValue());
		double c = a.getValue() * x1.getValue() * x2.getValue();
		
		expression
			.append(VariableUtil.getValueString(a))
			.append("x^2")
			.append(" + ")
			.append(VariableUtil.getValueString(b))
			.append("x")
			.append(" + ")
			.append(VariableUtil.getValueString(c))
			.append(" = 0");		
		
		return expression.toString();
	}
	

}
