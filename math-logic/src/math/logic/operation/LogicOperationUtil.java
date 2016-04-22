package math.logic.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import math.core.operation.Operation;

public class LogicOperationUtil {
	public static Map<String, Operation<Boolean>> getOperationMap(){
		return getOperationMap("and|or|then|eq");
	}
	
	private static Map<String, Operation<Boolean>> getOperationMap(String operationsPattern){
		HashMap<String, Operation<Boolean>> operations = new HashMap<String, Operation<Boolean>>();
		if("and".matches(operationsPattern))
			operations.put("and", new AndOperation());
		if("or".matches(operationsPattern))
			operations.put("or", new OrOperation());
		if("then".matches(operationsPattern))
			operations.put("then", new ThenOperation());
		if("eq".matches(operationsPattern))
			operations.put("eq", new EquivalentOperaton());
		return operations;
	}
	
	public static Operation<Boolean> getInstanceFromExpression(String expression){
		return getOperationMap().get(expression);
	}
	
	public static Operation<Boolean> getRandom(){
		Map<String, Operation<Boolean>> map = getOperationMap();

		return new ArrayList<>(map.values()).get((int)(map.size() * Math.random()));
	}
}
