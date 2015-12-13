package math.arithmetic.operand;

import java.text.DecimalFormat;

import math.core.operand.Variable;

public class ArithmeticVariable implements Variable<Double> {
	double value;
	String variable; 
	boolean sign;
	private int divisionFactor = 1;
	
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
			result = getValueString(variable.getValue());//""+new DecimalFormat("#.##").format(variable.getValue());
		
		
		if(variable.getSign())
			result = "-"+result;			
		
		return "{"+result+"}";
	}
	
	public static String getValueString(Double value){
		String result = "";
		
		if(value % 1 == 0)
			result = ""+value.intValue();
		else{
			result = ""+"\\frac{"+getDividend(value)+"}{"+getDivisor(value)+"}";
		}
		
		return "{"+result+"}";
	}
	
	public static int getDividend(double number){
		return (int) Math.round(number * getDivisor(number));
	}
	
	public static int getDivisor(double number){
		int divisor = 1; 
		while((number * divisor) % 1 != 0){
			divisor++;
			
			if(Math.abs(truncateDecimals(3, number * divisor) - Math.round(number * divisor)) < 0.003){
				break;
			}
			
		}
		return divisor;
	}

	public static double truncateDecimals(int decimals, double value){
		return Math.floor(value * Math.pow(10, decimals)) / Math.pow(10, decimals);
	}
	
	
	public static class RandomVariableInstancer{		
		private double 
			min = 0,
			max = 5,
			probablySign = 0;		
		private int divisionFactor = 1;
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
		
		public RandomVariableInstancer divisionFactor(int divisionFactor){
			this.divisionFactor = divisionFactor;
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
			long appliedFactor = Math.round(decimals / (100 / divisionFactor));
			double addDecimals = 0;
			
			if(appliedFactor != 0){
				addDecimals = ((double)appliedFactor) / ((double)divisionFactor);
			}
			
			value =  (value - (value - (int)value)) + addDecimals;
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
	
	public static void main(String[] args) {
		
				
		double num = 5.166;
		System.out.println("dividen "+getDividend(num));
		System.out.println("divisor "+getDivisor(num));	
		
		
		doThat(1);
		doThat(2);
		doThat(3);
		doThat(4);
		doThat(5);
		doThat(6);
		
	}
	
	public static void doThat(int divisionFactor){
		RandomVariableInstancer instancer = new RandomVariableInstancer().divisionFactor(divisionFactor).min(0).max(5).probablySign(0);
		
		for(int i = 0; i < 30; i++){
			ArithmeticVariable variable = instancer.getInstance();
			System.out.print(variable.value+" ");
		}
		System.out.println();

	}
}
