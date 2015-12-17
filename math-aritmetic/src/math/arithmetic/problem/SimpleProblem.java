package math.arithmetic.problem;

import math.arithmetic.broadcast.ArithmeticBroadcastUtil;
import math.arithmetic.operand.ArithmeticVariable;
import math.arithmetic.operand.ArithmeticVariableUtil;
import math.arithmetic.operation.DivisionOperation;
import math.arithmetic.parser.ArithmeticParser;
import math.arithmetic.tree.ArithmeticOperandNode;
import math.arithmetic.tree.ArithmeticOperationNode;
import math.core.tree.broadcast.BroadcastAction;
import builders.ArithmeticVariableBuilder;
import builders.OperationBuilder;

public class SimpleProblem implements Problem{
	ArithmeticOperationNode operationNode;
	private ArithmeticVariableBuilder variableBuilder;
	private OperationBuilder operationBuilder;
	ArithmeticBroadcastUtil br;

	private String expressionForm;
	private BroadcastAction<String> problemExpression; 
	
	public SimpleProblem(String expressionForm) {
		this.expressionForm = expressionForm;	
		variableBuilder = new ArithmeticVariableBuilder();
		this.operationBuilder = new OperationBuilder();
		init();
			
	}
	
	public SimpleProblem(String expressionForm, ArithmeticVariableBuilder rvi, OperationBuilder rob){
		this.variableBuilder = rvi;
		this.expressionForm = expressionForm;
		this.operationBuilder = rob;
		init();
	}
	
	public SimpleProblem(String expressionForm, ArithmeticVariableBuilder rvi){
		this.variableBuilder = rvi;
		this.expressionForm = expressionForm;
		this.operationBuilder = new OperationBuilder();
		init();
	}	
	
	private void init(){
		operationNode = (ArithmeticOperationNode) ArithmeticParser.getInstance().parse(expressionForm);
		
		br = new ArithmeticBroadcastUtil(operationNode);

		problemExpression = new BroadcastAction<String>();
		problemExpression.setOperandAction(operandNode ->{			
			return ArithmeticVariableUtil.getValueString((ArithmeticVariable) operandNode.getVariable());
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
	
	public ArithmeticVariableBuilder getRandomVariableInstancer() {
		return variableBuilder;
	}

	public void setRandomVariableInstancer(ArithmeticVariableBuilder randomVariableInstancer) {
		this.variableBuilder = randomVariableInstancer;
	}

	public String getProblemExpression(){
		return problemExpression.runOn(operationNode) + " = ";
	}
	
	public String getSolvedExpression(){	
		double result = br.getResult();
		return problemExpression.runOn(operationNode) + " = "+ ArithmeticVariableUtil.getValueString(result);
	}
	
	public void renew(){
		br.getVariablesBroadcast().forEach(variable ->{
			variable.set(variableBuilder.build(variable.getVariable()));
		});
		br.setOperations(operationBuilder);
		
		BroadcastAction<String> divisionOperations = new BroadcastAction<String>();
		
		divisionOperations.setOperationAction(operationNode ->{
			if(operationNode.getOperation() instanceof DivisionOperation){
				if(operationNode.rightNode instanceof ArithmeticOperandNode){
					ArithmeticOperandNode rightOperand = (ArithmeticOperandNode)operationNode.rightNode;
					if(rightOperand.getVariable().getValue() == 0){
						rightOperand.getVariable().setValue(1.0);
					}
				}
			}
			return "";
			
		});
		divisionOperations.runOn(operationNode);
	}
	
	public String[] getAnswer(){
		String[] answer = new String[1];
		answer[0] = ArithmeticVariableUtil.getValueString(br.getResult());
		return answer;
	}
	
	public String[] getAnswerOptions(int options){
		double[] answers = new double[options];
		String[] stringAnswers = new String[options];
		answers[0] = br.getResult();
		
		for(int i = 1; i < answers.length; i++)
			answers[i] = ArithmeticVariableUtil.getNoRepeatedAnswer(answers[0], answers, variableBuilder.getDivisionFactor());		
		
		for(int i = 0; i < answers.length; i++)
			stringAnswers[i] = ArithmeticVariableUtil.getValueString(answers[i]);
		
		//Reubicar respuesta correcta
		int newPosition = (int)(Math.random()*options);
		String auxAnswer = stringAnswers[newPosition];
		stringAnswers[newPosition] = stringAnswers[0];
		stringAnswers[0] = auxAnswer;
		
		return stringAnswers;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
//		str.append("variables: ");
//
//		br.getVariablesBroadcast().forEach(variable ->{
//			str.append("\t"+variable.getValue());
//		});
		str.append(getSolvedExpression());
		
		str.append("\nAnswers : \n");
		
		for(String answer : getAnswer())
			str.append(answer);
		
		str.append("\nOptions : \n");
		for(String option : getAnswerOptions(5))
			str.append(option);
		
		str.append("\n");
		return str.toString();
	}
	
	
}
