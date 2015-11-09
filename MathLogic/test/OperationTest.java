import org.junit.Assert;
import org.junit.Test;

import logic.Operation;

public class OperationTest {
	
	
	@Test
	public void randomTest(){
		boolean 
			and = false, 
			or = false, 
			then = false;
		
		Operation operation; 
		for(int i = 0; i < 1000; i++){
			operation = Operation.getRandom();
			if(operation.equals(Operation.AND)){
				and = true;
			}else if(operation.equals(Operation.THEN)){
				then = true;
			}else{
				or = true;
			}
			if(and && or && then){
				Assert.assertTrue(true);
				System.out.println(i);
				return;
			}
		}
		Assert.assertTrue("No generó AND", and);
		Assert.assertTrue("No generó OR", or);
		Assert.assertTrue("No generó THEN", then);
		
	}
}
