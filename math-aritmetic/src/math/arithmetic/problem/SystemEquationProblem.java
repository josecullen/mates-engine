package math.arithmetic.problem;

import static math.arithmetic.operand.ArithmeticVariableUtil.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import builders.ArithmeticVariableBuilder;
import builders.OperationBuilder;
import math.arithmetic.operand.ArithmeticVariable;
import math.arithmetic.operand.ArithmeticVariableUtil;
import math.core.list.ListOperationEjecutor;
import math.core.operation.Operation;
import math.core.problem.Problem;

public class SystemEquationProblem implements Problem{
	
	private ArithmeticVariableBuilder avbX;
	private ArithmeticVariableBuilder avbConst;
	private EquationLevel level;
	
	private ArithmeticVariable a,b,c,d,x,y,z;
	private OperationBuilder operationBuilder;
	private List<Operation<Double>> operations = new ArrayList<Operation<Double>>(); 
	
	public SystemEquationProblem(
			EquationLevel level, 
			ArithmeticVariableBuilder avbConst, 
			ArithmeticVariableBuilder avbX,
			OperationBuilder operationBuilder) {
		this.level = level;
		this.avbConst = avbConst;
		this.avbX = avbX;
		this.operationBuilder = operationBuilder;
		
		renew();
	}
	
	public void renew(){
		a = avbConst.build();
		b = avbConst.build();
		c = avbConst.build();
		d = avbConst.build();
		x = avbX.build();
		y = avbX.build();
		z = avbX.build();
		
		operations.clear();
		for(int i = 0; i < 3; i++){
			operations.add(operationBuilder.build());
		}
	}
	
	private void renewConstants(){
		a = avbConst.build();
		b = avbConst.build();
		c = avbConst.build();
		d = avbConst.build();
	}
	
	
	
	
	@Override
	public String getProblemExpression() {
		String problemExpression = null;
		String problem1, problem2, problem3;
		ListOperationEjecutor listOperation = new ListOperationEjecutor();
		switch (level) {
		case LEVEL_1:
			problemExpression = getLevel1Expression(listOperation);
			break;
		case LEVEL_2:
			problem1 = getLevel2Expression(listOperation);
			renewConstants();
			problem2 = getLevel2Expression(listOperation);
			problemExpression = decorateSystemString(problem1, problem2);
			break;
		case LEVEL_3:
			problem1 = getLevel3Expression(listOperation);
			renewConstants();
			problem2 = getLevel3Expression(listOperation);
			renewConstants();
			problem3 = getLevel3Expression(listOperation);
			problemExpression = decorateSystemString(problem1, problem2, problem3);

			break;
		default:
			break;
		
		}
		return problemExpression;
	}
	
	
	private String decorateSystemString(String... problems ) {
		StringBuilder result = new StringBuilder("\\begin{cases} ");
		Arrays.asList(problems).forEach(problem -> result.append(problem).append(" \\\\"));
		result.append("\\end{cases}");
		
		return result.toString();
	}

	public String getLevel1Expression(ListOperationEjecutor listOperation){
		String problemExpression = null;

		listOperation
			.left(a.getValue()*x.getValue())
			.and(operations.get(0), b.getValue());
	
		problemExpression = 
			getValueString(a)+"x "+operations.get(0).getLaTex()+" "
				+getValueString(b)+" = "+getValueString(listOperation.resolve());
		
		return problemExpression;
	}
	
	public String getLevel2Expression(ListOperationEjecutor listOperation){
		String problemExpression = null;

		listOperation
			.left(a.getValue()*x.getValue())
			.and(operations.get(0), b.getValue()*y.getValue())
			.and(operations.get(1), c.getValue());
	
		problemExpression = 
			getValueString(a)+"x "+operations.get(0).getLaTex()+" "+
			getValueString(b)+"y "+operations.get(1).getLaTex()+" "+
			getValueString(c)+" = "+getValueString(listOperation.resolve());
		
		return problemExpression;
	}
	
