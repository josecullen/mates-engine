package builders;

import java.util.Map;

import math.arithmetic.operation.OperationUtil;
import math.core.operation.Operation;

public class OperationBuilder {
	private double probablySign = 0; 
	private String operationsPattern = "+-/*\\^";

	public OperationBuilder() {}

	public OperationBuilder buildWithProbablySign(double probablySign) {
		this.probablySign = probablySign;
		return this;
	}

	public OperationBuilder buildWithThisOperations(String operationsPattern) {
		this.operationsPattern = operationsPattern;
		return this;
	}

	public Operation<Double> build() {
		Map<String, Operation<Double>> operations = OperationUtil.getOperationMap(operationsPattern);

		Operation<Double> operation = getRandomOperation(operations);
		operation.setSign(probablySign >= Math.random());

		return operation;
	}

	private Operation<Double> getRandomOperation(Map<String, Operation<Double>> operations) {
		String[] keys = new String[operations.keySet().size()];
		operations.keySet().toArray(keys);
		return operations.get(keys[(int) (Math.random() * keys.length)]);
	}

}
