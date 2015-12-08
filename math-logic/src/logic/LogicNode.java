package logic;

import java.util.HashMap;

public abstract class LogicNode {
	OperatorNode parent;
	
	boolean sign = false;
	
	public void setSign(boolean sign){
		this.sign = sign;
	}	
	
	abstract boolean getResult();
	public abstract void broadcast(String key, Object value);
	/**
	 * 
	 * @param zeroProb Posibilidad de que un valor tenga signo. Es inverso, a mayor número, menores posibilidades: 1 / zeroProb
	 */
	abstract void randomize(int zeroProb, String names[], HashMap<String, Boolean> map);
	/**
	 * @return Retorna la expresión escrita de la operación.
	 */
	public abstract String getExpression();
	abstract HashMap<String, Boolean> getVariables(HashMap<String, Boolean> map);
	
	void setParent(OperatorNode parent){
		this.parent = parent;
	}
}


