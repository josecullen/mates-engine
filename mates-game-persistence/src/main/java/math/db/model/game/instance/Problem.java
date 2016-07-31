package math.db.model.game.instance;

public interface Problem {	
	String getProblemExpression();
	String getSolvedExpression();
	String[] getAnswer();
	String[] getAnswerOptions(int options);
	
}
