package math.logic.problem;

import math.core.operand.Variable;
import math.core.problem.Problem;
import math.core.tree.broadcast.BroadcastAction;
import math.logic.broadcast.LogicBroadcastUtil;
import math.logic.parser.LogicParser;
import math.logic.tree.LogicOperationNode;

public class LogicProblem implements Problem {

	private LogicOperationNode operationTree;
	private String expressionForm;
	private double probablySign;
	private String operations;
	private BroadcastAction<String> problemExpression; 
	private LogicBroadcastUtil br;
	
	public LogicProblem(String expressionForm, double probablySign, String operations) {
		this.expressionForm = expressionForm;	
		this.probablySign = probablySign;
		this.operations = operations;
		init();			
	}
	
	private void init() {
		operationTree = (LogicOperationNode)LogicParser.getInstance(probablySign, operations).parse(expressionForm);
		br = new LogicBroadcastUtil(operationTree);
//		br.randomizeOperations();
		problemExpression = br.createProblemExpressionAction();	
		
	}

	@Override
	public String getProblemExpression() {
		return problemExpression.runOn(operationTree);
	}

	@Override
	public String getSolvedExpression() {
		return "";
	}

	@Override
	public String[] getAnswer() {
		String[] answer = new String[1];
		
		boolean result1 = br.getResult(); 
		Variable<Boolean> variable = br.getVariableToShow();
		boolean variableResult1 = variable.getValue();
		variable.setValue(!variable.getValue());

		boolean result2 = br.getResult();
		
		if(result1 && result2){
			answer[0] = "Tautología";
		}else if(!result1 && !result2){
			answer[0] = "Contradicción";
		}else if(result1 && variableResult1){
			answer[0] = "Verdadero";
		}else{
			answer[0] = "Falso";
		}
		
		return answer;
	}

	@Override
	public String[] getAnswerOptions(int options) {
		return new String[]{"Tautología","Verdadero", "Falso","Contradicción"};
	}

}
