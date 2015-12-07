define(["angular", "js/services"], function(angular, services){
	services.factory('gameHandler', ['$http', '$log', function($http, $log, $interval ){		
		var gameStatus = {
	    	problemCount: 0,
	    	corrects: 0,
	    	points: 0,		
	    	time: 60,
	    	problemTime: 0,
	    	levelCount: 0
	    };
	    
	    var updateTime = function(){
	    	console.log("updating");
	    	gameStatus.time--;
	    	gameStatus.problemTime++;
	    	
	    	if(gameStatus.time <= 0){
	    		$log.info("GAME OVER");
	    		$interval.cancel(stopGame);
	    	}
	    }
	    
	    var stopGame;
	    
	    var begin = function(){
	    	$interval.cancel(stopGame);
	    	stopGame = $interval(updateTime, 1000);	
	    }
	     	

		
		
		return {
			getInstance : function(instance){	
				return {
					gameStatus : gameStatus,
					updateTime : updateTime,
					begin : begin
				}
			}	
		};
    	
    }]);
});
