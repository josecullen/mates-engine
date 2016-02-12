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
            $scope.checkGameOver(newValue);
        });

        $scope.checkGameOver = function(gameStatus){
            if(gameStatus == 'GAME_OVER' || gameStatus == 'NOT_SELECTED'){
                $rootScope.showNav = true;
                $rootScope.bodyStyle = {"background-color" : "white"};
      //          $timeout(function(){
  //                  $log.info("disableFullscreen");
//                    disableFullScreen();
    //            },0);
            }else{
                $rootScope.showNav = false;
                $rootScope.bodyStyle = {"background-color" : "black"};
            }
        }
        

        $scope.timeCallback = function(gameTime, problemTime){
            $scope.prueba = gameTime <= 0;
            if($scope.prueba){
                $scope.gameTimer.pause(0);
                gameInstance.instance.status = 'GAME_OVER';
                $scope.checkGameOver(gameInstance.instance.status);
            }
        }

        $scope.onPauseCallback = function(answer){               
            
            var problemStatus = gameLevels.checkAnswer(answer);

            if(problemStatus.isCorrect && problemStatus.isProblemEnds){
                gameTooltips.showHideTooltips(true);
                gameTooltips.setResponseLevel(gameScoring.update(true));
                $scope.sendScoring();
            
                gameLevels.next();
                if(gameLevels.order.problem == 0){
                    gameInstance.status.problemStatus = "SHOW_NEW_LEVEL";    
                }else{
                    gameInstance.status.problemStatus = "SHOW_SOLUTION";
                }            

                $timeout(function(){
                    gameTimer.problemTime.reset();
                    gameTooltips.showHideTooltips(false);
                    gameInstance.status.problemStatus = "SHOW_PROBLEM";    
                }, 1500);
            }else if(!problemStatus.isCorrect){
                gameTooltips.showHideTooltips(true);
                gameTooltips.setResponseLevel(gameScoring.update(false));
                $scope.sendScoring();
            
                gameLevels.next();
                if(gameLevels.order.problem == 0){
                    gameInstance.status.problemStatus = "SHOW_NEW_LEVEL";    
                }else{
                    gameInstance.status.problemStatus = "SHOW_SOLUTION";
                }            

                $timeout(function(){
                    gameTimer.problemTime.reset();
                    gameTooltips.showHideTooltips(false);
                    gameInstance.status.problemStatus = "SHOW_PROBLEM";    
                }, 1500);
            }

        }

    	$scope.response = function(answer){
            gameTimer.pause(1700, $scope.onPauseCallback(answer));    		 
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
  /*
            $timeout(function(){
  
                $log.info("requestFullscreen");
                $scope.log = "requestFullscreen";
                enableFullScreen();
            },0);
*/
            gameInstance.join(name, instance, function(){
                gameLevels.order.reset();
                gameLevels.next();
                gameTimer.start($scope.timeCallback);
            });
            
        }
        
/*        
        function enableFullScreen(){
            var all = document.getElementById("allBody");
            if (document.body.requestFullscreen) {
                document.body.requestFullscreen();
            } else if (document.body.webkitRequestFullscreen) {
                document.body.webkitRequestFullscreen();
            } else if (document.body.mozRequestFullScreen) {
                document.body.mozRequestFullScreen();
            } else if (document.body.msRequestFullscreen) {
                $scope.log = "msRequestFullscreen";
                loading = document.getElementById('full_loading');
                loading.msRequestFullscreen();

                $timeout(function(){
                    document.body.msRequestFullscreen();
                },0);
  

            }else{
                $scope.log = "fullscreen no compatible ";
            }
        }

        function disableFullScreen(){
            $scope.log = "disableFullscreen";
            if (document.body.exitFullscreen) {
                document.body.exitFullscreen();
            } else if (document.body.webkitExitFullscreen) {
                document.body.webkitExitFullscreen();
            } else if (document.body.mozCancelFullScreen) {
                document.body.mozCancelFullScreen();
            } else if (document.body.msExitFullscreen) {
                document.body.msExitFullscreen();
            }
        }
*/
        $timeout(function(){
            game.instance.all.get().then(function(response){
                console.log(response);
                $scope.instances = response;
            });
        },1000);
        
		
    }]);
	
});
