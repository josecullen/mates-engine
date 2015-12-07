var arithmeticGameMultiApp = angular.module('arithmetic-game-multi', ['ngRoute'])
.config(['$routeProvider', function($routeProvider) {
	$routeProvider
		.when('/create',{
			templateUrl: '/angular/partials/game/multi/admin-game-create.html'
		})
		.when('/wait',{
			templateUrl: '/angular/partials/game/multi/admin-game-wait.html',
			controller: 'arithmetic-admin-await-ctrl'
		})
		.when('/close',{
			templateUrl: '/angular/partials/game/multi/admin-game-close.html'
		})
		.when('/player-wait',{
			templateUrl: '/angular/partials/game/multi/player-wait.html'
//			controller: 'arithmetic-admin-await-ctrl'
		})
		.when('/player-join',{
			templateUrl: '/angular/partials/game/multi/player-join.html'
		})
		.when('/player-game',{
			templateUrl: '/angular/partials/game/multi/player-game.html',
			controller: 'player-game-ctrl'
		})
		.otherwise({
			redirectTo: "/create"
		});
}])

.controller('arithmetic-game-multi-ctrl', function($scope, $http, $interval, $route, $log){	
	
	$scope.levels = [];

	$scope.game = {
		name: "",
		players: [],
		created: false,
		levels: $scope.levels
	}	
	
	$scope.submitGame = function(){
		var res = $http.post('/arithmetic/v1/game/multi', $scope.game);
		console.log($scope.game);
		res.success(function(data, status, headers, config) {
			$scope.game.created= true;
			
		});
		res.error(function(data, status, headers, config) {
			$log.error( JSON.stringify({data: data}));
			$scope.game.created= false;
		});
	}	
	
	$scope.updateGameStatus = function(status){
		$log.info('updateGameStatus');
		
		var res = $http.put('/arithmetic/v1/game/multi/game?status='+status);
		
		res.success(function(data, status, headers, config) {
			$scope.game.players = data;	
			$log.info(data);

		});
		res.error(function(data, status, headers, config) {
			$log.error( JSON.stringify({data: data}));
			$scope.game.created= false;
		});
	}	
	
	$scope.addLevel = function(){
		var level = {
			form: "a + b",
			max: 6,
			min: 0,
			probablySign: 0.2,
			operations: "+*",
			divisionFactor: 1,
			repetitions: 5
		};
		
		$scope.levels.push(level);
	};
	
	$scope.removeLevel = function(index){
		if (index > -1) {
			$scope.levels.splice(index, 1);
		}
	};
	
	$scope.addLevel();
	
	
	
	
	$(function () {
	  $('[data-toggle="tooltip"]').tooltip()
	});
})

.controller('arithmetic-admin-await-ctrl', function($http, $scope, $log, $interval){
	$scope.games = [];
	
	$scope.getGames = function(){
		var res = $http.get('/arithmetic/v1/game/multi');
		
		res.success(function(data, status, headers, config) {
			$log.info(data);
			$scope.games = data;
		});
		res.error(function(data, status, headers, config) {
			$log.error( JSON.stringify({data: data}));
			$scope.game.created= false;
		});
	}
	
	$interval(
		function() {
			$scope.getGames();
		}, 
		3000
	);
	
	$scope.updateGameStatus = function(status){
		$log.info('updateGameStatus');
		
		var res = $http.put('/arithmetic/v1/game/multi/game?status='+status);
		
		res.success(function(data, status, headers, config) {
			$scope.game.players = data;	
			$log.info(data);
		});
		
		res.error(function(data, status, headers, config) {
			$log.error( JSON.stringify({data: data}));
			$scope.game.created= false;
		});
	}	
	
	$scope.getGames();
})


