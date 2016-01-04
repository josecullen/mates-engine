define(["angular",'js/services', 'js/services/game-services'], function(angular, services){
	
    services.factory('gameInstance', ['$log', 'game', function($log,game){
        
        $log.info("gameInstance");

        var instance = { status : "NOT_SELECTED" };
        var status = {};

        var joinGame = function(name, selectedInstance){
            
            resetStatus();
            
            var params = {"playerName" : name, "instance" : selectedInstance };
            
            game.player.one.post(params).then(function(response){
            
                if(response.status == 'SUCCESS'){                    
                    if(selectedInstance.type == "MULTI_INSTANCE_GAME"){
                        $log.debug("MULTI_INSTANCE_GAME");
                        
                        game.instance.one.get(selectedInstance.gameId, selectedInstance._id, "RANDOM")
                        .then(function(instanceResponse){
                            instance = instanceResponse; 
                            instance.type = "MULTI_INSTANCE_GAME";
                            configGame(response.id);
                        });
                    
                    }else{
                        instance = selectedInstance;
                        configGame(response.id);
                    }
                    return true;
                }
                return false;
            });         
        };

        function resetStatus(){
            status = {
                id : "",
                problemStatus: "SHOW_PROBLEM",
                instanceId : "",
                lives: [1,2,3],
                problemCount: 0,
                corrects: 0,
                incorrects: 0,      
                score: 0,       
                time: 60,
                problemTime: 0,
                levelCount: 0
            };
        }

        function configGame(id){
            instance.status = 'SUCCESS';
            status.id = id;
            status.instanceId = instance._id;
        }

        var gameInstance = {
            instance : instance,
            status : status,
            join : joinGame
        };

        return gameInstance;

	}]);

})
