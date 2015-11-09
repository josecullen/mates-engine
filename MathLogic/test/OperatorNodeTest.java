import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import factory.OperationExpression;
import logic.OperatorNode;

public class OperatorNodeTest {

	@Test
	public void getVariables() {
		OperatorNode operator = (OperatorNode)OperationExpression.createFromExpression("(p or q) and s");
		HashMap<String, Boolean> map = operator.getVariables();
		
		assertTrue("No contiene p", map.containsKey("p"));
		assertTrue("No contiene q", map.containsKey("q"));
		assertTrue("No contiene s", map.containsKey("s"));
	}

}
