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

        /*
        $scope.tooltips = {            
            toolTipClass : 'tooltip-ok',
            score : {
                message : $sce.trustAsHtml('<h2>asa</h2>'),
                toolTipClass : 'tooltip-ok',
                show : false
            },
            time : {
                message : $sce.trustAsHtml('<h2></h2>'),
                toolTipClass : 'tooltip-ok',
                show : false
            },
            lives : {
                message : $sce.trustAsHtml('<h2>-1</h2>'),
                toolTipClass : 'tooltip-nook',
            }
        };

        $scope.showHideTooltips = function(mustShow){
            if(mustShow){
                $scope.tooltips.score.show = true;
                $scope.tooltips.time.show = true;
            }else{
                $scope.tooltips.score.show = false;
                $scope.tooltips.time.show = false;
           }
        }
*/

	}]);
});
