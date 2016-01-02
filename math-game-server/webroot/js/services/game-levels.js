'use strict'
define(["angular", "js/services"], function(angular, services){
	services.factory('gameLevels', ['$log', function($log){				
	    var GAME_OVER = false;
        var gameInstance = {};
        var actualProblem;

        var next = function(){
            $log.info("next");
            
            if(actualProblem == undefined){            
                actualProblem = gameInstance.levels[0][0];
            }else{
                if(order.next()){
                    actualProblem = gameInstance.levels[order.level][order.problem];
                }else{               
                }
            }
            $log.info("actualProblem: ");
            $log.info(actualProblem);
            return actualProblem;
        }

        var getActualProblem = function(){
            return actualProblem;
        }

        var correctAnswerList = [];
        var alreadyAnswer = [];
        var checkAnswer = function(answer){
            if(correctAnswerList.length == 0 || correctAnswerList == undefined){
                fillCorrectAnswerList();
            }

            return getStatusProblem(verifyAnswer(answer));
        }

        var verifyAnswer = function(answer){
            //$log.info("verifyAnswer : "+answer);
            //$log.info(correctAnswerList);

            for (var i = correctAnswerList.length - 1; i >= 0; i--) {                
                
                if(answer == correctAnswerList[i]){
                    //$log.warn(answer + " == " + correctAnswerList[i] + " = true");
                    alreadyAnswer.push(correctAnswerList.splice(i, 1));
                    return true;
                }else{
                  //  $log.warn(answer + " == " + correctAnswerList[i] + " = false");
                }
                
            };
            
            return false;
        }

        function getStatusProblem(isLastAnswerCorrect){
            var statusProblem = {
                isCorrect : false,
                isProblemEnds : true
            }

            if(isLastAnswerCorrect){
                statusProblem.isCorrect = true;
                if(correctAnswerList.length != 0){
                    statusProblem.isProblemEnds = false;
                }
            }
            $log.warn(statusProblem);
            return statusProblem;
        }


        var setGame = function(game){
            gameInstance = game;
        }

        // Devuelve true si continúa el juego y false si ya no hay más problemas.
        var nextOrder = function(){
            $log.info("nextOrder");
            $log.info(order);
            alreadyAnswer = [];
            correctAnswerList = [];

            if(isLastProblemForLevel(order.problem)){
                if(isLastLevelForGame(order.level)){
                    order.gameOver = true;
                }else{
                    order.level++;
                    order.problem = 0;
                }
            }else{ // Hay más problemas en el nivel
                order.problem++;
            }
            $log.info(order);
            return !order.gameOver;
        }       

        function isLastProblemForLevel(problemNumber){
            return problemNumber == gameInstance.levels[order.level].length -1;
        } 

        function isLastLevelForGame(levelNumber){
            return levelNumber == gameInstance.levels.length - 1;
        }

        function fillCorrectAnswerList(){
            $log.info("fillCorrectAnswerList");
            alreadyAnswer = [];
            correctAnswerList = [];
            actualProblem.correctAnswer.forEach(function(answer){
                correctAnswerList.push(answer);
            });
            $log.info(correctAnswerList);
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
            order : order,
            checkAnswer : checkAnswer,
            getActualProblem : getActualProblem
        }

        return game;

	}]);
})
