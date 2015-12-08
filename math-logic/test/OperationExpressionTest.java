import static org.junit.Assert.*;

import org.junit.Test;

import factory.OperationExpression;

public class OperationExpressionTest {

	@Test
	public void stripBraces() {
		String expression = "( esta operación no debe) quitar los braces";
		
		String result = OperationExpression.stripOuterBraces(expression);
		
		assertEquals(expression, result);
		
		expression = "( Esta operación debería quitar los braces)";
		result = OperationExpression.stripOuterBraces(expression);
		assertNotEquals(expression, result);
		assertEquals("Esta operación debería quitar los braces", result);
		
		expression = "  ( Esta operación debería quitar los braces y los espacios en blanco)  ";
		result = OperationExpression.stripOuterBraces(expression);
		assertNotEquals(expression, result);
		assertEquals("Esta operación debería quitar los braces y los espacios en blanco", result);
		
		
		expression = "  ( Esta operación (debería quitar) los( braces y )los espacios en blanco)  ";
		result = OperationExpression.stripOuterBraces(expression);
		assertNotEquals(expression, result);
		assertEquals("Esta operación (debería quitar) los( braces y )los espacios en blanco", result);
	}

}
