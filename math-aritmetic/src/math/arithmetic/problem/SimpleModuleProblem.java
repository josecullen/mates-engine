package math.arithmetic.problem;

import static math.arithmetic.operand.ArithmeticVariableUtil.getValueString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
	
	private List<Integer> getAllAnswers(int module){
		List<Integer> allAnswers = new ArrayList<Integer>();
		
		for(int i = 1; i <= module; i++){
			allAnswers.add(i % module);
		}
		
		return allAnswers;
	}

	@Override
	public String[] getAnswerOptions(int options) {
		int result = (int)result();
		String[] stringAnswers = null;
		List<Integer> allAnswers = getAllAnswers(mod.getValue().intValue());
		
		
		if(allAnswers.size() < 5){
			stringAnswers = new String[allAnswers.size()];
			for(int i = 0; i < allAnswers.size(); i++){
				stringAnswers[i] = getValueString(allAnswers.get(i).doubleValue());
			}			
		}else{
			stringAnswers = new String[5];
			allAnswers = allAnswers.stream().filter(value -> 
				!value.equals(result))
					.unordered()
					.collect(Collectors.toList());
			
			Collections.shuffle(allAnswers);
			allAnswers.subList(0, 4);
			
			allAnswers.add((int)Math.random()*5, (int)result);
			
			
			for(int i = 0; i < 5; i++){
				stringAnswers[i] = getValueString(allAnswers.get(i).doubleValue());
			}
			
			
			
		}
		return stringAnswers;
	}
	
	

}
