import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import factory.OperationFactory;
import logic.Operation;
import logic.OperatorNode;

public class OperationFactoryTest {

	@Test
	public void operationLevel1() {
		OperatorNode operation = factory.OperationFactory.getOperationLevel1();
		System.out.println(operation.getExpression());
	}
	
	@Test
	public void randomNames(){
		boolean 
			p = false, 
			q = false, 
			r = false,
			s = false;
		
		String operand; 
		for(int i = 0; i < 1000; i++){
			operand = OperationFactory.randomName(new String[]{"p","q","r","s"});
			if("p".equals(operand)){
				p = true;
			}else if("q".equals(operand)){
				q = true;
			}else if("r".equals(operand)){
				r = true;
			}else if("s".equals(operand)){
				s = true;
			}else{
				fail("Valor no esperado");
				return;
			}
			if(p && q && r && s){
				Assert.assertTrue(true);
				System.out.println(i);
				return;
			}
		}
		Assert.assertTrue("No generó p", p);
		Assert.assertTrue("No generó q", q);
		Assert.assertTrue("No generó r", r);
		Assert.assertTrue("No generó s", s);
		
	}

}
