package math.arithmetic.problem;

import math.arithmetic.operand.ArithmeticVariable;
import math.arithmetic.operand.ArithmeticVariableUtil;

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
			case LEVEL_3:
				return getLevel3Expression();
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
					.append(ArithmeticVariableUtil.getValueString(x1))
					.append(" ]");				
				return answer.toString();
			case LEVEL_2:
				answer
					.append("[ ")
					.append(ArithmeticVariableUtil.getValueString(x1))
					.append(" ")
					.append(ArithmeticVariableUtil.getValueString(x2))
					.append(" ]");
				return answer.toString();
			case LEVEL_3:
				answer
					.append("[ ")
					.append(ArithmeticVariableUtil.getValueString(x1))
					.append(" ")
					.append(ArithmeticVariableUtil.getValueString(x2))
					.append(" ")
					.append(ArithmeticVariableUtil.getValueString(x3))
					.append(" ]");
			return answer.toString();			
			default:
				return "";
		}		
	}

	@Override
	public String[] getAnswerOptions(int options) {
		
		double[] answers = new double[options];
		String[] stringAnswers = new String[options];		
		int correctsCount = 0;
		switch (level) {
			case LEVEL_1:
				answers[0] = x1.getValue();
				stringAnswers[0] = ArithmeticVariableUtil.getValueString(answers[0]);

				correctsCount = 1;
				break;
			case LEVEL_2:
				answers[0] = x1.getValue();		
				answers[1] = x2.getValue();	
				stringAnswers[0] = ArithmeticVariableUtil.getValueString(answers[0]);
				stringAnswers[1] = ArithmeticVariableUtil.getValueString(answers[1]);			
				correctsCount = 2;		
				break;
			case LEVEL_3:
				answers[0] = x1.getValue();
				answers[1] = x2.getValue();
				answers[2] = x3.getValue();
				stringAnswers[0] = ArithmeticVariableUtil.getValueString(answers[0]);
				stringAnswers[1] = ArithmeticVariableUtil.getValueString(answers[1]);
				stringAnswers[2] = ArithmeticVariableUtil.getValueString(answers[2]);
				
				correctsCount = 3;
				break;
		}		
		
		for(int i = correctsCount; i < answers.length; i++){
			answers[i] = ArithmeticVariableUtil.getNoRepeatedAnswer(answers[(int)(Math.random()*correctsCount)], answers, 1);
			stringAnswers[i] = ArithmeticVariableUtil.getValueString(answers[i]);
		}
				
		for(int i = correctsCount; i < answers.length; i++){
			int newPosition = (int)(Math.random()*options);
			String auxAnswer = stringAnswers[newPosition];
			stringAnswers[newPosition] = stringAnswers[i];
			stringAnswers[i] = auxAnswer;
		}
		
		return stringAnswers;
	}
	
	
	private String getLevel1Expression(){
		StringBuilder expression = new StringBuilder();
		
		double b = -1 * a.getValue() * x1.getValue();
		
		expression
			.append(ArithmeticVariableUtil.getValueString(a))
			.append("x")
			.append(" + ")
			.append(ArithmeticVariableUtil.getValueString(b))
			.append(" = 0");		
		
		return expression.toString();
	}
	
	private String getLevel2Expression(){
		StringBuilder expression = new StringBuilder();
		
		double b = -1 * a.getValue() * (x1.getValue() + x2.getValue()); // -a(x1 + x2)
		double c = a.getValue() * x1.getValue() * x2.getValue();
		
		expression
			.append(ArithmeticVariableUtil.getValueString(a))
			.append("x^2")
			.append(" + ")
			.append(ArithmeticVariableUtil.getValueString(b))
			.append("x")
			.append(" + ")
			.append(ArithmeticVariableUtil.getValueString(c))
			.append(" = 0");		
		
		return expression.toString();
	}
	
	private String getLevel3Expression() {
		StringBuilder expression = new StringBuilder();
		
		double b = -1 * a.getValue() * (x1.getValue() + x2.getValue() + x3.getValue()); // -a( x1 + x2 + x3 )
		double c = a.getValue() * ( x1.getValue() * x2.getValue() + x1.getValue() * x3.getValue() + x2.getValue() * x3.getValue() ); // -a( x1 * x2 + x1 * x3 + x2 * x3 )
		double d = -1 * a.getValue() * x1.getValue() * x2.getValue() * x3.getValue();
		
		expression
			.append(ArithmeticVariableUtil.getValueString(a))
			.append("x^3")
			.append(" + ")
			.append(ArithmeticVariableUtil.getValueString(b))
			.append("x^2")
			.append(" + ")
			.append(ArithmeticVariableUtil.getValueString(c))
			.append("x")
			.append(" + ")
			.append(ArithmeticVariableUtil.getValueString(d))
			.append(" = 0");		
		
		return expression.toString();
	}

}
