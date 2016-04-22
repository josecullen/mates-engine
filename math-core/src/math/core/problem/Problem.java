package math.core.problem;

public interface Problem {	
	String getProblemExpression();
	String getSolvedExpression();
	String[] getAnswer();
	String[] getAnswerOptions(int options);
	
}