	public String getLevel3Expression(ListOperationEjecutor listOperation){
		String problemExpression = null;

		listOperation
			.left(a.getValue()*x.getValue())
			.and(operations.get(0), b.getValue()*y.getValue())
			.and(operations.get(1), c.getValue()*z.getValue())
			.and(operations.get(2), d.getValue());
		
		problemExpression = 
			getValueString(a)+"x "+operations.get(0).getLaTex()+" "+
			getValueString(b)+"y "+operations.get(1).getLaTex()+" "+
			getValueString(c)+"z "+operations.get(2).getLaTex()+
			getValueString(d)+" = "+getValueString(listOperation.resolve());
		
		return problemExpression;
	}
	
	
	
	private Double getProblemResult(){
		double result = 0;
		switch (level) {
		case LEVEL_1:
			result = operations.get(0).operate(a.getValue()*x.getValue() , b.getValue());				
			break;
		case LEVEL_2:

			
				
			break;
		case LEVEL_3:
			break;
		default:
			break;
		
		}
		return result;
	}

	@Override
	public String getSolvedExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getAnswer() {
		
		switch (level) {
		case LEVEL_1:
			return new String[]{"x = "+getValueString(x.getValue())};			
		case LEVEL_2:				
			return new String[]{
					"x = "+getValueString(x.getValue())+ 
					"\\: y = "+getValueString(y.getValue())
			};
		case LEVEL_3:
			return new String[]{
					"x = "+getValueString(x.getValue())+ 
					"\\: y = "+getValueString(y.getValue())+ 
					"\\: z = "+getValueString(z.getValue())
			};
		default:
			break;
		
		}
		return null;
	}

	@Override
	public String[] getAnswerOptions(int options) {
		double[] answers = new double[options];

		String[] result = new String[3];
		
		List<Double> posibleAnswers = 
				getValuesWithDivisionFactor(20, x.getValue(), avbX.getDivisionFactor());
		
		List<String> posiblesAsString = posibleAnswers.stream().map(posibleAnswer -> getValueString(posibleAnswer)).collect(Collectors.toList());
		
		for(int i = 1; i < options; i++){
			answers[i] = posibleAnswers.remove((int)Math.random()*posibleAnswers.size());
			if(answers[i] == answers[0]){
				i--;
			}
		}
		
		result[0] = getAnswer()[0];

		switch (level) {
			case LEVEL_1:
				result[1] = "x = "+posiblesAsString.remove((int)Math.random()*posiblesAsString.size());
				result[2] = "x = "+posiblesAsString.remove((int)Math.random()*posiblesAsString.size());
				break;
			case LEVEL_2:	
				result[1] = "x = "+posiblesAsString.remove((int)Math.random()*posiblesAsString.size())+
							"\\: y = "+posiblesAsString.remove((int)Math.random()*posiblesAsString.size());
				result[2] = "x = "+posiblesAsString.remove((int)Math.random()*posiblesAsString.size())+
							"\\: y = "+posiblesAsString.remove((int)Math.random()*posiblesAsString.size());
				break;
			case LEVEL_3:
				result[1] = "x = "+posiblesAsString.remove((int)Math.random()*posiblesAsString.size())+
							"\\: y = "+posiblesAsString.remove((int)Math.random()*posiblesAsString.size())+
							"\\: z = "+posiblesAsString.remove((int)Math.random()*posiblesAsString.size());				
				result[2] = "x = "+posiblesAsString.remove((int)Math.random()*posiblesAsString.size())+
							"\\: y = "+posiblesAsString.remove((int)Math.random()*posiblesAsString.size())+
							"\\: z = "+posiblesAsString.remove((int)Math.random()*posiblesAsString.size());
			default:
				break;
			
			}
		
		int newPosition = (int)(Math.random()*3);
		String auxAnswer = result[newPosition];
		result[newPosition] = result[0];
		result[0] = auxAnswer;

		
		
		return result;
	}
	
	
}
