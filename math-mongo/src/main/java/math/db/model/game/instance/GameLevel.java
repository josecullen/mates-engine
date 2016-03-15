package math.db.model.game.instance;

import java.util.List;

public class GameLevel {
	private List<GameProblem> gameProblems;
	public List<GameProblem> getGameProblems() {
		return gameProblems;
	}
	public void setGameProblems(List<GameProblem> gameProblems) {
		this.gameProblems = gameProblems;
	}
	public GameLevel() {}
	public GameLevel(List<GameProblem> gameProblems){
		this.gameProblems = gameProblems;
	}
	
}
