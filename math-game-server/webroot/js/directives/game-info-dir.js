define(["angular", "js/directives", 'js/services/game-tooltips'], function(angular, directives){
	directives.directive('gameInfo', function(){
		return {
			restrict : 'E',
			templateUrl : "/views/game-info.html",
			controller: 'gameInfoCtrl'
		};
	});
	
	directives.controller('gameInfoCtrl',['$scope','$sce', 'gameTooltips', 
        function($scope, $sce, gameTooltips){
		
        console.log("gameInfoCtrl");
		$scope.tooltips = gameTooltips.tooltips;

	}]);
});
