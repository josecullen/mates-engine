package math.db.model.game.config;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class ExtraScore {
	private String name;
	private int extraTime;
	private int extraScore;
	private int thresholdTime;
	
	public ExtraScore() {}
	public ExtraScore(String name,int thresholdTime, int time, int extra){
		this.name = name;
		this.setThresholdTime(thresholdTime);
		this.setExtraTime(time);
		this.setExtraScore(extra);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public int getExtraTime() {
		return extraTime;
	}
	public void setExtraTime(int extraTime) {
		this.extraTime = extraTime;
	}
	public int getExtraScore() {
		return extraScore;
	}
	public void setExtraScore(int extraScore) {
		this.extraScore = extraScore;
	}
	public int getThresholdTime() {
		return thresholdTime;
	}
	public void setThresholdTime(int thresholdTime) {
		this.thresholdTime = thresholdTime;
	}

}
