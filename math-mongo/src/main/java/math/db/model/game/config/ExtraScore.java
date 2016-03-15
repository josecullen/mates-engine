package math.db.model.game.config;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class ExtraScore {
	private String name;
	private int time;
	private int extra;
	
	public ExtraScore() {}
	public ExtraScore(String name, int time, int extra){
		this.name = name;
		this.time = time;
		this.extra = extra;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getExtra() {
		return extra;
	}

	public void setExtra(int extra) {
		this.extra = extra;
	}

}
