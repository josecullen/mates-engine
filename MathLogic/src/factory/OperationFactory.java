package factory;

import java.util.HashMap;

import logic.OperandNode;
import logic.Operation;
import logic.OperatorNode;

public class OperationFactory {
	public static OperatorNode getOperationLevel1(){
		OperatorNode operator = new OperatorNode(Operation.getRandom()); 
		
		operator.setOperands(
				new OperandNode(randomName(), randomBoolean(), randomBoolean(5)), 
				new OperandNode(randomName(), randomBoolean(), randomBoolean(3)));
		
		return operator;
	}
	/**
	 * @param expression Expresión matemática que será la estructura de la expresión. La cantidad de variables que aparecen también inciden en la expresión generada.
	 * @param signProbability Probabilidad de que aparezca un signo.
	 * @return
	 */
	public static OperatorNode getOperation(String expression, int signProbability){
		OperatorNode operator = (OperatorNode)OperationExpression.createFromExpression(expression); 
		
		HashMap<String, Boolean> map = operator.getVariables();
		String keys[] = new String[operator.getVariables().keySet().size()];
		keys = operator.getVariables().keySet().toArray(keys);
		
		for(String key : keys){
			map.put(key, randomBoolean());
		}		
		
		operator.randomize(signProbability, keys, map);
		
		return operator;
	}
	
	
	
	public static boolean randomBoolean(){
		int value = (int)(Math.random()*2);
		return value == 1;
	}
	
	/**
	 * 
	 * @param zeroProb Probabilidad de que salga cero. Mientras menos, más posible. 
	 * 0 o 1 siempre dará 0
	 * 2 será probabilidad dividida
	 * 3 será probabilidad de 1 en 3 que se de. 
	 * @return
	 */
	public static boolean randomBoolean(int zeroProb){
		int value = (int)(Math.random()*zeroProb);
		return value == 1;
	}
	
	public static String randomName(){
		String names[] = "abcdefghijk".split("");
		int value = (int)(Math.random()*names.length);
		return names[value];
	}
	public static String randomName(String names[]){
		int value = (int)(Math.random()*names.length);
		return names[value];
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < 50; i++){
			OperatorNode operator = getOperation("p and q", 3);
			System.out.println( operator.getExpression()+"\t"+operator.getResult());
			System.out.println();
			operator = getOperation("(p and q) or (r and s)", 3);
			System.out.println(operator.getExpression()+"\t"+operator.getResult());
			System.out.println();
		}
	}
}
