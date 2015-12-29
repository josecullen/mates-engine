define(["angular", "js/controllers", 'js/services/service', 'js/services/game-services'], function(angular, controllers){
	
	controllers.controller('playerCtrl', 
        ['$scope', 'game','$location', '$interval', '$log', '$rootScope', '$timeout', '$sce',
        function($scope, game, $location, $interval, $log, $rootScope, $timeout, $sce){

    	$scope.instances = [];
    	
    	$scope.instance = {
    		status : "NOT_SELECTED"
    	};

        $scope.$watch('instance.status', function(newValue, oldValue) {
            if(newValue == 'GAME_OVER' || newValue == 'NOT_SELECTED'){
                $rootScope.showNav = true;
            }else{
                $rootScope.showNav = false;
            }
        });
        
        //$scope.htmlTooltip = $sce.trustAsHtml('<h2>I\'ve been made <b>bold</b></h2>');
        $scope.tooltips = {            
            toolTipClass : 'tooltip-ok',
            score : {
                message : $sce.trustAsHtml(''),
                toolTipClass : 'tooltip-ok',
                show : false
            },
            time : {
                message : $sce.trustAsHtml(''),
                toolTipClass : 'tooltip-ok',
                show : false
            },
            lives : {
                message : $sce.trustAsHtml('<h2>-1</h2>'),
                toolTipClass : 'tooltip-nook',
            }
        };

        $scope.showHideTooltips = function(mustShow){
            if(mustShow){
                $scope.tooltips.score.show = true;
                $scope.tooltips.time.show = true;
            }else{
                $scope.tooltips.score.show = false;
                $scope.tooltips.time.show = false;
           }
        }
    	
    	$scope.player = { name: ""};
    	
    	$scope.gameStatus = {};
    	
    	$scope.actualProblem = {};
    	
    	updateTime = function(){
    		//console.log("updating");
    		$scope.gameStatus.time--;
    		$scope.gameStatus.problemTime++;
    		
    		if($scope.gameStatus.time <= 0){
    			$log.info("GAME OVER");
    			$interval.cancel(stopGame);
				$scope.instance.status = "GAME_OVER";
    		}
    	}
    	
    	var stopGame;
    	
    	$scope.begin = function(){
    		$interval.cancel(stopGame);
    		stopGame = $interval(updateTime, 1000);	
    		$scope.actualProblem = $scope.instance.levels[0][0];
    	}

        $scope.pauseAndReleaseTime = function(){
          if (angular.isDefined(stop)) {
            $interval.cancel(stopGame);
            stopGame = undefined;

            $timeout(function(){
                $scope.showHideTooltips(false);

                if ( angular.isDefined(stopGame) ) {
                    return;
                }else{
                    stopGame = $interval(updateTime, 1000);    
                }                
            }, 1700);
          }
        }
    	



    	$scope.response = function(answer){
    		if($scope.verifyAnswer(answer)){
                if($scope.actualProblem.correctAnswer.length == 0){
                    $scope.endProblem(true);
                }    			
    		}else{    			
    			$scope.endProblem(false);               
    		} 
    	};


        $scope.lessAnswers = [];
        $scope.verifyAnswer = function(answer){
            $scope.pauseAndReleaseTime();
            var correctAnswers = $scope.actualProblem.correctAnswer;

            for (var i = $scope.actualProblem.correctAnswer.length - 1; i >= 0; i--) {                
                if(answer == $scope.actualProblem.correctAnswer[i]){
                    $scope.lessAnswers.push($scope.actualProblem.correctAnswer.splice(i, 1));

                    //$log.info("correct "+$scope.actualProblem.correctAnswer.length);
                    return true;
                }
            };

            //$log.warn("incorrect");
            return false;
        }

        $scope.checkButtonOptionAnswer = function(answer){

            var correctAnswers = $scope.lessAnswers;
   
            for (var i = correctAnswers.length - 1; i >= 0; i--) {                
                if(answer == correctAnswers[i]){
            //        $log.info("correct");
                    return true;
                }
            };

            //$log.warn("incorrect");
            return false;
        }

        $scope.endProblem = function(wasCorrect){
            if(wasCorrect){
                var plusScore = 10;
                var plusTime = 0;
                $scope.gameStatus.corrects++;

                //$scope.gameStatus.score += 10;
                if($scope.gameStatus.problemTime < 5){
                    //$scope.gameStatus.time += 5;
                    plusTime += 5;
                    plusScore += 3;
                    $scope.tooltips.toolTipClass = 'tooltip-great';
                }else if($scope.gameStatus.problemTime < 8){
                    plusTime += 3;
                    plusScore += 1;
                    $scope.tooltips.toolTipClass = 'tooltip-good';
                }else if($scope.gameStatus.problemTime < 10){
                    plusTime += 1;
                    $scope.tooltips.toolTipClass = 'tooltip-ok';
                }else{
                    $scope.tooltips.toolTipClass = 'tooltip-pass';    
                }
                
                $scope.gameStatus.score = plusScore;
                
                $scope.tooltips.score = $sce.trustAsHtml('<h2>+'+plusScore+'</h2>');
                $scope.tooltips.time = $sce.trustAsHtml('<h2>+'+plusTime+'</h2>');
                $scope.showHideTooltips(true);
                

            }else{
                $scope.gameStatus.lives.pop();

                if($scope.gameStatus.lives.length == 0){
                    $scope.instance.status = "GAME_OVER";
                }
                
                $scope.gameStatus.incorrects++;
            }

            for (var i = $scope.actualProblem.correctAnswer - 1; i >= 0; i--) {  
                $scope.lessAnswers
                    .push($scope.actualProblem.correctAnswer.splice(i, 1));
              
            };

            $scope.sendScoring();
            $scope.gameStatus.problemStatus = "SHOW_SOLUTION";

            $timeout(function(){
                $scope.gameStatus.problemTime = 0;
                $scope.gameStatus.problemStatus = "SHOW_PROBLEM";
                $scope.nextProblem();           
            }, 1500);
        };


    	
    	$scope.sendScoring = function(){
    		$log.info("sending scoring . . .");
    		game.player.one.put($scope.gameStatus).then(function(response){
    			$log.info("scoring sended");
    			$log.info(response);
    		});
    	};
    	
    	$scope.nextProblem = function(){
    		var problemCount = $scope.gameStatus.problemCount;
    		var levelCount = $scope.gameStatus.levelCount;
    		var levels = $scope.instance.levels;
    		
    		if(problemCount == levels[levelCount].length -1){
    			if(levelCount == levels.length -1){
    				$log.info("GAME OVER");
    				$scope.instance.status = "GAME_OVER";
    			}else{
    				
    				$scope.gameStatus.levelCount++;
    				$scope.gameStatus.problemCount = 0;
    			}
    		}else{
    			$scope.gameStatus.problemCount++;
    		}
    		
    		$scope.actualProblem = $scope.instance.levels[$scope.gameStatus.levelCount][$scope.gameStatus.problemCount];
            $scope.lessAnswers = [];            

       	};
      	
    	
    	
		
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
				console.log(response);
			
				if(response.status == 'SUCCESS'){
					
					$log.debug("SUCCESS");
					$log.debug(instance);
					
					if(instance.type == "MULTI_INSTANCE_GAME"){
						
						$log.debug("MULTI_INSTANCE_GAME");
						
						game.instance.one.get(instance.gameId, instance._id, "RANDOM").then(function(instanceResponse){
							$scope.instance = instanceResponse;	
							$scope.instance.type = "MULTI_INSTANCE_GAME";
							$scope.configGame(response.id);
							$scope.begin();	
						});
					
					}else{
						$scope.instance = instance;
						$scope.configGame(response.id);
						$scope.begin();
					}

				}
			});			
		};
		
		$scope.configGame = function(id){
			$scope.instance.status = 'SUCCESS';
			$scope.gameStatus.id = id;
			$scope.gameStatus.instanceId = $scope.instance._id;
		}
		
		game.instance.all.get().then(function(response){
    		console.log(response);
    		$scope.instances = response;
    	});
		
		$scope.printInstance = function(){
            return angular.toJson($scope.instance, true);
        };
		
		
    }]);
	
});
