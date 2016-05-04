package math.arithmetic.problem;

import static org.junit.Assert.*;

import org.junit.Test;

import math.arithmetic.operand.ArithmeticVariable;

public class ModuleProblemTest {

	@Test
	public void test() {
		ArithmeticVariable a = new ArithmeticVariable("a", 5, false);
		ArithmeticVariable pow = new ArithmeticVariable("a", 2, false);
		ArithmeticVariable mod = new ArithmeticVariable("a", 5, false);
		
		SimpleModuleProblem moduleProblem = new SimpleModuleProblem(a, pow, mod);
		
		System.out.println(moduleProblem.getProblemExpression());
		System.out.println(moduleProblem.getAnswer()[0]);
		
		a = new ArithmeticVariable("a", 4, false);
		moduleProblem = new SimpleModuleProblem(a, pow, mod);
		
		System.out.println(moduleProblem.getProblemExpression());
		System.out.println(moduleProblem.getAnswer()[0]);
		
		a = new ArithmeticVariable("a", 3, false);
		moduleProblem = new SimpleModuleProblem(a, pow, mod);
		
		System.out.println(moduleProblem.getProblemExpression());
		System.out.println(moduleProblem.getAnswer()[0]);
	}

}
