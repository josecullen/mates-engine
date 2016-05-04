package math.arithmetic.problem;

import java.util.List;
import static math.arithmetic.operand.ArithmeticVariableUtil.*;

import math.arithmetic.operand.ArithmeticVariable;
import math.arithmetic.operand.ArithmeticVariableUtil;
import math.arithmetic.operation.ModuleOperation;
import math.arithmetic.operation.PowOperation;
import math.core.problem.Problem;

public class SimpleModuleProblem implements Problem {
	private ArithmeticVariable a, pow, mod;
	
	public SimpleModuleProblem(ArithmeticVariable a, ArithmeticVariable pow, ArithmeticVariable mod) {
		this.a = a;
		this.pow = pow;
		this.mod = mod;
	}
	
	@Override
	public String getProblemExpression() {
		return getValueString(a.getValue())+"^"+getValueString(pow.getValue())+"\\equiv x_{"+"("+getValueString(mod.getValue())+")}";
	}

	@Override
	public String getSolvedExpression() {
		
		return null;
	}

	@Override
	public String[] getAnswer() {		
		return new String[]{getValueString(result())};
	}
	
	public double result(){
		double powResult = new PowOperation().operate(a.getValue(), pow.getValue());
		return new ModuleOperation().operate((int)powResult, mod.getValue().intValue());
	}

	@Override
	public String[] getAnswerOptions(int options) {
		double[] answers = new double[options];
		String[] stringAnswers = new String[options];
		
		
		List<Double> posibleAnswers = 
				ArithmeticVariableUtil.getValuesWithDivisionFactor(20, result(), 1);
		
		answers[0] = result();
		
		for(int i = 1; i < options; i++){
			answers[i] = posibleAnswers.remove((int)Math.random()*posibleAnswers.size());
			if(answers[i] == answers[0]){
				i--;
			}
		}
		
		for(int i = 0; i < answers.length; i++)
			stringAnswers[i] = getValueString(answers[i]);		
		
		int newPosition = (int)(Math.random()*3);
		String auxAnswer = stringAnswers[newPosition];
		stringAnswers[newPosition] = stringAnswers[0];
		stringAnswers[0] = auxAnswer;
		
		return stringAnswers;
	}

}
