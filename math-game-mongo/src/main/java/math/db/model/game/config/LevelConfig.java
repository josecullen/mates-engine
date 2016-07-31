package math.db.model.game.config;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class LevelConfig {
	private ProblemConfig problemConfig;
	private ScoreConfig scoreConfig;

	public LevelConfig() {}

	public LevelConfig(ProblemConfig problemConfig, ScoreConfig scoreConfig) {
		this.problemConfig = problemConfig;
		this.scoreConfig = scoreConfig;
	}

	public void setProblemConfig(ProblemConfig problemConfig) {
		this.problemConfig = problemConfig;
	}

	public ProblemConfig getProblemConfig() {
		return problemConfig;
	}

	public ScoreConfig getScoreConfig() {
		return scoreConfig;
	}

	public void setScoreConfig(ScoreConfig scoreConfig) {
		this.scoreConfig = scoreConfig;
	}



}
