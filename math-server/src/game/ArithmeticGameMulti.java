package game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import math.arithmetic.operand.ArithmeticVariable.RandomVariableInstancer;
import math.arithmetic.operation.OperationUtil.RandomOperationBuilder;
import math.arithmetic.problem.Problem;
import service.facade.ProblemFacade;

public class ArithmeticGameMulti {
	String name;
	boolean enabled = false;
	State state = State.CREATED;
	
	public enum State{
		CREATED, ENABLED, AWAITED, STARTED;
	}
	
	public void setStatus(String status){
		state = State.valueOf(status);
	}
	
	public State getStatus(){
		return state;
	}
	
	
	public List<Problem> problems = new ArrayList<>();		
	public List<Player> players = new ArrayList<>();
	
	
	public boolean isEnabled(){
		return enabled;
	}
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	public String getName(){
		return name;
	}
	
	
	public ArithmeticGameMulti() {
		problems = new ArrayList<>();		
	}
	
	public ArithmeticGameMulti(JsonObject game){
		super();
		name = game.getString("name");
		JsonArray levels = game.getJsonArray("levels");
		
		for(int i = 0; i < levels.size(); i++){
			addLevel(levels.getJsonObject(i));			
		}
		
	}
	
	public void addLevel(JsonObject level){
		String expression = level.getString("form");
		String operations = level.getString("operations");
		int max = level.getInteger("max");
		int min = level.getInteger("min");
		double probablySign = level.getDouble("probablySign");
		int divisionFactor = level.getInteger("divisionFactor");		
		
		for(int i = 0; i < level.getInteger("repetitions"); i++){
			RandomVariableInstancer randomVariableInstancer = new RandomVariableInstancer().max(max).min(min).probablySign(probablySign).divisionFactor(divisionFactor);
			RandomOperationBuilder randomOperationBuilder = new RandomOperationBuilder().buildWithProbablySign(probablySign).buildWithThisOperations(operations);
			Problem problem = new Problem(expression, randomVariableInstancer, randomOperationBuilder);
			problem.renew();
			problems.add(problem);
		}		

	}
	
	public void addPlayer(String name){
		Player player = new Player();
		player.name = name;
		players.add(player);
	}
	
	public Player getPlayerByName(String name){
		return players.stream().filter(player -> player.name.equals(name)).findFirst().get();	
	}
	
	public class Player{
		public String name;
		int points = 0;
		int corrects = 0;
		int problem = 0;
		boolean ready = false;
		
		public int getProblemCount(){
			return problem;
		}
		
		public void setProblemCount(int count){
			problem = count;
		}
		
		public void setReady(boolean ready){
			this.ready = ready;
		}
		
		public boolean getReady(){
			return ready;
		}
		
		public void updateScoring(boolean correct, int points){
			if(correct){
				corrects++;
			}
			this.points += points;
		}
		
		public JsonObject toJson(){
			JsonObject jsonPlayer = new JsonObject();
			
			jsonPlayer
				.put("name", name)
				.put("points", points)
				.put("ready", ready)
				.put("problem", problem);
			return jsonPlayer;
		}
		
	}
	
	
	public JsonObject toJson(){
		JsonObject jsonGame = new JsonObject();
		
		JsonArray players = new JsonArray();
		
		this.players.forEach(
			player -> players.add(player.toJson()
		));
		
		JsonArray problems = new JsonArray();
		
		this.problems.forEach(
			problem -> problems.add(ProblemFacade.parseProblemToJson(problem)
		));
		
		jsonGame
			.put("name", name)
			.put("state", state.name())
			.put("players", players)
			.put("problems", problems);		
		
		return jsonGame;
	}
	
}
