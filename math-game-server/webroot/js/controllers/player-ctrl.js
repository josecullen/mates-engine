define(["angular", "js/controllers", 'js/services/service', 'js/services/game-services'], function(angular, controllers){
	
	controllers.controller('playerCtrl', ['$scope', 'game','$location', '$interval', '$log', function($scope, game, $location, $interval, $log){
    	$scope.instances = [];
    	
    	$scope.instance = {
    		status : "NOT_SELECTED"
    	};
    	
    	$scope.player = { name: ""};
    	
    	$scope.gameStatus = {
//    		id : "",
//    		instanceId : "",
//    		problemCount: 0,
//    		corrects: 0,
//    		incorrects: 0,    	
//    		score: 0,		
//    		time: 60,
//    		problemTime: 0,
//    		levelCount: 0
    	};
    	
    	$scope.actualProblem;
    	
    	updateTime = function(){
    		console.log("updating");
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
    	
    	$scope.response = function(answer){
    		if(answer == $scope.actualProblem.correctAnswer){
    			$scope.gameStatus.corrects++;
    			$scope.gameStatus.score += 10;
    			if($scope.gameStatus.problemTime < 5){
    				$scope.gameStatus.time += 5;
    				$scope.gameStatus.score += 3;
    			}else if($scope.gameStatus.problemTime < 8){
    				$scope.gameStatus.time += 3;
    				$scope.gameStatus.score += 1;
    			}else if($scope.gameStatus.problemTime < 10){
    				$scope.gameStatus.time += 1;
    			}
    		}else{    			
    			$scope.gameStatus.lives.pop();
    			if($scope.gameStatus.lives.length == 0){
    				$scope.instance.status = "GAME_OVER";
    			}
    			
    			$scope.gameStatus.incorrects++;
    		}
    		$scope.gameStatus.problemTime = 0;
    		$scope.sendScoring();
    		$scope.nextProblem();    		
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
       	};
      	
    	
    	
		
		$scope.joinGame = function(name, instance){
			$scope.gameStatus = {
		    		id : "",
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
		
		
		
		
    }]);
	
});
