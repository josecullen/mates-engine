package math.db.model.player;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="instanceGameData")
public class InstanceGameData {
	@Id
	private ObjectId id;
	private ObjectId gameId;
	private String instanceName;
	private List<PlayerScore> playerScores;

	public InstanceGameData() {}
	public InstanceGameData(String instanceName, String gameId){
		this.instanceName = instanceName;
		this.gameId = new ObjectId(gameId);
	}
	
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getGameId() {
		return gameId;
	}

	public void setGameId(ObjectId gameId) {
		this.gameId = gameId;
	}

	public List<PlayerScore> getPlayerScores() {
		return playerScores;
	}

	public void setPlayerScores(List<PlayerScore> playerScores) {
		this.playerScores = playerScores;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}


}
