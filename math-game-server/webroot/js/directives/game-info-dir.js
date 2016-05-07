define(["angular", 
	"js/directives", 
	'js/services/game-tooltips',
	'js/services/game-instance',
	'js/services/game-levels',
	'js/services/game-timer'
	], function(angular, directives, gameTooltips, gameInstance){
	directives.directive('gameInfo', function(){
		return {
			restrict : 'E',
			templateUrl : "/views/game-info.html",
			controller: 'gameInfoCtrl'
		};
	});
	
	directives.controller('gameInfoCtrl',['$scope','$sce', 'gameTooltips','gameTimer',
        function($scope, $sce, gameTooltips, gameTimer){
		
        console.log("gameInfoCtrl");
		$scope.tooltips = gameTooltips.tooltips;

		$scope.getPrecount = function(){            
            var preCount = gameTimer.problemTime.extraTime;
            return preCount;
        }
	}]);
});
