package analyzer;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import factory.OperationExpression;
import logic.OperatorNode;

public class TruthAnalyzer {
	boolean analyzeOperation(String expression){
		return analyzeOperation((OperatorNode)OperationExpression.createFromExpression(expression));
	}
	
	boolean analyzeOperation(OperatorNode operator){
		return operator.getResult();
	}
	
	public static TruthOption analyzeTruthOptions(String expression){
		return analyzeTruthOptions((OperatorNode)OperationExpression.createFromExpression(expression));
	}
	
	
	public static TruthOption analyzeTruthOptions(OperatorNode operator){
		IterativeOperation iterativeOperation = new IterativeOperation();
		iterativeOperation.operate(operator);
		if(iterativeOperation.hasTrue && iterativeOperation.hasFalse){
			return TruthOption.CONTINGENCY;
		}else if(iterativeOperation.hasTrue){
			return TruthOption.TAUTOLOGY;
		}else{
			return TruthOption.CONTRADICTION;
		}
	}
	
}




class IterativeOperation{
	boolean hasTrue = false;
	boolean hasFalse = false;
	
	void operate(OperatorNode operator){
		int index = 0;
		HashMap<String, Boolean> map =  operator.getVariables();
		String[] keys = new String[map.keySet().size()];		
		keys =	map.keySet().toArray(keys);
		
		map.put(keys[index], false);
		operate(operator, map, (index+1));
		map.put(keys[index], true);
		operate(operator, map, (index+1));
	}
	
	void operate(OperatorNode operator,HashMap<String, Boolean> map, int index){
		
		String[] keys = new String[map.keySet().size()];		
		keys =	map.keySet().toArray(keys);
		if(index == keys.length){
			// Caso (p and -p)
			index = 0;
			map.put(keys[index], false);
			operator.broadcast("values", map);
			boolean result = operator.getResult();
			if(result)	hasTrue = true;
			else		hasFalse = true;
			
			map.put(keys[index], true);
			operator.broadcast("values", map);
			result = operator.getResult();
			if(result)	hasTrue = true;
			else		hasFalse = true;
			
			if(hasTrue && hasFalse){
				return;
			}			
		}else if(index != keys.length-1){
			map.put(keys[index], false);
			operate(operator, map, (index+1));
			map.put(keys[index], true);
			operate(operator, map, (index+1));
		}else{
			map.put(keys[index], false);
			operator.broadcast("values", map);
			boolean result = operator.getResult();
			if(result)	hasTrue = true;
			else		hasFalse = true;
			
			map.put(keys[index], true);
			operator.broadcast("values", map);
			result = operator.getResult();
			if(result)	hasTrue = true;
			else		hasFalse = true;
			
			if(hasTrue && hasFalse){
				return;
			}			
		}
	}
	
}