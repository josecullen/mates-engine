define(["angular", 
        "js/controllers", 
        'js/services/game-services', 
        'js/services/game-instance'],
		function(angular, controllers){
	
	controllers.controller('gameOverCtrl', 
        ['$scope', 'game','$location', 'gameInstance', '$document', '$log', '$timeout',
        function( $scope, game, $location, gameInstance, $document, $log,$timeout){
        	$scope.gameInstance = gameInstance;

        	$scope.selectGame = function(){
        		$location.path('/player-select-instance');
        	}

        	game.instance.one.get(
			    gameInstance.status.id,
			    gameInstance.status.instanceId,
			    "NO_RANDOM"
			).then(function(response){
			    var positions = new Array();

			    response.players.forEach(function(player){			        
			        if(player.scoring != undefined){
			            var playerScore = {};
			            playerScore.id = player.id;
			            playerScore.name = player.name;
			            playerScore.score = player.scoring.score;

			            if(positions.length == 0){
			                positions.push(playerScore);
			            }else{
			                var isInserted = false;
			                for(var i = 0; i < positions.length; i++ ){
			                    if(playerScore.score > positions[i].score){
			                        positions.splice(i,0,playerScore);
			                        isInserted = true;
			                        break;
			                    }
			                }                         
			                if(!isInserted){
			                    positions.push(playerScore);
			                }
			            }			        
			        }			        
			    });			   

			    $scope.posicion = -1;
			    for(var i = 0; i < positions.length; i++ ){			        
			        if(gameInstance.status.score >= positions[i].score){
			            $scope.posicion = i;
			            break;
			        }
			    }     

			    if(positions.length >= 6){
			        if($scope.posicion <= 2){
			            positions = positions.slice(0,5);
			        }else if(positions.length < $scope.posicion + 3){
			            var dif = $scope.posicion + 3 - positions.length;
			            positions = positions.slice($scope.posicion - 3 - dif,$scope.posicion +3 - dif);
			        }else{
			            positions = positions.slice($scope.posicion - 3,$scope.posicion +3);                            
			        }
			    }

			    $scope.posicion = $scope.posicion + 1;
			    $scope.data[0].values = positions;

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



