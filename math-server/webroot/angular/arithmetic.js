var arithmeticApp = angular.module('arithmetic-app', [])

.controller('arithmetic-ctrl', function(problem, $scope, $rootScope, $interval, $timeout, $log,$window){
	$scope.problem;
	
	$scope.query = {
		form: "a + b",
		max: 6,
		min: 0,
		probablySign: 0.2,
		operations: "+*",
		divisionFactor: 1			
	}
	
	$scope.newProblem = function(){
		problem.get($scope.getQueryStringProblem($scope.query)).success(applyProblem);
	}
	
	$scope.getQueryStringProblem = function(query){
		$scope.path = "/arithmetic/v1/problem";
		$scope.path += "?form="+$window.encodeURIComponent(query.form);
		$scope.path += "&max="+query.max;
		$scope.path += "&min="+query.min;
		$scope.path += "&probablySign="+query.probablySign;
		$scope.path += "&divisionFactor="+query.divisionFactor;
		$scope.path += "&operations="+$window.encodeURIComponent(query.operations);
		return $scope.path;
	}	
	
	function applyProblem(newProblem, status){
		$scope.problem = newProblem;
		$log.info($scope.problem);
	}
	
	$(function () {
		  $('[data-toggle="tooltip"]').tooltip()
		})
	
})



.factory('problem', function($http, $window){
	
	var get = function(query){
		return $http({
			method: 'GET',
			url: query
		});
	};	
	
	return{
		get : get
	}
})

.directive("mathjaxBind", function() {
    return {
        restrict: "A",
        controller: ["$scope", "$element", "$attrs", function($scope, $element, $attrs) {
            $scope.$watch($attrs.mathjaxBind, function(value) {
                var $script = angular.element("<script type='math/tex'>")
                    .html(value == undefined ? "" : value);
                $element.html("");
                $element.append($script);
                MathJax.Hub.Queue(["Reprocess", MathJax.Hub, $element[0]]);
            });
        }]
    };
});
