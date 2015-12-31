define(["angular", "js/services"], function(angular, services){
	services.factory('gameLevels', ['$log', function($log){				
	   
        var gameInstance = {};
        var actualProblem;

        var next = function(){
            if(actualProblem == undefined){
                actualProblem = gameInstance.levels[0][0];
            }else{
                if(order.next()){
                    actualProblem = gameInstance.levels[order.level][order.problem];
                }else{
                    // game over
                }
            }

            return actualProblem;
        }

        var checkAnswer = function(answer){

        }

        var setGame = function(game){
            gameInstance = game;
        }

        // Devuelve true si continúa el juego y false si ya no hay más problemas.
        var nextOrder = function(){
            if(isLastProblemForLevel(order.problem)){
                if(isLastLevelForGame(order.level)){
                    order.gameOver = true;
                }else{
                    order.level++;
                    order.problem++;
                }
            }else{ // Hay más problemas en el nivel
                order.problem++;
            }

            return !order.gameOver;
        }       

        function isLastProblemForLevel(problemNumber){
            return problemNumber == gameInstance.levels[order.level].length -1;
        } 

        function isLastLevelForGame(levelNumber){
            return levelNumber == gameInstance.levels.length - 1;
        }

        var order = {
            level : 0,
            problem : 0,
            next : nextOrder,
            gameOver : false
        }

        var game = {
            setGame : setGame,
            next : next,
            checkAnswer : checkAnswer
        }

        return game;

	}]);
})
