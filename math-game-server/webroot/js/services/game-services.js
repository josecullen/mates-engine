define(["angular", "js/services"], function(angular, services){
	services.factory('game', ['$http', '$log', function($http, $log){
		var path = "/arithmetic/v1/";
		var allPath = path+"all";
		var onePath = path+"one";
		var playerAllPath = path+"player/all";
		var playerOnePath = path+"player/one";
		var instanceAllPath = path+"instance/all";
		var instanceOnePath = path+"instance/one";

		
		var thenFunction = function(response){
	        return response.data;
		};
		
		var gameServices = {			
			all : {				
				get : function(){
					return $http.get(allPath).then(thenFunction);					
				},
				del : function(){
					return $http.delete(allPath).then(thenFunction);					
				}
			},
			one : {
				get : function(gameId){
					return $http.get(onePath+"?gameId="+gameId).then(thenFunction);					
				},
				post : function(gameConf){
					console.log("gameConf "+gameConf);
					return $http.post(onePath, gameConf);					
				},
				put : function(){
					return $http.put(onePath).then(thenFunction);					
				},
				del : function(gameId){
					return $http.delete(onePath+"?gameId="+gameId).then(thenFunction);					
				}
			},
			player :{
				all : {
					get : function(){
						return $http.get(playerAllPath).then(thenFunction);					
					},
					del : function(){
						return $http.delete(playerAllPath).then(thenFunction);					
					}
				},
				one : {
					get : function(){
						return $http.get(playerOnePath).then(thenFunction);					
					},
					post : function(params){
						return $http.post(playerOnePath, params).then(thenFunction);					
					},
					put : function(params){
						return $http.put(playerOnePath, params).then(thenFunction);					
					},
					del : function(){
						return $http.delete(playerOnePath).then(thenFunction);					
					}
				}
			},
			instance: {
				all : {
					get : function(){
						return $http.get(instanceAllPath).then(thenFunction);					
					},
					del : function(){
						return $http.delete(instanceAllPath).then(thenFunction);					
					}
				},
				one : {
					get : function(){
						return $http.get(instanceOnePath).then(thenFunction);					
					},
					post : function(gameConfig){
						console.log(gameConfig);
						return $http.post(instanceOnePath,gameConfig).then(thenFunction);					
					},
					put : function(){
						return $http.put(instanceOnePath).then(thenFunction);					
					},
					del : function(){
						return $http.delete(instanceOnePath).then(thenFunction);					
					}
				}
			}
		};
		
		return gameServices;	
    	
    }]);
})
