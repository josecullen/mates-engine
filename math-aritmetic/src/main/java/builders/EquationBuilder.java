package builders;

import math.arithmetic.problem.EquationLevel;
import math.arithmetic.problem.EquationProblem;

public class EquationBuilder {
	
	ArithmeticVariableBuilder 
		a, x1, x2, x3;
	
	EquationLevel level;
	
	public EquationBuilder buildWithLevel(EquationLevel level){
		this.level = level;
		return this;
	}
	
	public EquationBuilder buildWithA(ArithmeticVariableBuilder a){
		this.a = a;
		return this;
	}
	
	public EquationBuilder buildWithX1(ArithmeticVariableBuilder x1){
		this.x1 = x1;
		return this;
	}
	
	public EquationBuilder buildWithX2(ArithmeticVariableBuilder x2){
		this.x2 = x2;
		return this;
	}
	
	public EquationBuilder buildWithX3(ArithmeticVariableBuilder x3){
		this.x3 = x3;
		return this;
	}
	
	public EquationProblem build(){
		EquationProblem problem = 
				new EquationProblem(
						level, 
						a.build(), 
						x1.build(), 
						x2.build(), 
						x3.build());	
		
		return problem;
	}
	
	
}
