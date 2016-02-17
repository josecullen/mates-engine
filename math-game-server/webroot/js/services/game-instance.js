define(["angular",'js/services', 'js/services/game-services'], function(angular, services){
	
    services.factory('gameInstance', ['$log', 'game', function($log,game){
        
        $log.info("gameInstance");

        var status = {
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

        var joinGame = function(name, selectedInstance, callback){

            resetStatus();
            
            var params = {"playerName" : name, "instance" : selectedInstance };
            
            game.player.one.post(params).then(function(response){
            
                if(response.status == 'SUCCESS'){                    
                    if(selectedInstance.type == "MULTI_INSTANCE_GAME"){
                        $log.debug("MULTI_INSTANCE_GAME");
                        
                        game.instance.one.get(selectedInstance.gameId, selectedInstance._id, "RANDOM")
                        .then(function(instanceResponse){
                            gameInstance.instance = instanceResponse; 
                            gameInstance.instance.type = "MULTI_INSTANCE_GAME";
                            configGame(response.id);
                            callback();
                        });
                    
                    }else{
                        alert("same game");
                        gameInstance.instance = selectedInstance;
                        configGame(response.id);
                        callback();
                    }
                        
                }
                
            });         

        };

        function resetStatus(){
            gameInstance.status = {
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
            gameInstance.instance.status = "SUCCESS";
            gameInstance.status.id = id;
            gameInstance.status.instanceId = gameInstance.instance._id;
        }

        var resetAll = function(){
            resetStatus();
            $log.info("reset resetAll");
            

            gameInstance.instance = {status : "NOT_SELECTED" };
            $log.info(gameInstance.instance.status);
        }

        var gameInstance = {
            instance : { status : "NOT_SELECTED" },
            status : status,
            join : joinGame,
            resetAll : resetAll
        };



        return gameInstance;

	}]);

})
