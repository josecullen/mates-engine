package math.db.model.game.config;

import java.util.List;

public class Stage{
	private List<LevelConfig> levelConfigs;
	
	public void setLevelConfigs(List<LevelConfig> levelConfigs) {
		this.levelConfigs = levelConfigs;
	}
	
	public List<LevelConfig> getLevelConfigs() {
		return levelConfigs;
	}
	
	public ProblemType getProblemType(){
		ProblemType result = null;
		if(!getLevelConfigs().isEmpty()){
			result = getLevelConfigs().get(0).getProblemConfig().getProblemType();
		}
		return result;
	}
	
}