.controller('arithmetic-player-multi-ctrl', function($http, $scope, $log, $interval){
	
	$scope.player = {
		name: ""
	}
	
	$scope.games = [];
	
	$scope.problem = "AWAITED";
	
	$scope.getGames = function(){
		var res = $http.get('/arithmetic/v1/game/multi');
		
		res.success(function(data, status, headers, config) {
			$log.info(data);
			$scope.games = data;
			if($scope.games[0].state == "STARTED"){
				$scope.stopGettingGame();
				$scope.startGame();
			}
		});
		res.error(function(data, status, headers, config) {
			$log.error( JSON.stringify({data: data}));
			$scope.game.created= false;
		});
	}
	
	$scope.joinGame = function(index){
		var res = $http.get('/arithmetic/v1/game/multi/join?playerName='+$scope.player.name+'&gameName='+$scope.games[0].name);
		res.success(function(data, status, headers, config) {
			console.log(data);
		});
		res.error(function(data, status, headers, config) {
			$log.error( JSON.stringify({data: data}));
			$scope.game.created= false;
		});
	}
	
	$scope.startGame = function(index){
		var res = $http.get('/arithmetic/v1/game/multi/start?playerName='+$scope.player.name+'&gameName='+$scope.games[0].name);
		res.success(function(data, status, headers, config) {
			console.log(data);
		});
		res.error(function(data, status, headers, config) {
			$log.error( JSON.stringify({data: data}));
			$scope.game.created= false;
		});
	}
	
	$scope.sendResult = function(index){
		var res = $http.put(
			'/arithmetic/v1/game/multi/start?'
				+'playerName='+$scope.player.name
				+'&gameName='+$scope.games[0].name)
				+'&isCorrect='+$scope.problem.isCorrect
				+'&points='+$scope.problem.points;
		res.success(function(data, status, headers, config) {
			console.log(data);
		});
		res.error(function(data, status, headers, config) {
			$log.error( JSON.stringify({data: data}));
			$scope.game.created= false;
		});
	}
	
	var stopGettingGame = $interval(
		function() {
			$scope.getGames();
		}, 3000);
	
	$scope.stopGettingGame = function() {
        if (angular.isDefined(stopGettingGame)) {
          $interval.cancel(stopGettingGame);
          stopGettingGame = undefined;
        }
      };
	
	
	 $scope.$on('$destroy', function() {
		 $scope.stopGettingGame();
     });
    
	
    $scope.getGames();
})

.controller('player-game-ctrl', function($http, $scope, $rootScope, $interval, $timeout, $log, gameServices){
	$scope.game = "WAIT";
	
	$scope.gameStatus = {
		problemCount: 0,
		corrects: 0,
		points: 0,		
		time: 60,
		problemTime: 0
	};
	
	$scope.response = function(answer){
		if(answer == $scope.game.problems[$scope.gameStatus.problemCount].correctAnswer){
			$scope.gameStatus.corrects++;
			$scope.gameStatus.points += 15 - $scope.gameStatus.problemTime;
			if($scope.gameStatus.problemTime < 5){
				$scope.gameStatus.time += 3;
			}
		}
		$scope.gameStatus.problemTime = 0;
		$scope.gameStatus.problemCount++;
	};
	
	$scope.sendScoring = function(){
		var res = $http.put('/arithmetic/v1/game/multi/scoring', $scope.gameStatus)
	};
	
	
	
	updateTime = function(){
		console.log("updating");
		$scope.gameStatus.time--;
		$scope.gameStatus.problemTime++;
		
		if($scope.gameStatus.time <= 0){
			$log.info("GAME OVER");
			$interval.cancel(stopGame);
		}
	}
	var stopGame;
	$scope.begin = function(){
		$interval.cancel(stopGame);
		stopGame = $interval(updateTime, 1000);	
	}
	
	
//	$scope.getGame();
	gameServices.games.get().then(function(data){
		$scope.game = data[0];
	});
	$scope.begin();
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
})

.factory('gameServices', function($http, $log){
	var path = "/arithmetic/v1/";
	
	var gameServices = {
		games : {
			get: function(){
				var res = $http.get(path+'games').then(function(response){
			        $log.debug(response);
			        return response.data;
				});
				return res;	
			}
		},
		game : {
			get : function(gameId){
				var res = $http.get(path+'game'+"&gameId="+gameId).then(function(response){
			        $log.debug(response);
			        return response.data;
				});
				return res;
			},
			post : function(gameConfig){
				var res = $http.post(path+'game', gameConfig).then(function(response){
			        $log.debug(response);
			        return response.data;
				});
				return res;
			},
			put : function(updatedGame){
				var res = $http.put(path+'game', updatedGame).then(function(response){
			        $log.debug(response);
			        return response.data;
				});
				return res;
			}
			
		},
		player : {
			post : function(gamePlayer){
				var res = $http.post(path+'player', gamePlayer).then(function(response){
			        $log.debug(response);
			        return response.data;
				});
				return res;
			},
			put : function(updatedPlayer){
				var res = $http.put(path+'player', updatedPlayer).then(function(response){
			        $log.debug(response);
			        return response.data;
				});
				return res;
			}
		}	
	};
		
	return gameServices;	
});