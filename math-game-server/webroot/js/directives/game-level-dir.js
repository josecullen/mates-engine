define(["angular", "js/directives", 'js/services/game-tooltips'], function(angular, directives){
	directives.directive('gameLevel', function(){
		return {
			restrict : 'E',
			templateUrl : "/views/game-level.html",
			controller: 'gameLevelCtrl'
		};
	});
	
	directives.controller('gameLevelCtrl',['$scope','$sce', 'gameInstance', 'gameTimer', '$interval', 'game', 
		function($scope, $sce, gameInstance,gameTimer,$interval, game){
		
        console.log("gameLevelCtrl");
        $scope.gameInstance = gameInstance;
        $scope.count = 3;

        $scope.$watch('gameInstance.status.problemStatus', function(newValue){
        	if(newValue == 'SHOW_NEW_LEVEL'){
        		$scope.count = 3;
        		$scope.clock = "btn-warning";
        		 $scope.data = [
		            {
		                key: "¡Genial!",
		                y: $scope.gameInstance.status.responses.great
		            },
		            {
		                key: "Muy bien",
		                y: $scope.gameInstance.status.responses.good
		            },
		            {
		                key: "Bien",
		                y: $scope.gameInstance.status.responses.ok
		            },
		            {
		                key: "Lentas",
		                y: $scope.gameInstance.status.responses.pass
		            },
		            {
		                key: "Incorrectas",
		                y: $scope.gameInstance.status.responses.nook
		            }
		        ];
				
				var stopCount;
				stopCount = $interval(function(){
	    			$scope.count--;
	    			if($scope.count == 0){
	    				$scope.count = "";
        	    		$scope.clock = "btn-success glyphicon glyphicon-ok";
        	    		$interval.cancel(stopCount);
	    			}
	    		}, 1000);    

			$scope.positionData = [{
	        	key: "Cumulative Return",
	            values: []
	        }];

			game.player.all.get(
			    gameInstance.status.instanceId
			).then(function(response){
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
			    console.log(response);

			    $scope.posicion = $scope.posicion + 1;
			    
			    response.forEach(function(item){
			    	if(item.id == $scope.gameInstance.status.id){
			    		item.color = "#1DD81D";
			    	}else{
			    		item.color = "#8B80F1";
			    	}
			    	item.key = item.name;
			    	item.values = [{
			    		score: item.score,
			    		name: item.name
			    	}];
			    });

			    $scope.positionData = response;		    
			    

			  
			});

		        

		   	}
        });

        $scope.clickClock = function(){
        	gameTimer.problemTime.reset();
	        gameInstance.status.problemStatus = "SHOW_PROBLEM";  
	        gameTimer.resume();  
        }

        $scope.options = {
            chart: {
                type: 'pieChart',
                height: 200,
                donut: true,
                color: ['#06E01B','#7FC879', '#C4D534','#9F9A9A','#D80B0B'],
                x: function(d){return d.key;},
                y: function(d){return d.y;},
                showLabels: false,

                pie: {
                    startAngle: function(d) { return d.startAngle/2 -Math.PI/2 },
                    endAngle: function(d) { return d.endAngle/2 -Math.PI/2 }
                },
                duration: 500,
                showLegend: false
            }
        };
        
        $scope.data = [ ];


        $scope.positionOption = {
            chart: {
                type: 'multiBarHorizontalChart',
                height: 200,
                width: 200,
                x: function(d){return d.name;},
                y: function(d){return d.score;},
                showControls: false,
                showValues: false,
                showLegend: false,
                duration: 500,
                xAxis: {
                    showMaxMin: false
                },
                yAxis: {
                    axisLabel: '',
                    tickFormat: function(d){
                        return d3.format(',.2f')(d);
                    }
                }
            }
        };


        $scope.positionData = [{
        	key: "Cumulative Return",
            values: []
        }];


	}]);
});
