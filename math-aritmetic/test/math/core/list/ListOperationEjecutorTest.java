package math.core.list;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import math.arithmetic.operation.AdditionOperation;
import math.arithmetic.operation.MultiplicationOperation;
import math.arithmetic.operation.SubstractionOperation;

public class ListOperationEjecutorTest{
	
	@Test
	public void test(){
		ListOperationEjecutor list = new ListOperationEjecutor();
		
		list.left(1)
			.add(2)
			.multiply(3)
			.multiply(4)
			.add(5)
			.multiply(6)
			.add(3);
		
		assertEquals(58, list.resolve(),0.0);
	}
	

	@Test
	public void testFirstMultiply(){
		ListOperationEjecutor list = new ListOperationEjecutor();
		
		list
			.left(2)
			.multiply(3)
			.add(5)
			.add(3)
			.multiply(4);

		assertEquals(23, list.resolve(),0.0);		
	}
	
	@Test
	public void testWithSubstractions(){
		ListOperationEjecutor list = new ListOperationEjecutor();
		
		list
			.left(2)
			.and(new AdditionOperation(), 3)
			.and(new SubstractionOperation(), 5);
		
		assertEquals(0, list.resolve(),0.0);		
		
		list = new ListOperationEjecutor();
		
		list
			.left(10)
			.and(new AdditionOperation(), 5)
			.and(new MultiplicationOperation(), 3)
			.and(new SubstractionOperation(), 1);

		assertEquals(24, list.resolve(),0.0);		
	}	
	
	
	
}