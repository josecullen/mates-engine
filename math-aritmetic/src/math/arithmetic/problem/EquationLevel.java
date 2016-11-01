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
	
	
	public static EquationLevel getInstance(int levelValue){
		switch (levelValue) {
			case 1:
				return LEVEL_1;
			case 2:
				return LEVEL_2;
			case 3: 
				return LEVEL_3;
		}
		return null;
	}
	
	
	public boolean equals(int levelValue){
		return this.levelValue == levelValue;
	}
	
	public boolean equals(String levelName){
		return this.name().equals(levelName);
	}
	
}
