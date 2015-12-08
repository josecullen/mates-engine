package factory;

public class Answer {
	String variableName;
	AnswerOption answer;
	
	
	public String getVariableName(){
		return variableName; 
	}
	
	public String getAnswerString(){
		return answer.toString();
	}
	
	
	public String toString(){
		return "variable: "+variableName+ "  answer: "+answer.toString();
	}
	
	
}
