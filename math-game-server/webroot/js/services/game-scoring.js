define(["angular", "js/services", 'js/services/game-timer', 'js/services/game-instance', 'js/services/game-tooltips', 'js/services/game-levels'], 
    function(angular, services){
	services.factory('gameScoring', ['$log', 'gameTimer', 'gameInstance', 'gameTooltips', 'gameLevels',
        function($log, gameTimer, gameInstance, gameTooltips, gameLevels){				
	    
        $log.info("gameScoring");

        var update = function(wasCorrect){
            var responseLevel = 0;
            var plusScore = 0;
            var plusTime = 0;

            if(wasCorrect){
                var scoreConfig = gameInstance.instance.levels[gameLevels.order.level].scoreConfig;

                plusScore = scoreConfig.baseScore;
                gameInstance.status.corrects++;

                var extras = scoreConfig.extras;

                if(gameTimer.problemTime.value < extras[0].thresholdTime){
                    gameInstance.status.responses.great++;
                    plusTime += extras[0].extraTime;
                    plusScore += extras[0].extraScore;
                    responseLevel = 4;
                }else if(gameTimer.problemTime.value < extras[1].thresholdTime){
                    gameInstance.status.responses.good++;
                    plusTime += extras[1].extraTime;
                    plusScore += extras[1].extraScore;
                    responseLevel = 3;
                }else if(gameTimer.problemTime.value < extras[2].thresholdTime){
                    gameInstance.status.responses.ok++;
                    plusTime += extras[2].extraTime;
                    plusScore += extras[2].extraScore;
                    responseLevel = 2;
                }else{
                    gameInstance.status.responses.pass++;
                    responseLevel = 1;
                }
                
                gameInstance.status.score += plusScore;
                gameTimer.gameTime.value += plusTime;

            }else{        
                gameInstance.status.responses.nook++;
                gameInstance.status.incorrects++;
                gameInstance.status.lives.pop();
                if(gameInstance.status.lives.length == 0){
                    gameInstance.instance.status = "GAME_OVER";
                }
            }

            gameTooltips.setScoreTooltip(plusScore);
            gameTooltips.setTimeTooltip(plusTime);

            return responseLevel;            
        }

        return {
            update : update
        }

	}]);
})
