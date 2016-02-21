define(["angular", "js/directives", 'js/services/game-tooltips'], function(angular, directives){
	directives.directive('gameLevel', function(){
		return {
			restrict : 'E',
			templateUrl : "/views/game-level.html",
			controller: 'gameLevelCtrl'
		};
	});
	
	directives.controller('gameLevelCtrl',['$scope','$sce', 'gameInstance', 'gameTimer', '$interval', 
		function($scope, $sce, gameInstance,gameTimer,$interval){
		
        console.log("gameLevelCtrl");
        $scope.gameInstance = gameInstance;
        $scope.count = 3;
        console.log($scope.gameInstance.status.responses.great);
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
		        
/*
		        $scope.count = 3;
		        for(var i = 0; i < 3; i++){
        	     	$timeout(function(){
        	     		$scope.count--;
                	}, 1000);  
        	    }
        	    

*/
	                    

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



	}]);
});
