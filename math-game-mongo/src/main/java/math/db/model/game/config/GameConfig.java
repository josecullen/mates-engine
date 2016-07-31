package math.db.model.game.config;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "gameConfig")
public class GameConfig {
	@Id
	private String id;
	private String name;
	private List<Stage> stages;

	public GameConfig() {}
	public GameConfig(String name, List<Stage> stages){
		this.name = name;
		this.stages = stages;
	
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Stage> getStages() {
		return stages;
	}
	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}
	
	
}



