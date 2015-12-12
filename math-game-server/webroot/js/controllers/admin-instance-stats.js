define(["angular", "js/controllers", 'js/services/service', 'js/services/game-services', 'lib/sockjs-1.0.3'], function(angular, controllers) {
    controllers.controller('adminInstanceStats', 
    	['$scope', 'game', '$location', '$interval', '$log', '$routeParams',
    	function($scope, game, $location, $interval, $log, $routeParams) {
        
        console.log("adminInstanceStats "+ $routeParams.instanceId);
        
        $scope.firstPosition = {
            label : "",
            value : 0
        };

		$scope.instance = {
        	instanceId : $routeParams.instanceId
        };

        $scope.sock;

        $scope.showStats = function(instanceId) {
            $log.info(instanceId);
            $scope.instance.instanceId = instanceId;
            $scope.openSocket();
        };

        
        $scope.openSocket = function() {
            $scope.sock = new SockJS('http://localhost:8081/socketjs/');
            $scope.sock.onopen = function() {
                $log.info('open');
                $scope.$apply(function() {
                    $scope.instance.status = "SUCCESS";
                });
                $scope.sendMessage($scope.instance.instanceId);
            };
        
            $scope.sock.onmessage = function(e) {
                $log.info('message', e.data);
                $scope.$apply(function() {
                    $scope.instance.data = JSON.parse(e.data);

                    $scope.candidateToFirst = {
                        "label": $scope.firstPosition.label,
                        "value": $scope.firstPosition.value
                    }

                    $scope.instance.data.players.forEach(function(player) {
                        if (typeof player.scoring != 'undefined') {
                            var newPlayer = {
                                "label": player.name,
                                "value": player.scoring.score
                            }

                            if($scope.candidateToFirst.value < newPlayer.value){
                                $scope.candidateToFirst = newPlayer;
                            }

                            $scope.data[0].values.push(newPlayer);
                        }
                    });

                    $scope.firstPosition = $scope.candidateToFirst;
                });
            };
        
            $scope.sock.onclose = function() {
                console.log('close');
            };
        }
        
        $scope.sendMessage = function(message) {
            $scope.sock.send(message);
        }
        
        $scope.closeSocket = function() {
            $scope.sock.close();
        }
        
        $scope.options = {
            chart: {
                type: 'discreteBarChart',
                height: 600,
                margin: {
                    top: 20,
                    right: 20,
                    bottom: 50,
                    left: 55
                },
                x: function(d) {
                    return d.label;
                },
                y: function(d) {
                    return d.value + (1e-10);
                },
                showValues: true,
                valueFormat: function(d) {
                    return d3.format(',.0f')(d);
                },
                duration: 500,
                xAxis: {
                    axisLabel: 'Jugadores'
                },
                yAxis: {
                    axisLabel: 'Puntaje',
                    axisLabelDistance: -10
                }
            }
        };
        
        $scope.data = [{
            key: "Cumulative Return",
            values: []
        }]
        
		$scope.showStats($scope.instance.instanceId);
    }]);
});