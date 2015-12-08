define(["angular", "js/controllers", 'js/services/service', 'js/services/game-services'], function(angular, controllers){

	controllers.controller('gameController', ['$scope', 'game','$location', '$interval', '$log', function($scope, game, $location, $interval, $log){
    	$scope.gameStatus = 'NOT CREATED';
    	
    	$scope.statuses = [{
            id: "MULTI_INSTANCE_GAME",
            name: "Juego multi instancia"        
        }, {
            id: "ONE_INSTANCE_GAME",
            name: "Juego de una instancia"        
        }, {
            id: "TOURNAMENT",
            name: "Tournament"        
        }];
    	
    	
    	$scope.game = {
    		name: "",
    		type: $scope.statuses[0].id,
    		levels: []
    	}    	
    	
    	$scope.addLevel = function(){
    		var level = {
    			form: "a + b",
    			max: 6,
    			min: 0,
    			probablySign: 0.2,
    			operations: "+*",
    			divisionFactor: 1,
    			repetitions: 5
    		};
    		
    		$scope.game.levels.push(level);
    	};
    	
    	$scope.removeLevel = function(index){
    		if (index > -1) {
    			$scope.game.levels.splice(index, 1);
    		}
    	}; 
    	
    	$scope.reset = function(){
    		$scope.game = {
    	    	name: "",
    	    	levels: []    	    
    		};
    		$scope.addLevel();
        	$scope.gameStatus = 'NOT CREATED';
    	}
    	
    	$scope.submitGame = function(){
    		$log.info("submit game");
    		console.log($scope.game);
    		game.one.post($scope.game).then(function(response){
    			$scope.gameStatus = 'CREATED';
    		});
    	};

    	
    	
    	
    	
    	
    	
    	
    	
    	
        
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	$scope.addLevel();
    	
    	
    }]);        
});
