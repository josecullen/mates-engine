package service.facade;

import factory.Answer;
import factory.Exercise;
import io.vertx.core.json.JsonObject;

public class ExerciseFacade {
	public static JsonObject getExercise(String expression){
		JsonObject jsonExercise = new JsonObject();
		
		Exercise exercise = new Exercise(expression);
		exercise.getNext();
		
		jsonExercise.put("expression", exercise.getExpression());
		jsonExercise.put("answer", getAnswerJson(exercise.getAnswer()));
		
		
		
		
		return jsonExercise;
	}
	
	
	public static JsonObject getAnswerJson(Answer answer){
		JsonObject answerJson = new JsonObject();
		
		answerJson.put("variable", answer.getVariableName());
		answerJson.put("answer", answer.getAnswerString());
		return answerJson;
	}
	
	
	
}
