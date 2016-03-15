package math.db.model.game.instance;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "gameInstance")
public class GameInstance {
	private String instanceId;
	private String gameId;
	private List<GameLevel> levels;

	public GameInstance() {}
	public GameInstance(String instanceId, String gameId, List<GameLevel> levels){
		this.instanceId = instanceId;
		this.gameId = gameId;
		this.levels  = levels;
	}
	
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public List<GameLevel> getLevels() {
		return levels;
	}

	public void setLevels(List<GameLevel> levels) {
		this.levels = levels;
	}

}
