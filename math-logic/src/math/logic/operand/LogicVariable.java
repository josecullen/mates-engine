package math.logic.operand;

import math.core.operand.Variable;

public class LogicVariable implements Variable<Boolean> {
	Boolean value;
	String variable; 
	boolean sign;
	
	public LogicVariable(String variable, boolean value, boolean sign) {
		this.variable = variable;
		this.value = value;
		this.sign = sign;
	}
	
	public LogicVariable(String variable) {
		this.variable = variable;
		this.value = 0.5 >= Math.random();
	}
	
	public LogicVariable(String variable, double probablySign) {
		this.variable = variable;
		this.value = 0.5 >= Math.random();		
		setSign(probablySign >= Math.random());
	}
	
	public LogicVariable(Variable<Boolean> variable){
		set(variable);
	}
	
	public LogicVariable(Variable<Boolean> variable, double probablySign){
		set(variable);
		setSign(probablySign >= Math.random());		
	}
	
	@Override
	public Boolean getValue() {
		return sign ? !value : value;
	}

	@Override
	public void setValue(Boolean value) {
		this.value = value;
	}

	@Override
	public String getVariable() {
		return variable;
	}

	@Override
	public boolean getSign() {
		return sign;
	}

	@Override
	public void setSign(boolean sign) {
		this.sign = sign;
	}

	@Override
	public void set(Variable<Boolean> variable) {
		setSign(variable.getSign());
		setValue(variable.getSign() ? !variable.getValue() : variable.getValue());			
		this.variable = variable.getVariable();
	}

}
