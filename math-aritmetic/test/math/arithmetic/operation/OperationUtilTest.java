package math.arithmetic.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import math.core.operation.Operation;

public class OperationUtilTest {

	@Test
	public void testRandom() {
		Operation<Double> operation;
		
		int 
			addCount = 0,
			subCount = 0,
			divCount = 0,
			multiCount = 0,
			powCount = 0;
		
		for(int i = 0; i < 1000; i++){
			operation = new OperationUtil.RandomOperationBuilder().build();

			assertFalse(operation.getSign());
//			assertTrue(operation.getExpression().matches("[+*/-^]"));
			
			if(operation.getExpression() == "+")
				addCount++;
			else if(operation.getExpression() == "-")
				subCount++;
			else if(operation.getExpression() == "*")
				multiCount++;
			else if(operation.getExpression() == "/")
				divCount++;
			else
				powCount++;
		}
		
		assertEquals(200, addCount, 50);
		assertEquals(200, subCount, 50);
		assertEquals(200, divCount, 50);
		assertEquals(200, multiCount, 50);	
		assertEquals(200, powCount, 50);	

	}
	
	@Test
	public void testRandomProbablySign(){
		Operation<Double> operation;
		int 
			signCount = 0;
		
		for(int i = 0; i < 1000; i++){
			operation = new OperationUtil.RandomOperationBuilder().buildWithProbablySign(0.5).build();
			
//			assertTrue(operation.getExpression().matches("[+*/-^]"));
			
			if(operation.getSign())
				signCount++;		
		}
		
		assertEquals(500, signCount, 100);
		
		signCount = 0;
		
		for(int i = 0; i < 1000; i++){
			operation = new OperationUtil.RandomOperationBuilder().buildWithProbablySign(0.2).build();

//			assertTrue(operation.getExpression().matches("[+*/-^]"));
			
			if(operation.getSign())
				signCount++;		
		}
		
		assertEquals(200, signCount, 100);
	}
	
	@Test
	public void testFilteredOperations(){
		Operation<Double> operation;
		String operationsPattern = "+-";
		
		for(int i = 0; i < 1000; i++){
			operation = new OperationUtil.RandomOperationBuilder().buildWithThisOperations(operationsPattern).build();
			assertTrue(operation.getExpression().matches("["+operationsPattern+"]"));			
		}
		
		operationsPattern = "-";
		
		for(int i = 0; i < 1000; i++){
			operation = new OperationUtil.RandomOperationBuilder().buildWithThisOperations(operationsPattern).build();
			assertTrue(operation.getExpression().matches("["+operationsPattern+"]"));			
		}
		
		operationsPattern = "-+/";
		
		for(int i = 0; i < 1000; i++){
			operation = new OperationUtil.RandomOperationBuilder().buildWithThisOperations(operationsPattern).build();
			assertTrue(operation.getExpression().matches("["+operationsPattern+"]"));			
		}
		
	}
	
	
	
	
	

}
