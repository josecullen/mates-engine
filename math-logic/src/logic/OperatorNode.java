package logic;

import java.util.HashMap;

import factory.OperationFactory;

public class OperatorNode extends LogicNode {
	Operation operation; 
	
	LogicNode 
		logicLeft,
		logicRight;
	
	public void setLeft(LogicNode left){
		left.setParent(this);
		this.logicLeft = left;
	}
	
	public void setRight(LogicNode right){
		right.setParent(this);
		this.logicRight = right;
	}
	
	public void setOperands(LogicNode left, LogicNode right){
		setLeft(left);
		setRight(right);
	}
	
	public void setOperation(Operation operation){
		this.operation = operation;
	}
	
	
	public OperatorNode(Operation operation){
		this.operation = operation;
	}
	
	
	@Override
	public boolean getResult() {
		boolean result = operar(logicLeft.getResult(), logicRight.getResult()); 
		return sign ? !result : result;
	}

	@Override
	public void broadcast(String key, Object value) {
		switch (key) {
		case "values":
			logicLeft.broadcast(key, value);
			logicRight.broadcast(key, value);
			break;
		case "useValuesRatherNames":
			logicLeft.broadcast(key, value);
			logicRight.broadcast(key, value);
		default:
			break;
		}
	}

	@Override
	public String getExpression() {
		String expression = logicLeft.getExpression() +" "+ operation.getString() +" "+ logicRight.getExpression();

		return sign 
				? "-( "+expression +" )"
				: parent == null
					? expression
					: "( "+expression+" )";
	}
	
	
	
	private boolean operar(boolean left, boolean right){
		switch(operation){
		case AND:
			return left && right;			
		case THEN:
			if(left && !right){
				return false;
			}else{
				return true;
			}			
		case OR:
			return left || right;
		default:
			System.out.println("Caso no contemplado");
			return false;		
		}
	}
	
	@Override
	HashMap<String, Boolean> getVariables(HashMap<String, Boolean> map) {
		map = logicLeft.getVariables(map);
		map = logicRight.getVariables(map);
		return map;
	}
	/**
	 * Permite obtener todos los nombres de variables que se están utilizando, con sus repectivos valores. 
	 * @return
	 */
	public HashMap<String, Boolean> getVariables() {
		HashMap<String, Boolean> map = new HashMap<String, Boolean>(); 
		map = logicLeft.getVariables(map);
		map = logicRight.getVariables(map);
		return map;
	}

	@Override
	public void randomize(int zeroProb, String[] names, HashMap<String, Boolean> map) {
		setOperation(Operation.getRandom());
		setSign(OperationFactory.randomBoolean(zeroProb));
		logicLeft.randomize(zeroProb, names, map);
		logicRight.randomize(zeroProb, names, map);
	}

}
