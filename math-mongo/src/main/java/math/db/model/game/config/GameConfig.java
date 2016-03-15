package math.db.model.game.config;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "gameConfig")
public class GameConfig {
	@Id
	private String id;
	private String name;
	private List<LevelConfig> levelConfigs;

	public GameConfig() {}
	public GameConfig(String name, List<LevelConfig> levelConfigs){
		this.name = name;
		this.levelConfigs = levelConfigs;
	}
	
	public List<LevelConfig> getLevelConfigs() {
		return levelConfigs;
	}

	public String getName() {
		return name;
	}

	public void setLevelConfigs(List<LevelConfig> levelConfigs) {
		this.levelConfigs = levelConfigs;
	}

	public void setName(String name) {
		this.name = name;
	}
}
