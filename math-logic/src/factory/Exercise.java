package factory;

import java.util.ArrayList;
import java.util.HashMap;

import logic.OperatorNode;

public class Exercise {
	OperatorNode operator;
	String expression;
	Answer answer;
	
	
	
	public Exercise(OperatorNode operator){
		this.operator = operator;
	}
	public Exercise(String expression){
		operator = (OperatorNode)OperationExpression.createFromExpression(expression);	
	}
	
	public Exercise getNext(){
		OperatorNode operatorAux = OperationFactory.getOperation(operator.getExpression(), 3);				
				
		HashMap<String, Boolean> map = operatorAux.getVariables(); 
		
		String keys[] = new String[map.keySet().size()];
		keys = map.keySet().toArray(keys);
		ArrayList<String> names = new ArrayList<>();
		for(String key : keys){
			names.add(key);
		}
		
		String selected = keys[(int)Math.random()*keys.length];
		
		names.remove(selected);	
		
		operatorAux.broadcast("useValuesRatherNames", names);
		
		answer = new Answer();
		answer.variableName = selected;
		
		map.put(selected, true);
		operatorAux.broadcast("values", map);
		boolean resInTrue = operatorAux.getResult();
		map.put(selected, false);
		operatorAux.broadcast("values", map);
		boolean resInFalse = operatorAux.getResult();
		
		
		if(resInTrue && resInFalse){
			answer.answer = AnswerOption.TAUTOLOGY;
		}else if(!resInTrue && !resInFalse){
			answer.answer = AnswerOption.CONTRADICTION;
		}else if(resInTrue){
			answer.answer = AnswerOption.TRUE;
		}else{
			answer.answer = AnswerOption.FALSE;
		}
		
		expression = operatorAux.getExpression();
		
		
		return this;
	}
	
	public String getExpression(){
		return expression;
	}
	
	public Answer getAnswer(){
		return answer;
	}
	
	public String toString(){
		return expression+"\n"+"answer: "+answer.toString();
	}
}
