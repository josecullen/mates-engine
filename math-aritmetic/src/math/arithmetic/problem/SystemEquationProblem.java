package math.arithmetic.problem;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public String getProblemExpression() {
		String problemExpression = null;
		ListOperationEjecutor listOperation = new ListOperationEjecutor();
		switch (level) {
		case LEVEL_1:
			listOperation
				.left(a.getValue()*x.getValue())
				.and(operations.get(0), b.getValue());
			
			problemExpression = 
				ArithmeticVariableUtil.getValueString(a)+"x "+operations.get(0).getLaTex()+" "
					+ArithmeticVariableUtil.getValueString(b)+" = "+ArithmeticVariableUtil.getValueString(listOperation.resolve());
			break;
		case LEVEL_2:
			listOperation
				.left(a.getValue()*x.getValue())
				.and(operations.get(0), b.getValue()*y.getValue())
				.and(operations.get(1), c.getValue());
			
			problemExpression = 
				ArithmeticVariableUtil.getValueString(a)+"x "+operations.get(0).getLaTex()+" "+
				ArithmeticVariableUtil.getValueString(b)+"y "+operations.get(1).getLaTex()+" "
					+ArithmeticVariableUtil.getValueString(c)+" = "+ArithmeticVariableUtil.getValueString(listOperation.resolve());
			break;
		case LEVEL_3:
			listOperation
				.left(a.getValue()*x.getValue())
				.and(operations.get(0), b.getValue()*y.getValue())
				.and(operations.get(1), c.getValue()*z.getValue())
				.and(operations.get(2), d.getValue());
			
			problemExpression = 
				ArithmeticVariableUtil.getValueString(a)+"x "+operations.get(0).getLaTex()+" "+
				ArithmeticVariableUtil.getValueString(b)+"y "+operations.get(1).getLaTex()+" "+
				ArithmeticVariableUtil.getValueString(c)+"z "+operations.get(2).getLaTex()
					+ArithmeticVariableUtil.getValueString(d)+" = "+ArithmeticVariableUtil.getValueString(listOperation.resolve());
			break;
		default:
			break;
		
		}
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
			return new String[]{"x = "+x.getValue()};			
		case LEVEL_2:				
			return new String[]{"x = "+x.getValue(), "y = "+y.getValue()};
		case LEVEL_3:
			return new String[]{"x = "+x.getValue(), "y = "+y.getValue(), "z = "+z.getValue()};
		default:
			break;
		
		}
		return null;
	}

	@Override
	public String[] getAnswerOptions(int options) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
