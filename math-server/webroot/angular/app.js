var app = angular.module('app', ['ngRoute'])
.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
	$routeProvider
	.when('/game', {
        templateUrl: 'angular/game.html'
      })
    .when('/examples', {
        templateUrl: 'angular/examples.html'
    })
      
    .otherwise('/examples', {
        templateUrl: 'angular/examples.html'
    });

}])
.controller('appCtrl', function($http, $scope, $rootScope, $interval, $timeout) {
	
	$scope.game = {
			puntos: 0,
			vidas: [1,2,3],
			estado:{
				comenzo: false,
				termino: false	
			},
			ejercicio: {
				expresion: "",
				respuesta: "",
				estado:{
					respondio: false,
					mostrarResultado: 0
				}
			},
			tiempo: {
				restante: 100,
				respuesta: 0,
				bonificaciones: [3,5,7][10,5,3]
			},
			nivel: {
				actual: 0,
				niveles: [
				          "p or q", 
				          "p or (r or q)", 
				          "(p or q) then (r or s)", 
				          "(p or (q or s) then (r or s)", 
				          "(p or (q or s) then ((p or r) then g)"
				          ],
				ejerciciosCompletos: 0,
				ejerciciosPorNivel: 3
			}		
	}
	
	updateTime = function(){
		console.log("updating");
		$scope.game.tiempo.restante--;
		$scope.game.tiempo.respuesta++;
	}
	
	getNext = function(){
		$scope.game.ejercicio.estado.respondio = false;
		$scope.game.nivel.ejerciciosCompletos++;
		$scope.game.tiempo.respuesta = 0;
		if($scope.game.nivel.ejerciciosCompletos == $scope.game.nivel.ejerciciosPorNivel){
			$scope.game.nivel.actual++;
		}
		$scope.getExercise($scope.game.nivel.niveles[$scope.game.nivel.actual]);
	}
	
	$scope.newGame = {};
	angular.copy($scope.game, $scope.newGame);
	$scope.reset = function(){
		angular.copy($scope.newGame, $scope.game);
		$scope.getExercise($scope.game.nivel.niveles[$scope.game.nivel.actual]);
		$scope.comenzar();
	}
	
	$scope.response = function(response){
		$scope.game.ejercicio.estado.respondio = true;
		
		if(response == $scope.game.ejercicio.respuesta){
			console.log("Correcta!");
			$scope.game.puntos++;			
			if($scope.game.tiempo.respuesta < 5){
				$scope.game.tiempo.restante += 10;
			} 
		
		}else{
			console.log("Incorrecta!");
			$scope.game.vidas.pop();
			if($scope.game.vidas.length == 0){
				$scope.game.estado.termino = true;
			}
		}
		$timeout(getNext, 2000);
	}
	
	
	$scope.comenzar = function(){
		$scope.game.estado.comenzo = true;
		$interval(updateTime, 1000);	
	}
	
	
	
	
	
	$scope.exercise= {
			expression: "",
			answer: ""
	};
	$rootScope.valueMath = "$$\\star$$";
	
	$scope.expressionResult;
	$scope.newExpression;
	$scope.exercise;
	$scope.signProbability = 3;
	
	$scope.forceMath = function(){
		MathJax.Hub.Queue([ "Typeset", MathJax.Hub, $rootScope.valueMath ]);
	}
	
	$scope.init = function() {		
		$scope.forceMath();
	};
	
	$scope.getExpression = function(expressionString){
		$http({
			method: 'GET',
			url: '/expression?expression='+expressionString
		}).then(function success(response){
			$scope.expressionResult = $scope.expressionToLaTex(response.data.expression);
		}, function error(response){
			console.log(response);
		});
	}
	
	$scope.getNewExpression = function(expressionString,signProbability){
		$http({
			method: 'GET',
			url: '/newExpression?expression='+expressionString+"&signProbability="+signProbability
		}).then(function success(response){
			$scope.newExpression= $scope.expressionToLaTex(response.data.expression);
			
		}, function error(response){
			console.log(response);
		});
	}
	
	$scope.getExercise = function(expressionString){
		$http({
			method : 'GET',
			url : '/exercise?expression='+expressionString
		}).then(function successCallback(response) {
			console.log(response);
			$scope.exercise.expression = $scope.expressionToLaTex(response.data.expression);
			$scope.exercise.answer = response.data.answer.answer;
			$scope.game.ejercicio.expresion = $scope.exercise.expression;
			$scope.game.ejercicio.respuesta = $scope.exercise.answer;
		}, function errorCallback(response) {
			console.log(response);
		});
	}
	
	
	
	$scope.expressionToLaTex = function(expression){
		String.prototype.replaceAll = function(s,r){return this.split(s).join(r)}

			expression = expression.replaceAll("and", "\\land");
			expression = expression.replaceAll("or", "\\lor");
			expression = expression.replaceAll("then", "\\Rightarrow");
			expression = expression.replaceAll("-", "\\thicksim ");	
		return expression;
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
});;