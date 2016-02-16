define(["angular", "js/services"], function(angular, services){
	services.factory('gameTimer', ['$log', '$interval', '$timeout', function($log, $interval, $timeout){				
		var stopGame;
    	
		var callbackPerSecond = function(){
    		console.log(timer);
    	};

    	var callbackOnPause = function(){
    		console.log("callbackOnPause");
    	}
		
    	var start = function(callback){   		
    		timer.gameTime.value = timer.gameTime.initTime;
    		resetProblemTime();
    		resume(callback);
    	}

        var stop = function(){
            $interval.cancel(stopGame);
        }
    	
    	var resume = function(callback){
    		$interval.cancel(stopGame);

    		if(callback != undefined){
    			callbackPerSecond = callback;
    		}

    		stopGame = $interval(function(){
    			timer.gameTime.value--;
    			timer.problemTime.value++;
    			callbackPerSecond(timer.gameTime.value, timer.problemTime.value);
    		}, 1000);    
    	}

    	var pause = function(timeOnPause, callback){    		
    		if(callback != undefined){
    			callbackOnPause = callback;
    		}

    		if (angular.isDefined(stopGame)) {
	            $interval.cancel(stopGame);
	            stopGame = undefined;
        	}

    		if(timeOnPause > 0){
    			callbackOnPause(timer.gameTime.value, timer.problemTime.value);
    			$timeout(resume, timeOnPause);
    		}			
    	}

    	var resetProblemTime = function(){
    		timer.problemTime.value = 0;
    	}

    	var timer = {
    		start : start,
			pause : pause,
			resume : resume,
            stop : stop,
			gameTime : {				
				value : 60,
				initTime : 60
			},
			problemTime : {
				reset : resetProblemTime,
				value : 0
			}			
		};

    	return timer;

	}]);
})
