package handlers;

import java.util.ArrayList;
import java.util.List;

import builders.ArithmeticVariableBuilder;
import builders.OperationBuilder;
import math.arithmetic.problem.SimpleProblem;
import math.db.model.game.config.GameConfig;
import math.db.model.game.config.ProblemConfig;
import math.db.model.game.config.ProblemType;
import math.db.model.game.config.VariableConfig;
import math.db.model.game.instance.GameLevel;
import math.db.model.game.instance.GameProblem;

public class GameCreator {
	public static List<GameLevel> createLevels(GameConfig gameConfig){
		List<GameLevel> levels = new ArrayList<>();
		
		
		gameConfig.getLevelConfigs().forEach(levelConfig ->{
			List<GameProblem> instanceProblems = new ArrayList();
			ProblemConfig problemConfig = levelConfig.getProblemConfig(); 
			
			if(problemConfig.getProblemType() == ProblemType.SIMPLE){
				
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
				levels.add(new GameLevel(instanceProblems));
			}			
		});
		
		return levels;
	}
	
	
	
}
