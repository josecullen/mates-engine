define(["angular", "js/services", 'js/services/game-timer', 'js/services/game-instance'], 
    function(angular, services){
	services.factory('gameScoring', ['$log', 'gameTimer', 'gameInstance', 
        function($log, gameTimer, gameInstance){				
	    
        $log.info("gameScoring");

        var update = function(wasCorrect){
            var responseLevel = 0;

            if(wasCorrect){
                var plusScore = 10;
                var plusTime = 0;

                gameInstance.status.corrects++;

                if(gameTimer.problemTime.value < 5){
                    plusTime += 5;
                    plusScore += 3;
                    responseLevel = 4;
                }else if(gameTimer.problemTime.value < 8){
                    plusTime += 3;
                    plusScore += 1;
                    responseLevel = 3;
                }else if(gameTimer.problemTime.value < 10){
                    plusTime += 1;
                    responseLevel = 2;
                }else{
                    responseLevel = 1;
                }

                gameInstance.status.score += plusScore;
                
            }else{                      
                gameInstance.status.incorrects++;
            }
            
            return responseLevel;            
        }

        return {
            update : update
        }

	}]);
})
