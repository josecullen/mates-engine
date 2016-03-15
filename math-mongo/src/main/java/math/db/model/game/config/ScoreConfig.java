package math.db.model.game.config;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class ScoreConfig {
	private int baseScore;
	private int preCount;
	private boolean withTime;
	private List<ExtraScore> extras;
	
	public ScoreConfig() {}
	public ScoreConfig(int baseScore, int preCount, boolean withTime, List<ExtraScore> extras){
		this.baseScore = baseScore;
		this.preCount = preCount;
		this.withTime = withTime;
		this.extras = extras;
	}
	
	public int getBaseScore() {
		return baseScore;
	}
	public void setBaseScore(int baseScore) {
		this.baseScore = baseScore;
	}
	public int getPreCount() {
		return preCount;
	}
	public void setPreCount(int preCount) {
		this.preCount = preCount;
	}
	public boolean isWithTime() {
		return withTime;
	}
	public void setWithTime(boolean withTime) {
		this.withTime = withTime;
	}
	public List<ExtraScore> getExtras() {
		return extras;
	}
	public void setExtras(List<ExtraScore> extras) {
		this.extras = extras;
	}
	
	
}


/*
constructor(
    public baseScore: number = 10,
    public extras: Array<number> = [
        new ExtraScore("Perfecto", 5, 5),
        new ExtraScore("Muy bien", 7, 3),
        new ExtraScore("Bien", 10, 1)
    ],
    public precount: number = 0,
    public withTime: boolean = true
    ) { }

*/