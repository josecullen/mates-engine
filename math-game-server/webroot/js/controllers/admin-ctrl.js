define(["angular", "js/controllers", 'js/services/service', 'js/services/game-services'], function(angular, controllers){

	controllers.controller('gameController', 
        ['$scope', 'game','$location', '$interval', '$log', '$routeParams', '$rootScope',
        function($scope, game, $location, $interval, $log, $routeParams, $rootScope ){
    	console.log($routeParams.gameId);

        $rootScope.showNav = true;

        if($routeParams.gameId == "new"){
            $scope.gameStatus = 'NOT CREATED';
        }else{
            $scope.gameStatus = 'EDITED';
            $log.info('Request for existing game '+$routeParams.gameId);
            game.one.get($routeParams.gameId).then(function(response){
                $log.info(response);
                $scope.game = response;
            });
        }

    	
    	$scope.statuses = [{
            id: "MULTI_INSTANCE_GAME",
            name: "Juego multi instancia"        
        }, {
            id: "ONE_INSTANCE_GAME",
            name: "Juego de una instancia"        
        }, {
            id: "TOURNAMENT",
            name: "Torneo"        
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
    		
            if($scope.gameStatus == 'NOT CREATED'){
                game.one.post($scope.game).then(function(response){
                    $scope.gameStatus = 'CREATED';
                });
            }else{
                game.one.put($scope.game).then(function(response){
                    $scope.gameStatus = 'CREATED';
                    $scope.game._id = $routeParams.gameId;
                });
            }
            
    	};

    	
    	
    	
    	
    	
    	
    	
    	
    	
        
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	$scope.addLevel();
    	
    	
    }]);        
});
