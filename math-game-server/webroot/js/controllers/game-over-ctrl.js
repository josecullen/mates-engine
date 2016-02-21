define(["angular", 
        "js/controllers", 
        'js/services/game-services', 
        'js/services/game-instance'],
		function(angular, controllers){
	
	controllers.controller('gameOverCtrl', 
        ['$scope', 'game','$location', 'gameInstance', '$document', '$log', '$timeout',
        function( $scope, game, $location, gameInstance, $document, $log,$timeout){
        	$scope.gameInstance = gameInstance;
        	$scope.score = gameInstance.status.score;
        	$scope.selectGame = function(){
        		$location.path('/player-select-instance');
        	}

        	game.player.all.get(
			    gameInstance.status.instanceId
			).then(function(response){
				$log.info(response);

			    var positions = new Array();

			    $scope.posicion = -1;

				for(var i = 0; i < response.length; i++){
					if(response[i].id == $scope.gameInstance.status.id){
						$scope.posicion = i;
						break;
					}
				}

			    if(response.length >= 6){
			        if($scope.posicion <= 2){
			            response = response.slice(0,5);
			        }else if(response.length < $scope.posicion + 3){
			            var dif = $scope.posicion + 3 - response.length;
			            response = response.slice($scope.posicion - 3 - dif,$scope.posicion +3 - dif);
			        }else{
			            response = response.slice($scope.posicion - 3,$scope.posicion +3);                            
			        }
			    }

			    $scope.posicion = $scope.posicion + 1;
			    $scope.data[0].values = response;

			    $timeout(function(){
			    	$scope.gameInstance.resetAll();
					gameInstance.resetAll();
			    }, 0)

			});

		$scope.options = {
            chart: {
                type: 'discreteBarChart',
                height: 200,
                color: ['#008DDE','#4DA5DD', '#72AED4','#A5C7DD','#B6C2C9','#B1B4B6','#A5AAAD'],
                margin: {
                    top: 20, right: 20, bottom: 50, left: 55
                },
                x: function(d) {
                    return d.name;
                },
                y: function(d) {
                    return d.score + (1e-10);
                },
                showValues: true,
                valueFormat: function(d) {
                    return d3.format(',.0f')(d);
                },
                duration: 500,
                xAxis: {
                    axisLabel: 'Jugadores'
                },
                yAxis: {
                    axisLabel: 'Puntaje',
                    axisLabelDistance: -10
                }
            }
        };
        
        $scope.data = [{
            key: "Cumulative Return",
            values: []
        }];












    }]);
});



