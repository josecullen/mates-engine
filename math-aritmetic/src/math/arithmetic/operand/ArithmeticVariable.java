package math.arithmetic.operand;

import math.core.operand.Variable;

public class ArithmeticVariable implements Variable<Double> {
	double value;
	String variable; 
	boolean sign;
	
	@Override
	public Double getValue() {
		return value;
	}

	@Override
	public void setValue(Double value) {
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
	public void set(Variable<Double> variable) {
		this.variable = variable.getVariable();
		this.value = variable.getValue();
		this.sign = variable.getSign();
	}
	
	public ArithmeticVariable() {}
	public ArithmeticVariable(String variable){
		this.variable = variable;
	}
	public ArithmeticVariable(String variable, double value, boolean sign){
		this.value = value;
		this.variable = variable;
		this.sign = sign;
	}
	
	public static String getValueString(ArithmeticVariable variable){
		String result = "";
		
		if(variable.getValue() % 1 == 0)
			result = ""+variable.getValue().intValue();
		else
			result = ""+variable.getValue();
		
		
		if(variable.getSign())
			result = "-"+result;			
		
		return result;
	}
	
	public static String getValueString(Double value){
		String result = "";
		
		if(value % 1 == 0)
			result = ""+value.intValue();
		else
			result = ""+value;
		
		return result;
	}

	
	public static class RandomVariableInstancer{		
		private double 
			min = 0,
			max = 5,
			probablySign = 0;		
		private int divisionFactor = 100;
		private String variableNames = "a";
		
		public int getDivisionFactor() {
			return divisionFactor;
		}

		public void setDivisionFactor(int divisionFactor) {
			this.divisionFactor = divisionFactor;
		}

		public double getMin() {
			return min;
		}

		public void setMin(double min) {
			this.min = min;
		}

		public double getMax() {
			return max;
		}

		public void setMax(double max) {
			this.max = max;
		}

		public double getProbablySign() {
			return probablySign;
		}

		public void setProbablySign(double probablySign) {
			this.probablySign = probablySign;
		}

		public String getVariableNames() {
			return variableNames;
		}

		public void setVariableNames(String variableNames) {
			this.variableNames = variableNames;
		}

		public RandomVariableInstancer() {}
		
		public RandomVariableInstancer min(double min){
			this.min = min;
			return this;
		};
		
		public RandomVariableInstancer max(double max){
			this.max = max;
			return this;
		}
		
		public RandomVariableInstancer probablySign(double probablySign){
			this.probablySign = probablySign;
			return this;
		}
		
		public RandomVariableInstancer variableNames(String variableNamesPattern){
			this.variableNames = variableNamesPattern;
			return this;
		}		
		
		public ArithmeticVariable getInstance(){
			ArithmeticVariable instance = new ArithmeticVariable();
			instance.sign = probablySign >= Math.random();
			instance.value = getValueWithDivisionFactor(min, max); //(Math.random() * (max - min)) + min;
			instance.variable = getVariableName();
			return instance;
		}
		
		public double getValueWithDivisionFactor(double min, double max){
			double value = (Math.random() * (max - min)) + min;
			double decimals = (int)((value - (int)value)*100);
			long applyFactor = Math.round(decimals / divisionFactor);
			value =  (value - (value - (int)value)) + applyFactor;
			return value;
		}
		
		public ArithmeticVariable getInstance(String variableName){
			ArithmeticVariable instance = getInstance();
			instance.variable = variableName;
			return instance;
		}
		
		private String getVariableName(){
			String[] names = variableNames.split("");
			return names[(int)(Math.random()* names.length)];
		}
		
	}

	@Override
	public void setSign(boolean sign) {
		this.sign = sign;
	}
	
	
}
