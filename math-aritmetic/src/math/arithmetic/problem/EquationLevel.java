package math.arithmetic.problem;

public enum EquationLevel {
	LEVEL_1(1), 
	LEVEL_2(2), 
	LEVEL_3(3);
	
	int levelValue;
	
	private EquationLevel(int levelValue) {
		this.levelValue = levelValue;
	}
	
	public int getLevelValue() {
		return levelValue;		
	}
	
	
	public boolean equals(int levelValue){
		return this.levelValue == levelValue;
	}
	
	public boolean equals(String levelName){
		return this.name().equals(levelName);
	}
	
}
