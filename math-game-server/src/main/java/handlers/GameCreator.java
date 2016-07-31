package handlers;

import java.util.ArrayList;
import java.util.List;

import builders.ArithmeticVariableBuilder;
import builders.OperationBuilder;
import math.arithmetic.problem.EquationLevel;
import math.arithmetic.problem.EquationProblem;
import math.arithmetic.problem.SimpleModuleProblem;
import math.arithmetic.problem.SimpleProblem;
import math.arithmetic.problem.SystemEquationProblem;
import math.db.model.game.config.GameConfig;
import math.db.model.game.config.LevelConfig;
import math.db.model.game.config.ProblemConfig;
import math.db.model.game.config.VariableConfig;
import math.db.model.game.instance.GameLevel;
import math.db.model.game.instance.GameProblem;
import math.logic.problem.LogicProblem;

public class GameCreator {
	
	
	public static List<GameLevel> createLevels(GameConfig gameConfig){
		List<GameLevel> levels = new ArrayList<>();
		
		
		gameConfig.getStages().forEach(stage ->{
			
			switch(stage.getProblemType()){
			case EQUATION:
				stage.getLevelConfigs().forEach(levelConfig ->{
					levels.add(createEquationGameLevel(levelConfig));
				});
				break;
			case SIMPLE:
				stage.getLevelConfigs().forEach(levelConfig ->{
					levels.add(createSimpleGameLevel(levelConfig));
				});
				break;
			case LOGIC:
				stage.getLevelConfigs().forEach(levelConfig ->{
					levels.add(createLogicGameLevel(levelConfig));
				});
				break;
			case SYSTEM_EQUATION:
				stage.getLevelConfigs().forEach(levelConfig ->{
					levels.add(createSystemEquationGameLevel(levelConfig));
				});
				break;
			case MODULE:
				stage.getLevelConfigs().forEach(levelConfig ->{
					levels.add(createModuleGameLevel(levelConfig));
				});
				break;

			default:
				break;			
			}			
		});
				
		return levels;
	}

	
	private static GameLevel createModuleGameLevel(LevelConfig levelConfig) {
		List<GameProblem> instanceProblems = new ArrayList<>();
		ProblemConfig problemConfig = levelConfig.getProblemConfig();
		
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
		
		for(int i = 0; i < problemConfig.getRepetitions() ; i++){			
			instanceProblems.add(new GameProblem(new SimpleModuleProblem(avbA.build(), avbPow.build(), avbMod.build())));
		}
		
		return new GameLevel(instanceProblems, levelConfig.getScoreConfig());
	}


	private static GameLevel createSystemEquationGameLevel(LevelConfig levelConfig) {
		List<GameProblem> instanceProblems = new ArrayList<>();
		ProblemConfig problemConfig = levelConfig.getProblemConfig();
		
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
		
		SystemEquationProblem problem = new SystemEquationProblem(EquationLevel.getInstance(problemConfig.getLevel()), avbConst, avbX, operationBuilder);
		for(int i = 0; i < problemConfig.getRepetitions() ; i++){			
			problem.renew();
			instanceProblems.add(new GameProblem(problem));
		}
		
		return new GameLevel(instanceProblems, levelConfig.getScoreConfig());
	}


	private static GameLevel createLogicGameLevel(LevelConfig levelConfig) {
		List<GameProblem> instanceProblems = new ArrayList<>();
		ProblemConfig problemConfig = levelConfig.getProblemConfig();

		for(int i = 0; i < problemConfig.getRepetitions() ; i++){			
			instanceProblems.add(new GameProblem(
					new LogicProblem(problemConfig.getForm(), problemConfig.getSign(), problemConfig.getOperations())
			));
		}
		
		return new GameLevel(instanceProblems, levelConfig.getScoreConfig());
	}


	private static GameLevel createSimpleGameLevel(LevelConfig levelConfig){
		List<GameProblem> instanceProblems = new ArrayList<>();
		ProblemConfig problemConfig = levelConfig.getProblemConfig(); 
		
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
		
		for(int i = 0; i < problemConfig.getRepetitions() ; i++){
			problem.renew();	
			
			instanceProblems.add(new GameProblem(problem));
		}
		return new GameLevel(instanceProblems, levelConfig.getScoreConfig());
	}
	
	private static GameLevel createEquationGameLevel(LevelConfig levelConfig){
		List<GameProblem> instanceProblems = new ArrayList<>();
		ProblemConfig problemConfig = levelConfig.getProblemConfig();

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
		
		for(int i = 0; i < problemConfig.getRepetitions() ; i++){
			EquationProblem equationProblem = new EquationProblem(EquationLevel.getInstance(problemConfig.getLevel()), avbA.build(), avbX.build(), avbX.build(), avbX.build());				
			instanceProblems.add(new GameProblem(equationProblem));
		}		
		
		return new GameLevel(instanceProblems, levelConfig.getScoreConfig());		
	}
	
	
	
	
	
}
