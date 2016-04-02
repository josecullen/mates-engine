'use strict'
define(["angular", "js/services"], function(angular, services){
	services.factory('gameTooltips', ['$log', '$sce', '$timeout', function($log, $sce, $timeout){				
        
        var styleLevels = [            
            'tooltip-great',
            'tooltip-good',            
            'tooltip-ok',  
            'tooltip-pass',
            'tooltip-nook'
        ];

        var responsTypes = [
            "¡Genial!",
            "Muy bien",
            "Bien",            
            "Muy lento",
            "Incorrecto"
        ];

        var tooltips = {            
            tooltipClass : 'tooltip-ok',
            show : false,
            score : {
                message : $sce.trustAsHtml('<h2></h2>')
            },
            time : {
                message : $sce.trustAsHtml('<h2></h2>')
            },
            lives : {
                message : $sce.trustAsHtml('<h2>-1</h2>')
            },
            responseStatus :{
              message : $sce.trustAsHtml('<h2></h2>')  
            }
        };


        var showHideTooltips = function(mustShow){
            if(mustShow){
                tooltips.show = true;
                $timeout(function(){
                    showHideTooltips(false);
                },1500);
            }else{
                tooltips.show = false;
           }
        }

        var setScoreTooltip = function(score){
            tooltips.score.message = $sce.trustAsHtml('<h2>'+score+'pts.</h2>');
        }

        var setTimeTooltip = function(extraTime){
            tooltips.time.message = $sce.trustAsHtml('<h2>'+extraTime+'s.</h2>');
        }

        var setResponseStatusTooltip = function(extraName){
            tooltips.responseStatus.message = $sce.trustAsHtml('<h2>'+extraName+'</h2>');
        }

        var setResponseLevel = function(responseLevel){
            tooltips.tooltipClass = styleLevels[responseLevel];
           // setResponseStatusTooltip(responseLevel);
        }

        var setTooltip = function(correct, scoreConfig, extraIndex){
            if(correct){
                var extraScore = 0;
                var extraTime = 0;       
                var extraName = "Muy lento";         
                if(extraIndex != -1){
                    extraScore = scoreConfig.extras[extraIndex].extraScore;
                    extraTime = scoreConfig.extras[extraIndex].extraTime; 
                    extraName = scoreConfig.extras[extraIndex].name;
                }else{
                    extraIndex = scoreConfig.extras.length;
                }
                setScoreTooltip(scoreConfig.baseScore+extraScore);
                setTimeTooltip(extraTime);
                setResponseLevel(extraIndex);                    
                setResponseStatusTooltip(extraName);
            }else{
                setScoreTooltip(0);
                setTimeTooltip(0);
                setResponseLevel(responsTypes.length-1);
                setResponseStatusTooltip("Incorrecto");
            }
        }

        var gameTooltips = {
            tooltips : tooltips,
            setTooltip : setTooltip,
            showHideTooltips : showHideTooltips,
            setScoreTooltip : setScoreTooltip,
            setTimeTooltip : setTimeTooltip,
            setResponseLevel : setResponseLevel,
            setResponseStatusTooltip : setResponseStatusTooltip
        }

        return gameTooltips;


	}]);
})
