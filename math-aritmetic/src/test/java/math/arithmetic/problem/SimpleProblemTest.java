package math.arithmetic.problem;

import static org.junit.Assert.*;

import org.junit.Test;

import builders.ArithmeticVariableBuilder;
import builders.OperationBuilder;
import math.core.problem.Problem;

public class SimpleProblemTest {

	@Test
	public void test() {
		
		ArithmeticVariableBuilder avb = new ArithmeticVariableBuilder().divisionFactor(1).max(5).min(1).probablySign(0);
		OperationBuilder ob = new OperationBuilder().buildWithProbablySign(0).buildWithThisOperations("+-");
		
		SimpleProblem problem = new SimpleProblem("a + b", avb, ob);
		
		System.out.println();
		
		
	}

}
