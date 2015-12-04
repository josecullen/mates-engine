package math.arithmetic.problem;

import math.arithmetic.broadcast.ArithmeticBroadcastUtil;
import math.arithmetic.operand.ArithmeticVariable;
import math.arithmetic.operand.ArithmeticVariable.RandomVariableInstancer;
import math.arithmetic.operation.OperationUtil.RandomOperationBuilder;
import math.arithmetic.parser.ArithmeticParser;
import math.arithmetic.tree.ArithmeticOperationNode;
import math.core.tree.broadcast.BroadcastAction;

public class Problem {
	ArithmeticOperationNode operationNode;
	private RandomVariableInstancer randomVariableInstancer;
	private RandomOperationBuilder randomOperationBuilder;
	ArithmeticBroadcastUtil br;

	private String expressionForm;
	private BroadcastAction<String> problemExpression; 
	public Problem(String expressionForm) {
		this.expressionForm = expressionForm;	
		randomVariableInstancer = new RandomVariableInstancer();
		this.randomOperationBuilder = new RandomOperationBuilder();
		init();
			
	}
	
	public Problem(String expressionForm, RandomVariableInstancer rvi, RandomOperationBuilder rob){
		this.randomVariableInstancer = rvi;
		this.expressionForm = expressionForm;
		this.randomOperationBuilder = rob;
		init();
	}
	
	public Problem(String expressionForm, RandomVariableInstancer rvi){
		this.randomVariableInstancer = rvi;
		this.expressionForm = expressionForm;
		this.randomOperationBuilder = new RandomOperationBuilder();
		init();
	}	
	
	private void init(){
		operationNode = (ArithmeticOperationNode) ArithmeticParser.getInstance().parse(expressionForm);
		
		br = new ArithmeticBroadcastUtil(operationNode);

		problemExpression = new BroadcastAction<String>();
		problemExpression.setOperandAction(operandNode ->{			
			return ArithmeticVariable.getValueString((ArithmeticVariable) operandNode.getVariable());
		});
		problemExpression.setOperationAction(operationNode->{
			String expression = ""+problemExpression.runOn(
					operationNode.getLeftNode()) +" "+ 
					operationNode.getOperation().getLaTex()+" "+ 
					problemExpression.runOn(operationNode.getRightNode());
			
			return operationNode.getOperation().getSign() 
					? "-( "+expression +" )"
					: "( "+expression+" )";
		});	
	}	
	
	public RandomVariableInstancer getRandomVariableInstancer() {
		return randomVariableInstancer;
	}

	public void setRandomVariableInstancer(RandomVariableInstancer randomVariableInstancer) {
		this.randomVariableInstancer = randomVariableInstancer;
	}

	public String getProblemExpression(){
		return problemExpression.runOn(operationNode) + " = ?";
	}
	
	public String getSolvedExpression(){	
		double result = br.getResult();
		return problemExpression.runOn(operationNode) + " = "+ ArithmeticVariable.getValueString(result);
	}
	
	public void renew(){
		br.getVariablesBroadcast().forEach(variable ->{
			variable.set(randomVariableInstancer.getInstance(variable.getVariable()));
		});
		br.setOperations(randomOperationBuilder);
	}
	
	public String getAnswer(){
		return ArithmeticVariable.getValueString(br.getResult());
	}
	
	public String[] getAnswerOptions(){
		double[] answers = new double[5];
		String[] stringAnswers = new String[5];
		answers[0] = br.getResult();
		for(int i = 1; i < answers.length; i++){
			answers[i] = getNoRepeatedAnswer(answers);
		}
		
		for(int i = 0; i < answers.length; i++)
			stringAnswers[i] = ArithmeticVariable.getValueString(answers[i]);
		
		return stringAnswers;
	}
	
	private double getNoRepeatedAnswer(double[] answers){
		double answer = br.getResult() + (randomVariableInstancer.getValueWithDivisionFactor(0,20) - 10);
		for(Double ans : answers){
			if(ans == answer){
				answer = getNoRepeatedAnswer(answers);
			}
		}
		return answer;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("variables: ");

		br.getVariablesBroadcast().forEach(variable ->{
			str.append("\t"+variable.getValue());
		});
	
		
		return str.toString();
	}
	
	
}