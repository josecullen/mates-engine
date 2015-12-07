(function() {
	'use strict';

	define([], function(require) {
		var app = angular.module('app', [ 'controllers', 'ngRoute' ]);
		app.config([ '$routeProvider', function($routeProvider) {
			$routeProvider
				.when('/admin-game-new', {
					templateUrl : 'partials/admin-game-new.html'
				})
				.when('/admin-game-list', {
					templateUrl : 'partials/admin-game-list.html',
					controller: 'gameListCtrl'
				})
				.when('/admin-instance-new', {
					templateUrl : 'partials/admin-instance-new.html'
				})
				.when('/admin-instance-list', {
					templateUrl : 'partials/admin-instance-list.html',
					controller: 'adminInstancesCtrl'
				})
				.when('/player-select-instance',{
					templateUrl : 'partials/player-select-instance.html',
					controller: 'playerCtrl'
				})
				.when('/player-play',{
					templateUrl : 'partials/player-play.html',
					controller: 'playerCtrl'
				})
//				.otherwise({
//					redirectTo : '/admin'
//				});
		}]);
		
		app.directive("mathjaxBind", function() {
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
		
		
	});

})();
