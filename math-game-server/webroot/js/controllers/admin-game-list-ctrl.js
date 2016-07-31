define(["angular", "js/controllers", 'js/services/game-services'], function(angular, controllers){

	controllers.controller('gameListCtrl', ['$scope', 'game', '$location', function($scope, game, $location){
    	$scope.games;
		
		$scope.getAll = function(){
			game.all.get().then(function(response){
	    		$scope.games = response;
	    	});
		}

		$scope.goToEditPage = function(gameId){
			var path = "/admin-game-edit/"+ gameId;
			console.log(path);
			$location.path(path);
		};
    	
		$scope.del = function(gameId){
			game.one.del(gameId).then(function(response){
				$scope.getAll();
			});
		};
		
		$scope.createInstance = function(gameConfig){
			game.instance.one.post(gameConfig).then(function(response){
				console.log(response);
			});
		};
		
		$scope.getAll();
    	
    }]);        
});
