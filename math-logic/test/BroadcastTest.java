import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import logic.OperandNode;
import logic.Operation;
import logic.OperatorNode;

public class BroadcastTest {

	@Test
	public void broadcastSimple(){
		HashMap<String, Boolean> map = getMap(true, false, true, true);
		
		OperatorNode operator = new OperatorNode(Operation.AND);
		operator.setOperands(new OperandNode("p", true), new OperandNode("q", true));
		
		Assert.assertEquals("p and q", operator.getExpression());
		Assert.assertTrue(operator.getResult());
		
		operator.broadcast("values", map);
		Assert.assertFalse(operator.getResult());
	}
	
	@Test
	public void broadCastSignSimple(){
		HashMap<String, Boolean> map = getMap(true, false, true, true);
		
		OperatorNode operator = new OperatorNode(Operation.AND);
		operator.setOperands(new OperandNode("p", true), new OperandNode("p", true, true));

		Assert.assertEquals("p and -p", operator.getExpression());
		Assert.assertFalse(operator.getResult());

		operator.setOperation(Operation.OR);
		Assert.assertEquals("p or -p", operator.getExpression());
		Assert.assertTrue(operator.getResult());
	}

	HashMap<String, Boolean> getMap(Boolean p, Boolean q, Boolean r, Boolean s){
		HashMap<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("p", p);
		map.put("q", q);
		map.put("r", r);
		map.put("s", s);
		
		return map;
	}
}
