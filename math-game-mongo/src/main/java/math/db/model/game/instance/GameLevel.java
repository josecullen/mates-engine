package math.db.model.game.instance;

import java.util.List;

import math.db.model.game.config.ScoreConfig;



public class GameLevel {
	private List<GameProblem> gameProblems;
	private ScoreConfig scoreConfig;
	
	public GameLevel() {}
	public GameLevel(List<GameProblem> gameProblems, ScoreConfig scoreConfig){
		this.gameProblems = gameProblems;
		this.setScoreConfig(scoreConfig);
	}
	
	public List<GameProblem> getGameProblems() {
		return gameProblems;
	}
	public void setGameProblems(List<GameProblem> gameProblems) {
		this.gameProblems = gameProblems;
	}
	public ScoreConfig getScoreConfig() {
		return scoreConfig;
	}
	public void setScoreConfig(ScoreConfig scoreConfig) {
		this.scoreConfig = scoreConfig;
	}
	
	
	
}
