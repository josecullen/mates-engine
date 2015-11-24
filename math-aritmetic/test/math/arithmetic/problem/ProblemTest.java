package math.arithmetic.problem;

import static org.junit.Assert.*;

import org.junit.Test;

import math.arithmetic.operand.ArithmeticVariable.RandomVariableInstancer;
import math.arithmetic.operation.OperationUtil.RandomOperationBuilder;
import math.core.operand.Variable;

public class ProblemTest {

	@Test
	public void testProblemExpression() {
		Problem problem = new Problem("a + b");
		assertEquals("( 0 + 0 ) = ?", problem.getProblemExpression());
	}
	
	@Test
	public void testSolvedExpression(){
		Problem problem = new Problem("a + b");
		assertEquals("( 0 + 0 ) = 0.0", problem.getSolvedExpression());
	}
	
	@Test
	public void testCorrectAnswer(){
		Problem problem = new Problem("a + b");
		problem.renew();
		System.out.println(problem.getAnswer());
	}
	
	@Test
	public void testAnswerOptions(){
		System.out.println("Test answer options");
		Problem problem = new Problem("a + b");
		problem.renew();
		System.out.println("Correct Answer : "+problem.getAnswer());
		for(Double option : problem.getAnswerOptions()){
			System.out.println(option);
		}
	}
	
	@Test
	public void testRenew(){
		Problem problem = new Problem("a + b");
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

		RandomVariableInstancer rvi = new RandomVariableInstancer().max(10).min(0).probablySign(0.5);
		RandomOperationBuilder rob = new RandomOperationBuilder().buildWithProbablySign(0.2).buildWithThisOperations("+*");
		
		Problem problem = new Problem("(a + b)", rvi, rob );
		
		int countSign = 0;
		for(int i = 0; i < 1000; i++){
			problem.renew();
			System.out.println(problem.getProblemExpression());
			for(Variable<Double> var : problem.br.getVariablesBroadcast()){
				if(var.getSign()) countSign++;
			}
		}
		
		assertEquals(1000, countSign, 100);
		
		countSign = 0;
		rvi.setProbablySign(0.2);
		for(int i = 0; i < 1000; i++){
			problem.renew();
			for(Variable<Double> var : problem.br.getVariablesBroadcast()){
				if(var.getSign()) countSign++;
			}
		}
		
		assertEquals(400, countSign, 100);
		
	}

}
