package builders;

import math.arithmetic.operand.ArithmeticVariable;
import math.arithmetic.operand.ArithmeticVariableUtil;

public class ArithmeticVariableBuilder {
	
		private double 
			min = 0,
			max = 5,
			probablySign = 0;
		
		private int 
			divisionFactor = 1;
		
		private String 
			variableNames = "a";
		
		public ArithmeticVariableBuilder() {}
		
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

		
		public ArithmeticVariableBuilder min(double min){
			this.min = min;
			return this;
		}
		
		public ArithmeticVariableBuilder max(double max){
			this.max = max;
			return this;
		}
		
		public ArithmeticVariableBuilder divisionFactor(int divisionFactor){
			this.divisionFactor = divisionFactor;
			return this;
		}
		
		public ArithmeticVariableBuilder probablySign(double probablySign){
			this.probablySign = probablySign;
			return this;
		}
		
		public ArithmeticVariableBuilder variableNames(String variableNamesPattern){
			this.variableNames = variableNamesPattern;
			return this;
		}	
		
		private String getVariableName(){
			String[] names = variableNames.split("");
			return names[(int)(Math.random()* names.length)];
		}
		
		public ArithmeticVariable build(){
			ArithmeticVariable instance = new ArithmeticVariable();
			instance.setSign(probablySign >= Math.random());
			instance.setValue(ArithmeticVariableUtil.getValueWithDivisionFactor(min, max, getDivisionFactor())); 
			instance.setVariable(getVariableName());
			return instance;
		}
		
		
		
		public ArithmeticVariable build(String variableName){
			ArithmeticVariable instance = build();
			instance.setVariable(variableName);
			return instance;
		}
		
		


}
