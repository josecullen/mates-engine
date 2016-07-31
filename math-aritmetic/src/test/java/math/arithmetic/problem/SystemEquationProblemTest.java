package math.arithmetic.problem;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import builders.ArithmeticVariableBuilder;
import builders.OperationBuilder;


public class SystemEquationProblemTest {

	ArithmeticVariableBuilder avbConst = new ArithmeticVariableBuilder()
			.min(1).max(5).divisionFactor(1).probablySign(0);
	
	ArithmeticVariableBuilder avbX = new ArithmeticVariableBuilder()
			.min(1).max(5).divisionFactor(1).probablySign(0);
	
	OperationBuilder operationBuilder = new OperationBuilder()
			.buildWithProbablySign(0).buildWithThisOperations("+\\*/-");
	
	@Test
	public void testLevel1(){	
		
		SystemEquationProblem systemEquationProblem = new SystemEquationProblem(EquationLevel.LEVEL_1, avbConst, avbX, operationBuilder);
		
		for (int i = 0; i < 5; i++) {
			System.out.println(systemEquationProblem.getProblemExpression());
			Arrays.asList(systemEquationProblem.getAnswer()).forEach(answer ->{
				System.out.print(answer+" ");
			});
			System.out.println("\n");
			systemEquationProblem.renew();
		}		
		
	}
	
	@Test
	public void testLevel2(){	
		
		SystemEquationProblem systemEquationProblem = new SystemEquationProblem(EquationLevel.LEVEL_2, avbConst, avbX, operationBuilder);
		
		for (int i = 0; i < 5; i++) {
			System.out.println(systemEquationProblem.getProblemExpression());
			Arrays.asList(systemEquationProblem.getAnswer()).forEach(answer ->{
				System.out.print(answer+" ");
			});
			System.out.println("\n");
			systemEquationProblem.renew();
		}		
		
	}
	
	
	@Test
	public void testLevel3(){	
		
		SystemEquationProblem systemEquationProblem = new SystemEquationProblem(EquationLevel.LEVEL_3, avbConst, avbX, operationBuilder);
		
		for (int i = 0; i < 5; i++) {
			System.out.println(systemEquationProblem.getProblemExpression());
			Arrays.asList(systemEquationProblem.getAnswer()).forEach(answer ->{
				System.out.print(answer+" ");
			});
			System.out.println("\n");
			systemEquationProblem.renew();
		}		
		
	}
	
	@Test
	public void testSubstractionProblem(){	
		OperationBuilder operationBuilder = new OperationBuilder()
				.buildWithProbablySign(0).buildWithThisOperations("-");
					
		SystemEquationProblem systemEquationProblem = new SystemEquationProblem(EquationLevel.LEVEL_2, avbConst, avbX, operationBuilder);
		
		for (int i = 0; i < 5; i++) {
			System.out.println(systemEquationProblem.getProblemExpression());
			Arrays.asList(systemEquationProblem.getAnswer()).forEach(answer ->{
				System.out.print(answer+" ");
			});
			System.out.println("\n");
			systemEquationProblem.renew();
		}		
		
	}
	
	
	
	
	
	
}
