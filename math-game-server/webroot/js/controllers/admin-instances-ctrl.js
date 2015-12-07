define(["angular", "js/controllers", 'js/services/service', 'js/services/game-services'], function(angular, controllers){
	
	controllers.controller('adminInstancesCtrl', ['$scope', 'game','$location', '$interval', '$log', function($scope, game, $location, $interval, $log){

		console.log("adminInstanceCtrl");
			
		$scope.instance = {
			status : "NOT_SELECTED"
		};
			
		$scope.refreshInstances = function() {
			game.instance.all.get().then(function(response) {
				console.log(response);
				$scope.instances = response;
			});
		}
	
		
		
		
		$scope.refreshInstances();
	}]);
});
