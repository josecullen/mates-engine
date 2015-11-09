package exercise;

import static org.junit.Assert.*;

import org.junit.Test;

import factory.Exercise;

public class ExerciseTest {

	@Test
	public void test() {
		Exercise exercise = new Exercise("p and q");	
		for(int i = 0; i < 10; i++){
			exercise = exercise.getNext();
			System.out.println(exercise);	
		}
		
		exercise = new Exercise("(p and q) then r");	
		for(int i = 0; i < 10; i++){
			exercise = exercise.getNext();
			System.out.println(exercise);	
		}
				
	}

}
