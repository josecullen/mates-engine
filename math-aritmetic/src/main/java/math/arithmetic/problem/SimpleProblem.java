package math.arithmetic.problem;

import math.arithmetic.broadcast.ArithmeticBroadcastUtil;
import math.arithmetic.operand.ArithmeticVariable;
import math.arithmetic.operand.ArithmeticVariableUtil;
import math.arithmetic.operation.DivisionOperation;
import math.arithmetic.parser.ArithmeticParser;
import math.arithmetic.tree.ArithmeticOperandNode;
import math.arithmetic.tree.ArithmeticOperationNode;
import math.core.problem.Problem;
import math.core.tree.broadcast.BroadcastAction;

import java.util.Collections;
import java.util.List;

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
		renew();
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
		
		if(br.getResult() == Double.POSITIVE_INFINITY || br.getResult() == Double.NEGATIVE_INFINITY){
			this.renew();
		}
	}

	public String[] getAnswer(){
		String[] answer = new String[1];
		answer[0] = ArithmeticVariableUtil.getValueString(br.getResult());
		return answer;
	}
	
	public String[] getAnswerOptions(int options){
		Double[] answers = new Double[options];
		String[] stringAnswers = new String[options];
		double correct = br.getResult();
		System.out.print("|--- "+ br.getResult()+"\t"+variableBuilder.getDivisionFactor()+" ---|");
		
		List<Double> posibleAnswers = 
				ArithmeticVariableUtil.getValuesWithDivisionFactor(20, br.getResult(), variableBuilder.getDivisionFactor());
		
		System.out.println("\t\tend "+br.getResult()+"\t"+posibleAnswers.size());
		try{
			Collections.shuffle(posibleAnswers);
			posibleAnswers = posibleAnswers.subList(0, options);
			if(!posibleAnswers.stream().anyMatch(ans -> ans == correct)){
				posibleAnswers.set(0, correct);
				Collections.shuffle(posibleAnswers);
			}
			posibleAnswers.toArray(answers);

			
		}catch(IndexOutOfBoundsException ex){
			StringBuilder str = new StringBuilder();

			str.append(getSolvedExpression());
			
			str.append("\nAnswers : \n");
			
			for(String answer : getAnswer())
				str.append(answer);
			
			str.append("\n");
			System.out.println(str.toString());
			
		}
		
		
		
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
