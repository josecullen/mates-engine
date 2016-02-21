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
        'gameTimer', 'gameLevels', 'gameTooltips', 'gameInstance', 'gameScoring', '$document',
        function(
            $scope, game, $location, $interval, $log, $rootScope, $timeout, $sce, 
            gameTimer, gameLevels, gameTooltips, gameInstance, gameScoring, $document){
            var loading;
        $log.info("playerCtrl");
        $scope.gameTimer = gameTimer;
        $scope.gameLevels = gameLevels;

        $scope.gameInstance = gameInstance;

    	$scope.instances = [];
    	$scope.player = { name: ""};
        $scope.problemStatus = gameInstance.status.problemStatus;

        $scope.log = "";

        $scope.$watch('gameInstance.instance.status', function(newValue, oldValue) {
            $scope.checkGameOver(gameInstance.instance.status);
        });

        $scope.$watch('gameInstance.status.problemStatus', function(newStatus){
            $log.info("problem status : "+newStatus);
        });

        $scope.checkGameOver = function(gameStatus){
            $log.info("checkGameOver "+gameInstance.instance.status);

            if(gameStatus == 'GAME_OVER' || gameStatus == 'NOT_SELECTED'){
                $rootScope.showNav = true;
                $rootScope.bodyStyle = {"background-color" : "white"};
                if(gameStatus == 'GAME_OVER'){ 
                    $location.path('/game-over');                
                }
            }else{
                $rootScope.showNav = false;
                $rootScope.bodyStyle = {"background-color" : "black"};
            }
        }
        

        $scope.timeCallback = function(gameTime, problemTime){
            $scope.prueba = gameTime <= 0;
            if($scope.prueba && gameInstance.instance.status != 'NOT_SELECTED'){
                $scope.gameTimer.pause(0);
                $log.info("game over por tiempo");        
                
                gameInstance.instance.status = 'GAME_OVER';
                $scope.checkGameOver(gameInstance.instance.status);
            }
        }

        $scope.onPauseCallback = function(answer){               
            
            var problemStatus = gameLevels.checkAnswer(answer);
            if(isProblemEnds(problemStatus)){
                gameTooltips.showHideTooltips(true);

                if(problemStatus.isCorrect && problemStatus.isProblemEnds){
                    gameTooltips.setResponseLevel(gameScoring.update(true));
                }else{
                    gameTooltips.setResponseLevel(gameScoring.update(false));
                }

                $scope.sendScoring();
                var showNewLevel = gameLevels.isLastProblemForLevel(gameLevels.order.problem);
                if(showNewLevel){
                    gameInstance.status.problemStatus = "SHOW_NEW_LEVEL";  
                    gameLevels.next();

                    
                }else{
                    gameInstance.status.problemStatus = "SHOW_SOLUTION";

                    $timeout(function(){
                        gameTimer.problemTime.reset();
                        gameLevels.next();    
                        gameInstance.status.problemStatus = "SHOW_PROBLEM";                   
                        $scope.solvedClass = '';
                        gameTimer.resume();
                    }, 250);
                }            
               
               
            }

        }



        function isProblemEnds(problemStatus){
            return problemStatus.isCorrect && problemStatus.isProblemEnds || !problemStatus.isCorrect;
        }

    	$scope.response = function(answer){
            //gameTimer.pause(250, $scope.onPauseCallback(answer));    		 
            gameTimer.stop();
            $scope.onPauseCallback(answer);
    	};

        $scope.sendScoring = function(){
            $log.info("sending scoring . . .");
            game.player.one.put(gameInstance.status).then(function(response){
                $log.info("scoring sended");
                $log.info(response);
            });
        };

        $scope.joinGame = function(name, instance){
            
            $log.info("join game . . ." );

            gameInstance.join(name, instance, function(){                
                gameLevels.order.reset();
                gameInstance.instance.status = 'SUCCESS';
                gameInstance.status.problemStatus = "SHOW_NEW_LEVEL";      
                           
                $timeout(function(){
                    gameLevels.next();
                    gameInstance.status.problemStatus = "SHOW_PROBLEM";   
                    gameTimer.start($scope.timeCallback);
                },3000);
                
                $log.info(gameInstance.instance);
            });
            
        }
        

        $timeout(function(){
            game.instance.all.get().then(function(response){
                console.log(response);
                $scope.instances = response;
            });
        },1000);
        
		
    }]);
	
});
