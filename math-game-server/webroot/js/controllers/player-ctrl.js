define(["angular", 
        "js/controllers", 
        'js/services/game-services', 
        'js/services/game-timer',
        'js/services/game-levels',
        'js/services/game-tooltips',        
        'js/services/game-scoring',
        'js/services/game-instance'],
		function(angular, controllers){
	
	controllers.controller('playerCtrl', 
        ['$scope', 'game','$location', '$interval', '$log','$rootScope', '$timeout', '$sce', 
        'gameTimer', 'gameLevels', 'gameTooltips', 'gameInstance', 'gameScoring',
        function(
            $scope, game, $location, $interval, $log, $rootScope, $timeout, $sce, 
            gameTimer, gameLevels, gameTooltips, gameInstance, gameScoring){

        $log.info("playerCtrl");

        $scope.gameTimer = gameTimer;
        $scope.gameLevels = gameLevels;
        $scope.gameStatus = gameInstance.status;
        $scope.instance = gameInstance.instance;

    	$scope.instances = [];
    	$scope.player = { name: ""};

        $scope.$watch('instance.status', function(newValue, oldValue) {
            if(newValue == 'GAME_OVER' || newValue == 'NOT_SELECTED'){
                $rootScope.showNav = true;
            }else{
                $rootScope.showNav = false;
            }
        });
        

        $scope.timeCallback = function(gameTime, problemTime){
            if(gameTime <= 0){
                $log.info("GAME OVER");
                gameTimer.pause(0);
                gameInstance.instance.status = "GAME_OVER";
            }
        }

        $scope.onPauseCallback = function(answer){               
            gameTooltips.showHideTooltips(true);
            var problemStatus = gameLevels.checkAnswer(answer);

            if(problemStatus.isCorrect && problemStatus.isProblemEnds){
                gameTooltips.setResponseLevel(gameScoring.update(true));
            }else if(!problemStatus.isCorrect){
                gameTooltips.setResponseLevel(gameScoring.update(false));
            }

            $scope.sendScoring();
            gameInstance.status.problemStatus = "SHOW_SOLUTION";

            $timeout(function(){
                gameLevels.next();
                gameTimer.problemTime.reset();
                gameTooltips.showHideTooltips(false);
                gameInstance.status.problemStatus = "SHOW_PROBLEM";    
            }, 1500);

        }

    	$scope.response = function(answer){
            gameTimer.pause(1700, $scope.onPauseCallback(answer));    		 
    	};
/*
        $scope.endProblem = function(wasCorrect){

            if(wasCorrect){
                var plusScore = 10;
                var plusTime = 0;
                $scope.gameStatus.corrects++;

                if(gameTimer.problemTime.value < 5){
                    plusTime += 5;
                    plusScore += 3;
                    gameTooltips.setResponseLevel(4);
                }else if(gameTimer.problemTime.value < 8){
                    plusTime += 3;
                    plusScore += 1;
                    gameTooltips.setResponseLevel(3);
                }else if(gameTimer.problemTime.value < 10){
                    plusTime += 1;
                    gameTooltips.setResponseLevel(2);
                }else{
                    gameTooltips.setResponseLevel(1);
                }

                gameTooltips.setScoreTooltip(plusScore);
                gameTooltips.setTimeTooltip(plusTime);

                $scope.gameStatus.score += plusScore;
                
            }else{
                gameTooltips.setScoreTooltip(0);
                gameTooltips.setTimeTooltip(0);

                $scope.gameStatus.lives.pop();
                gameTooltips.setResponseLevel(0);
                if($scope.gameStatus.lives.length == 0){
                    $scope.instance.status = "GAME_OVER";
                }
                
                $scope.gameStatus.incorrects++;
            }
            
            $scope.sendScoring();
            $scope.gameStatus.problemStatus = "SHOW_SOLUTION";

            $timeout(function(){
                gameLevels.next();
                gameTimer.problemTime.reset();
                gameTooltips.showHideTooltips(false);
                $scope.gameStatus.problemStatus = "SHOW_PROBLEM";    
            }, 1500);
        };

*/
    	
    	$scope.sendScoring = function(){
    		$log.info("sending scoring . . .");
    		game.player.one.put(gameInstance.gameStatus).then(function(response){
    			$log.info("scoring sended");
    			$log.info(response);
    		});
    	};

        $scope.joinGame = function(name, instance){
            if(gameInstance.join(name, instance)){
                gameLevels.next();
                gameTimer.start($scope.timeCallback);
            }
        }
		/*
		$scope.joinGame = function(name, instance){
			$scope.gameStatus = {
		    		id : "",
                    problemStatus: "SHOW_PROBLEM",
		    		instanceId : "",
		    		lives: [1,2,3],
		    		problemCount: 0,
		    		corrects: 0,
		    		incorrects: 0,    	
		    		score: 0,		
		    		time: 60,
		    		problemTime: 0,
		    		levelCount: 0
		    	};
			
			var params = {
				"playerName" : name,
				"instance" : instance 
			};
			
			game.player.one.post(params).then(function(response){
			
				if(response.status == 'SUCCESS'){
					
					if(instance.type == "MULTI_INSTANCE_GAME"){
						
						$log.debug("MULTI_INSTANCE_GAME");
						
						game.instance.one.get(instance.gameId, instance._id, "RANDOM").then(function(instanceResponse){
							$scope.instance = instanceResponse;	
							$scope.instance.type = "MULTI_INSTANCE_GAME";
							$scope.configGame(response.id);
		                    gameLevels.setGame($scope.instance);
                            gameLevels.next();    
                            gameTimer.start($scope.timeCallback);
						});
					
					}else{
						$scope.instance = instance;
						$scope.configGame(response.id);
		                gameLevels.setGame($scope.instance);
                        gameLevels.next();
                        gameTimer.start($scope.timeCallback);
					}

				}
			});			
		};
		
		$scope.configGame = function(id){
			$scope.instance.status = 'SUCCESS';
			$scope.gameStatus.id = id;
			$scope.gameStatus.instanceId = $scope.instance._id;
		}
		*/
		game.instance.all.get().then(function(response){
    		console.log(response);
    		$scope.instances = response;
    	});
		
		$scope.printInstance = function(){
            return angular.toJson($scope.instance, true);
        };
		
		
    }]);
	
});
