package com.torbitoinc.mates.engine.endpoint.model;

public class Problem {
	private String problemExpression;
	private String answer[];
	private String answerOptions[];
	
	public String getProblemExpression() {
		return problemExpression;
	}
	public void setProblemExpression(String problemExpression) {
		this.problemExpression = problemExpression;
	}
	public String[] getAnswer() {
		return answer;
	}
	public void setAnswer(String[] answer) {
		this.answer = answer;
	}
	public String[] getAnswerOptions() {
		return answerOptions;
	}
	public void setAnswerOptions(String[] answerOptions) {
		this.answerOptions = answerOptions;
	}
	
	public static Problem create(math.core.problem.Problem problemGen){
		Problem problem = new Problem();
		problem.setAnswer(problemGen.getAnswer());
		problem.setAnswerOptions(problemGen.getAnswerOptions(5));
		problem.setProblemExpression(problemGen.getProblemExpression());
		
		return problem;
	}
	
	
}
