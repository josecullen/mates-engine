package math.arithmetic.problem;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import math.arithmetic.operand.ArithmeticVariable;

public class ModuleProblemTest {

	@Test
	public void test() {
		ArithmeticVariable a = new ArithmeticVariable("a", 5, false);
		ArithmeticVariable pow = new ArithmeticVariable("a", 2, false);
		ArithmeticVariable mod = new ArithmeticVariable("a", 5, false);
		
		SimpleModuleProblem moduleProblem = new SimpleModuleProblem(a, pow, mod);

		printProblem(moduleProblem);
		
		
		a = new ArithmeticVariable("a", 4, false);
		moduleProblem = new SimpleModuleProblem(a, pow, mod);
		
		printProblem(moduleProblem);
		
		a = new ArithmeticVariable("a", 3, false);
		moduleProblem = new SimpleModuleProblem(a, pow, mod);

		printProblem(moduleProblem);
}
	
	private void printProblem(SimpleModuleProblem problem){
		System.out.println("---		problem		---");
		System.out.println("Problem Expression:");
		System.out.println(problem.getProblemExpression());
		System.out.println("\nAnswer:");
		System.out.println(problem.getAnswer()[0]);
		System.out.println("\nAnswer Options:");
		Arrays.asList(problem.getAnswerOptions(0)).forEach(answer ->{
			System.out.print(answer+" ");
		});
		System.out.println("\n---------------------------------------\n");
		
	}
	
}
