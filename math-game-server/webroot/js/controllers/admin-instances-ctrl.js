define(["angular", "js/controllers", 'js/services/game-services', 'lib/sockjs-1.0.3'], function(angular, controllers){
	
	controllers.controller('adminInstancesCtrl', 
		['$scope', 'game','$location', '$interval', '$log', '$route',
		function($scope, game, $location, $interval, $log, $route){

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

		$scope.del = function(instanceId){
			game.instance.one.del(instanceId).then(function(response){
				if(response.status == 'SUCCESS'){
					$log.info("delete OK");
					$route.reload();
				}else{
					$log.error(response.message);
				}
			});
		};

		$scope.erase = function(instance){
			instance.players = [];
			game.instance.one.put(instance).then(function(response){
				if(response.status == 'SUCCESS'){
					$log.info("players remove OK");
				}else{
					$log.error(response.message);
				}
			});
		};
	
		$scope.showStats = function(instanceId){
			$location.path("/admin-instance-stats/"+instanceId);
		};

		$scope.refreshInstances();
	}]);
});
