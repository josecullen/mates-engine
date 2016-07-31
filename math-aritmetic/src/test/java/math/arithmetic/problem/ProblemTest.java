package math.arithmetic.problem;

import static org.junit.Assert.assertEquals;
import math.core.operand.Variable;

import org.junit.Test;

import builders.ArithmeticVariableBuilder;
import builders.OperationBuilder;

public class ProblemTest {

	@Test
	public void testProblemExpression() {
		SimpleProblem problem = new SimpleProblem("a + b");
		//assertEquals("( {0} + {0} ) = ", problem.getProblemExpression());
	}
	
	@Test
	public void testSolvedExpression(){
		SimpleProblem problem = new SimpleProblem("a + b");
	//	assertEquals("( {0} + {0} ) = {0}", problem.getSolvedExpression());
	}
	
	@Test
	public void testCorrectAnswer(){
		SimpleProblem problem = new SimpleProblem("a + b");
		problem.renew();
		System.out.println(problem.getAnswer());
	}
	
	@Test
	public void testAnswerOptions(){
		System.out.println("Test answer options");
		SimpleProblem problem = new SimpleProblem("a + b");
		problem.renew();
		System.out.println("Correct Answer : "+problem.getAnswer());
		for(String option : problem.getAnswerOptions(5)){
			System.out.println(option);
		}
	}
	
	@Test
	public void testRenew(){
		SimpleProblem problem = new SimpleProblem("a + b");
		System.out.println(problem.getProblemExpression());
		System.out.println(problem.getSolvedExpression());
		problem.renew();
		System.out.println(problem.getProblemExpression());
		System.out.println(problem.getSolvedExpression());
		problem.renew();
		System.out.println(problem.getProblemExpression());
		System.out.println(problem.getSolvedExpression());
		problem.renew();
		System.out.println(problem.getProblemExpression());
		System.out.println(problem.getSolvedExpression());
		problem.renew();
		System.out.println(problem.getProblemExpression());
		System.out.println(problem.getSolvedExpression());
	}
	
	@Test
	public void testProbablySign(){

		ArithmeticVariableBuilder rvi = new ArithmeticVariableBuilder().max(10).min(0).probablySign(0.5);
		OperationBuilder rob = new OperationBuilder().buildWithProbablySign(0.2).buildWithThisOperations("+*");
		
		SimpleProblem problem = new SimpleProblem("(a + b)", rvi, rob );
		
		int countSign = 0;
		for(int i = 0; i < 1000; i++){
			problem.renew();
//			System.out.println(problem.getProblemExpression());
			for(Variable<Double> var : problem.br.getVariablesBroadcast()){
				if(var.getSign()) countSign++;
			}
		}
		
		assertEquals(1000, countSign, 100);
		
		countSign = 0;
		rvi.setProbablySign(0.2);
		for(int i = 0; i < 1000; i++){
			problem.renew();
			System.out.println(problem);
			for(Variable<Double> var : problem.br.getVariablesBroadcast()){
				if(var.getSign()) countSign++;
			}
		}
		
		assertEquals(400, countSign, 100);
		
	}
	
	@Test
	public void testDivideCero(){
		ArithmeticVariableBuilder rvi = new ArithmeticVariableBuilder().max(3).min(0).probablySign(0.1);
		OperationBuilder rob = new OperationBuilder().buildWithProbablySign(0.2).buildWithThisOperations("/");
		
		SimpleProblem problem = new SimpleProblem("(a + b)", rvi, rob );
		
		for(int i = 0; i < 1000; i++){
			problem.renew();
//			System.out.println(problem.getProblemExpression());
//
//			System.out.println(problem.getSolvedExpression());	
		}
		
		
	}

}
