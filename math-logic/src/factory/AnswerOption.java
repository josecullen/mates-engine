package factory;

public enum AnswerOption {
	TRUE("true"), 
			FALSE("false"), 
			TAUTOLOGY("tautology"), 
			CONTRADICTION("contradiction");
	
	private final String text;
	private AnswerOption(String text) {
		this.text = text;
	}
	
	public String toString(){
		return this.text;
	}
}
