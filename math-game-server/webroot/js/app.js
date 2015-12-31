(function() {
	'use strict';

	define([], function(require) {
		var app = angular.module('app', [ 'controllers', 'directives', 'ngRoute', 'ngAnimate', 'nvd3', 'ui.bootstrap' ]);
		app.config([ '$routeProvider', function($routeProvider) {
			$routeProvider
				.when('/admin-game-edit/:gameId', {
					templateUrl : 'partials/admin-game-edit.html',
					controller: 'gameController'
				})
				.when('/admin-game-list', {
					templateUrl : 'partials/admin-game-list.html',
					controller: 'gameListCtrl'
				})
				.when('/admin-instance-stats/:instanceId', {
					templateUrl : 'partials/admin-instance-stats.html',
					controller: 'adminInstanceStats'
				})
				.when('/admin-instance-list', {
					templateUrl : 'partials/admin-instance-list.html',
					controller: 'adminInstancesCtrl'
				})
				.when('/player-select-instance',{
					templateUrl : 'partials/player-select-instance.html',
					controller: 'playerCtrl'
				})
				.otherwise({
					redirectTo : '/player-select-instance'
				});
		}]);
		
		app.directive("mathjaxBind", function() {
		    return {
		        restrict: "A",
		        controller: ["$scope", "$element", "$attrs", "$window", "$timeout", 
		                     function($scope, $element, $attrs, $window, $timeout) {
		            $scope.$watch($attrs.mathjaxBind, function(value) {
		                var $script = angular.element("<script type='math/tex'>")
		                    .html(value == undefined ? "" : value);
		                $element.html("");
		                $element.append($script);
		                MathJax.Hub.Queue(["Reprocess", MathJax.Hub, $element[0]]);

		                $window.MathJax.Hub.Register.StartupHook("Begin",function () { 
		                	$element[0].style.visibility = 'hidden';
		                });		                
		                $window.MathJax.Hub.Register.StartupHook("End",function () { 
		                	$timeout(function(){
			                	$element[0].style.visibility = '';
		                	}, 1500);	                	
		                }); 

		                
		            });
		        }]
		    };
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
	});

})();
