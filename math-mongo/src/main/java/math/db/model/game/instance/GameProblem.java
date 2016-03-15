package math.db.model.game.instance;

import math.arithmetic.problem.Problem;

public class GameProblem {

	private String problemExpression;
	private String solvedExpression;
	private String[] answerOptions;
	private String[] correctAnswer;

	public GameProblem() {
	}

	public GameProblem(String problemExpression, String solvedExpression, String[] answerOptions,
			String[] correctAnswer) {
		this.problemExpression = problemExpression;
		this.solvedExpression = solvedExpression;
		this.answerOptions = answerOptions;
		this.correctAnswer = correctAnswer;
	}

	public GameProblem(Problem problem) {
		setProblemExpression(problem.getProblemExpression());
		setSolvedExpression(problem.getSolvedExpression());
		setAnswerOptions(problem.getAnswerOptions(5));
		setCorrectAnswer(problem.getAnswer());

	}

	public String getProblemExpression() {
		return problemExpression;
	}

	public void setProblemExpression(String problemExpression) {
		this.problemExpression = problemExpression;
	}

	public String getSolvedExpression() {
		return solvedExpression;
	}

	public void setSolvedExpression(String solvedExpression) {
		this.solvedExpression = solvedExpression;
	}

	public String[] getAnswerOptions() {
		return answerOptions;
	}

	public void setAnswerOptions(String[] answerOptions) {
		this.answerOptions = answerOptions;
	}

	public String[] getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String[] correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

}
