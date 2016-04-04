package parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import math.arithmetic.broadcast.ArithmeticBroadcastUtil;
import math.arithmetic.parser.ArithmeticParser;
import math.core.tree.OperationNode;

public class ArithmeticParserTest {

	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testNormal() {
		
		ArithmeticBroadcastUtil br = createFromParser("(a + b)");
		assertContains(br, "ab");
		
		

		
		br = createFromParser("a + b");
		assertContains(br, "ab");
		
		br = createFromParser("(a + b) + (c + d)");
		assertContains(br, "abcd");
		
		br = createFromParser("( ((a + b) + (c + d)) + e )");
		assertContains(br, "abcde");
		
//		br = createFromParser("(a + b) + (c + d) + e");
//		assertContains(br, "abcde");
		
		br = createFromParser("( e + ((a + b) + (c + d) ) )");
		assertContains(br, "abcde");
		
		br = createFromParser("((a + b) + (c + d) )+ (f + g)");
		assertContains(br, "abcdfg");
		
		System.out.println(br.getExpression());
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testLargerForms(){
		OperationNode<Double> operation = (OperationNode<Double>)ArithmeticParser.getInstance().parse("(a + b) + (5 + 2) + e");
		
		ArithmeticBroadcastUtil br = new ArithmeticBroadcastUtil(operation);
		
		System.out.println(br.getExpression());
		
//		assertEquals(4, br.getExpression().split("\\(").length);
//		assertTrue(br.getExpression().contains("e"));	
	}
	
	/**
	 * Error cuando la variable está pegada al operador
	 */
	@Test
	public void testError() {
		
		OperationNode<Double> operation = (OperationNode<Double>)ArithmeticParser.getInstance().parse("(a + b) + c");
		
		ArithmeticBroadcastUtil br = new ArithmeticBroadcastUtil(operation);
		System.out.println(br.getExpression());
	}
	
	private void assertContains(ArithmeticBroadcastUtil br, String expectedCharacters) {
		String chars[] = expectedCharacters.split("");
		String expression = br.getExpression();
		for(int i = 0; i < chars.length; i++){
			assertTrue(expression.contains(chars[i]));
		}
	}

	private ArithmeticBroadcastUtil createFromParser(String form){
		OperationNode<Double> operation = (OperationNode<Double>)ArithmeticParser.getInstance().parse(form);
		
		ArithmeticBroadcastUtil br = new ArithmeticBroadcastUtil(operation);
		
		return br;
	}

}
