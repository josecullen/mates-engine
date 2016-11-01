package builders;

import static org.junit.Assert.*;
import math.arithmetic.problem.EquationLevel;
import math.arithmetic.problem.EquationProblem;

import org.junit.Test;

public class EquationBuilderTest {

	@Test
	public void test() {
		ArithmeticVariableBuilder avb = new ArithmeticVariableBuilder()
			.divisionFactor(1)
			.max(5)
			.min(1)
			.probablySign(0.6);
			
		
		EquationBuilder equationBuilder = new EquationBuilder()
			.buildWithLevel(EquationLevel.LEVEL_1)
			.buildWithA(avb)
			.buildWithX1(avb)
			.buildWithX2(avb)
			.buildWithX3(avb);
		
		System.out.println("LEVEL_1");
		EquationProblem problem  = equationBuilder.build();	
		printProblem(problem);
		
		System.out.println("\n");
		
		System.out.println("LEVEL_2");
		problem = equationBuilder.buildWithLevel(EquationLevel.LEVEL_2).build();	
		printProblem(problem);
		
		System.out.println("\n");
		
		System.out.println("LEVEL_3");
		problem = equationBuilder.buildWithLevel(EquationLevel.LEVEL_3).build();		
		printProblem(problem);


			
	}

	private void printProblem(EquationProblem problem) {
		System.out.println(problem.getProblemExpression());
		
		for(String answer : problem.getAnswer()){
			System.out.print(answer);
		}
		
		System.out.println();			
		
		for(String option : problem.getAnswerOptions(5)){
			System.out.print(option);
		}		
	}

}
