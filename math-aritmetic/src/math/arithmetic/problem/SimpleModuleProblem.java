package math.arithmetic.problem;

import math.arithmetic.operand.ArithmeticVariable;
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
		return a.getValue()+"^"+pow.getValue()+"\\equiv x_{"+"("+mod.getValue()+")}";
	}

	@Override
	public String getSolvedExpression() {
		
		return null;
	}

	@Override
	public String[] getAnswer() {
		double powResult = new PowOperation().operate(a.getValue(), pow.getValue());
		
		return new String[]{new ModuleOperation().operate((int)powResult, mod.getValue().intValue()).toString()};
	}

	@Override
	public String[] getAnswerOptions(int options) {

		return null;
	}

}
