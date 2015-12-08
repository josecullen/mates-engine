define(["angular", "js/controllers", 'js/services/service', 'js/services/game-services'], function(angular, controllers){
	
	controllers.controller('playerCtrl', ['$scope', 'game','$location', '$interval', '$log', function($scope, game, $location, $interval, $log){
    	$scope.instances = [];
    	
    	$scope.instance = {
    		status : "NOT_SELECTED"
    	};
    	
    	$scope.gameStatus = {
    		id : "",
    		instanceId : "",
    		problemCount: 0,
    		corrects: 0,
    		incorrects: 0,    	
    		score: 0,		
    		time: 60,
    		problemTime: 0,
    		levelCount: 0
    	};
    	
    	$scope.actualProblem;
    	
    	updateTime = function(){
    		console.log("updating");
    		$scope.gameStatus.time--;
    		$scope.gameStatus.problemTime++;
    		
    		if($scope.gameStatus.time <= 0){
    			$log.info("GAME OVER");
    			$interval.cancel(stopGame);
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
    			$scope.gameStatus.score += 15;
    			if($scope.gameStatus.problemTime < 5){
    				$scope.gameStatus.time += 3;
    			}
    		}else{
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
    				$scope.instance.status = "GAME OVER";
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
			var params = {
				"playerName" : name,
				"instance" : instance 
			};
			game.player.one.post(params).then(function(response){
				console.log(response);
				if(response.status == 'SUCCESS'){
					$scope.instance = instance;
					$scope.instance.status = 'SUCCESS';
					$scope.gameStatus.id = response.id;
					$scope.gameStatus.instanceId = $scope.instance._id;
					$scope.begin();
					$log.info($scope.instance);
					$log.info($scope.gameStatus);
				}
			});			
		};
		
		game.instance.all.get().then(function(response){
    		console.log(response);
    		$scope.instances = response;
    	});
		
		
		
		
    }]);
	
});
