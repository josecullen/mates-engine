package math.game.facade.problem;

import builders.ArithmeticVariableBuilder;
import builders.OperationBuilder;
import math.arithmetic.problem.EquationLevel;
import math.arithmetic.problem.EquationProblem;
import math.arithmetic.problem.SimpleModuleProblem;
import math.arithmetic.problem.SimpleProblem;
import math.arithmetic.problem.SystemEquationProblem;
import math.core.problem.Problem;
import math.db.model.game.config.ProblemConfig;
import math.db.model.game.config.ProblemType;
import math.db.model.game.config.VariableConfig;
import math.logic.problem.LogicProblem;

public class ProblemCreator{

	public static Problem createProblem(ProblemConfig problemConfig, ProblemType problemType){
		Problem problem = null;
				
		
		switch (problemType) {
		case SIMPLE:
			problem = createSimpleProblem(problemConfig);
			break;
		case EQUATION:
			problem = createEquationProblem(problemConfig);
			break;
		case LOGIC:
			problem = createLogicProblem(problemConfig);
			break;
		case MODULE:
			problem = createModuleProblem(problemConfig);
			break;		
		case SYSTEM_EQUATION:
			problem = createSystemEquationProblem(problemConfig);
			break;
			
		default:
			break;
		}
		
		return problem;
	}
	


	private static Problem createSimpleProblem(ProblemConfig problemConfig){
		VariableConfig variableConfig = problemConfig.getVariableConfigs().get(0);

		ArithmeticVariableBuilder rvi =
				new ArithmeticVariableBuilder()
					.divisionFactor(variableConfig.getDivisionFactor())
					.max(variableConfig.getMax())
					.min(variableConfig.getMin())
					.probablySign(variableConfig.getSign());

		OperationBuilder rob =
				new OperationBuilder()
					.buildWithProbablySign(variableConfig.getSign())
					.buildWithThisOperations(problemConfig.getOperations());

		SimpleProblem problem = new SimpleProblem(problemConfig.getForm(), rvi, rob);

		
		int i = 0;
		while(i < 1000){
			problem.renew();
			i++;
		}
		
		return problem;
	}
	
	private static Problem createEquationProblem(ProblemConfig problemConfig){

		VariableConfig variableA = problemConfig.getVariableConfigs().get(0);
		VariableConfig variableX = problemConfig.getVariableConfigs().get(1);

		ArithmeticVariableBuilder avbA = 
				new ArithmeticVariableBuilder()
					.divisionFactor(variableA.getDivisionFactor())
					.max(variableA.getMax())
					.min(variableA.getMin())
					.probablySign(variableA.getSign());
		
		ArithmeticVariableBuilder avbX = 
				new ArithmeticVariableBuilder()
					.divisionFactor(variableX.getDivisionFactor())
					.max(variableX.getMax())
					.min(variableX.getMin())
					.probablySign(variableX.getSign());
		
		return new EquationProblem(EquationLevel.getInstance(problemConfig.getLevel()), avbA.build(), avbX.build(), avbX.build(), avbX.build());				
				
	}
	
	private static Problem createLogicProblem(ProblemConfig problemConfig) {
		return new LogicProblem(problemConfig.getForm(), problemConfig.getSign(), problemConfig.getOperations());
	}
	
	private static Problem createModuleProblem(ProblemConfig problemConfig) {
		VariableConfig a = problemConfig.getVariableConfigs().get(0);
		VariableConfig pow = problemConfig.getVariableConfigs().get(1);
		VariableConfig mod = problemConfig.getVariableConfigs().get(2);


		ArithmeticVariableBuilder avbA = 
				new ArithmeticVariableBuilder()
					.divisionFactor(a.getDivisionFactor())
					.max(a.getMax())
					.min(a.getMin())
					.probablySign(a.getSign());
		
		ArithmeticVariableBuilder avbPow = 
				new ArithmeticVariableBuilder()
					.divisionFactor(pow.getDivisionFactor())
					.max(pow.getMax())
					.min(pow.getMin())
					.probablySign(pow.getSign());
		
		ArithmeticVariableBuilder avbMod = 
				new ArithmeticVariableBuilder()
					.divisionFactor(mod.getDivisionFactor())
					.max(mod.getMax())
					.min(mod.getMin())
					.probablySign(mod.getSign());
		
		return new SimpleModuleProblem(avbA.build(), avbPow.build(), avbMod.build());				
	}


	private static Problem createSystemEquationProblem(ProblemConfig problemConfig) {
		VariableConfig variableConfigA = problemConfig.getVariableConfigs().get(0);
		VariableConfig variableConfigX = problemConfig.getVariableConfigs().get(1);

		ArithmeticVariableBuilder avbConst = 
				new ArithmeticVariableBuilder()
					.divisionFactor(variableConfigA.getDivisionFactor())
					.max(variableConfigA.getMax())
					.min(variableConfigA.getMin())
					.probablySign(variableConfigA.getSign());
		
		ArithmeticVariableBuilder avbX = 
				new ArithmeticVariableBuilder()
					.divisionFactor(variableConfigX.getDivisionFactor())
					.max(variableConfigX.getMax())
					.min(variableConfigX.getMin())
					.probablySign(variableConfigX.getSign());

		OperationBuilder operationBuilder =
				new OperationBuilder()
					.buildWithProbablySign(variableConfigA.getSign())
					.buildWithThisOperations(problemConfig.getOperations());		
		
		return new SystemEquationProblem(EquationLevel.getInstance(problemConfig.getLevel()), avbConst, avbX, operationBuilder);

	}

	
	
	
	
	
	
}
