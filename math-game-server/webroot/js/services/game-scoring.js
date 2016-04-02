define(["angular", "js/services", 'js/services/game-timer', 'js/services/game-instance', 'js/services/game-tooltips', 'js/services/game-levels'], 
    function(angular, services){
	services.factory('gameScoring', ['$log', 'gameTimer', 'gameInstance', 'gameTooltips', 'gameLevels',
        function($log, gameTimer, gameInstance, gameTooltips, gameLevels){				
	    
        $log.info("gameScoring");

        var update = function(wasCorrect){
            
            var scoreConfig = gameInstance.instance.levels[gameLevels.order.level].scoreConfig;
            var extras = scoreConfig.extras;
            if(wasCorrect){
                var extraIndex = -1;
                for(var i = 0; i < extras.length; i++){
                    if(gameTimer.problemTime.value < extras[i].thresholdTime){
                        extraIndex = i;
                        break;
                    }
                }
                gameTooltips.setTooltip(true, scoreConfig, extraIndex);
            }else{
                gameInstance.status.lives.pop();
                gameTooltips.setTooltip(false);
            }

            
        }

        return {
            update : update
        }

	}]);
})
