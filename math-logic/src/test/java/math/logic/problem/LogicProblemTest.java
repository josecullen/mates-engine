package math.logic.problem;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class LogicProblemTest {

	
	Pattern p0= Pattern.compile("\\{(.*?)\\}",Pattern.DOTALL);
	Pattern p1= Pattern.compile("\\[(.*?)\\]",Pattern.DOTALL);
	Pattern p2 = Pattern.compile("\\((.*?)\\)",Pattern.DOTALL);
	Pattern p3 = Pattern.compile("(and)|(then)");
	
//	String expression = "{[( p and q ) and ( r and s )] then (q and t)}";
	String expression = "(( p and q ) and ( r and s ))";
	List<String> expressions = Arrays.asList(
			"( p and q )",
			"(( p and q ) and r)",
			"( p and ( r and s ))",
			"(( p and q ) and ( r and s ))",
			"((( p and q ) and ( r and s )) and ( p and q ))",
			"(( p and q ) and (( p and q ) and ( r and s )))"
			);
	@Test
	public void test() {
//		split0(expression);	
		
		expressions.forEach(expression ->{
			LogicProblem problem = new LogicProblem(expression, 0.1, "and|or");		
			System.out.println(problem.getProblemExpression());
			String [] answer = problem.getAnswer();
			System.out.println(answer[0]);
		});
		
	}
	
	private String split0(String expression){
		Matcher matcher = p0.matcher(expression);
		List<String> groups = null;
		while(matcher.find()){
			groups = split1(matcher.group());
			for(String group : groups){
				expression = expression.replaceFirst(group, "");
			}
			
			groups.addAll(split2(expression));
			for(String group : groups){
				System.out.println(group);
			}
		}
		
		
		return "";
	}
	
	private List<String> split1(String expression){
		Matcher matcher = p1.matcher(expression);
		List<String> groups = null;
		while(matcher.find()){
			groups = split2(matcher.group());
			for(String group : groups){
				expression = expression.replaceFirst(group, "");
			}
		}
		
		
		
		return groups;
	}
	
	private List<String> split2(String expression){
		Matcher matcher = p2.matcher(expression);
		List<String> groups = new ArrayList<>();
		int count = 0;
		
		while(matcher.find()){
			String group = matcher.group();
			groups.add(group);
			expression = expression.replaceFirst(group, "");
		}
		
		matcher = p3.matcher(expression);
		if(matcher.find()){
			groups.add( matcher.group());
		}
		
		return groups;
		
	}
	
	
	

}
