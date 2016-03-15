package math.db.model.game.config;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class VariableConfig {
	private String variableName;
	private int min, max, divisionFactor;
	private double sign;

	public VariableConfig() {}
	public VariableConfig(String variableName, int min, int max, int divisionFactor, double sign){
		this.variableName = variableName;
		this.min = min;
		this.max = max;
		this.divisionFactor = divisionFactor;
		this.sign = sign;
	}
	
	public void setSign(double sign) {
		this.sign = sign;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public void setDivisionFactor(int divisionFactor) {
		this.divisionFactor = divisionFactor;
	}

	public double getSign() {
		return sign;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public int getDivisionFactor() {
		return divisionFactor;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
}
