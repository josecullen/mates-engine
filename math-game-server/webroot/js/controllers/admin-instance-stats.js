define(["angular", "js/controllers",'js/services/game-services', 'lib/sockjs-1.0.3.min'], function(angular, controllers) {
    controllers.controller('adminInstanceStats', 
    	['$scope', 'game', '$location', '$interval', '$log', '$routeParams', '$timeout',
    	function($scope, game, $location, $interval, $log, $routeParams, $timeout) {
        
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
            $scope.sock = new SockJS('http://localhost:10000/socketjs/');
            $scope.sock.onopen = function() {
                $log.info('open');
                $scope.$apply(function() {
                    $scope.instance.status = "SUCCESS";
                });
                $scope.sendMessage($scope.instance.instanceId);
            };
            
            $scope.sock.onmessage = function(e) {
                $log.info('message', e.data);
                $scope.data = [{
                    key: "Cumulative Return",
                    values: []
                }]
                $scope.$apply(function() {
                    $scope.instance.data = JSON.parse(e.data);

                    $scope.candidateToFirst = {
                        "label": $scope.firstPosition.label,
                        "value": $scope.firstPosition.value
                    }

                    $scope.instance.data.forEach(function(player) {
                        if (typeof player.score != 'undefined') {
                            var newPlayer = {
                                "label": player.name,
                                "value": player.score
                            }

                            if($scope.candidateToFirst.value < newPlayer.value){
                                $scope.candidateToFirst = newPlayer;
                            }

                            $scope.data[0].values.push(newPlayer);
                        }
                    });

                    if($scope.candidateToFirst.label != $scope.firstPosition.label){
                        
                        $scope.firstPositionNameClass = 'change-first-position';
                        $scope.firstPositionScoreClass = 'change-first-position';
                        
                        $timeout(function(){
                            $scope.firstPosition = $scope.candidateToFirst;
                            $scope.firstPositionNameClass = '';
                            $scope.firstPositionScoreClass = '';
                        }, 750);

                    }else if($scope.candidateToFirst.value != $scope.firstPosition.value){
                        $scope.firstPositionScoreClass = 'change-first-position';
                        
                        $timeout(function(){
                            $scope.firstPosition = $scope.candidateToFirst;
                            $scope.firstPositionScoreClass = '';
                        }, 750);
                    }
                    
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
                height: 550,
                margin: {
                    top: 20,
                    right: 100,
                    bottom: 50,
                    left: 100
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
                    axisLabel: 'Jugadores',
                    axisLabelDistance: 5
                },
                yAxis: {
                    axisLabel: 'Puntaje',
                    axisLabelDistance: 25
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