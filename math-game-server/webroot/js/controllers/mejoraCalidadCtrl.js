define(["angular", 
        "js/controllers"],
		function(angular, controllers){
	
	controllers.controller('mejoraCalidadCtrl', 
        ['$scope', '$location', '$log', '$timeout', '$http', function( $scope, $location, $log,$timeout, $http){
        	$scope.mejora = "";
        	$scope.sendMejora = function(){
        		return $http.get('/arithmetic/v1/mejoraCalidad?mejora='+$scope.mejora).then(function(response){
        			$scope.mejoraSended = true;
        			$scope.mejoras = response.data;
        			/*
        			$timeout(function(){
        				$location.path('/player-select-instance');
        			},1500);
        			*/
        		});
        	}

        	$scope.selectGame = function(){
        		$location.path('/player-select-instance');
        	}


    }]);
});



