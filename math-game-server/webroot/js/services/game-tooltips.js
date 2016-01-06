'use strict'
define(["angular", "js/services"], function(angular, services){
	services.factory('gameTooltips', ['$log', '$sce', function($log, $sce){				
        
        var styleLevels = [
            'tooltip-nook',
            'tooltip-pass',
            'tooltip-ok',            
            'tooltip-good',
            'tooltip-great'
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
            }
        };


        var showHideTooltips = function(mustShow){
            if(mustShow){
                tooltips.show = true;
                //tooltips.time.show = true;
            }else{
                tooltips.show = false;
                //tooltips.time.show = false;
           }
        }

        var setScoreTooltip = function(score){
            tooltips.score.message = $sce.trustAsHtml('<h2>'+score+'</h2>');
        }

        var setTimeTooltip = function(extraTime){
            tooltips.time.message = $sce.trustAsHtml('<h2>'+extraTime+'</h2>');
        }

        var setResponseLevel = function(responseLevel){
            tooltips.tooltipClass = styleLevels[responseLevel];
        }

        var gameTooltips = {
            tooltips : tooltips,
            showHideTooltips : showHideTooltips,
            setScoreTooltip : setScoreTooltip,
            setTimeTooltip : setTimeTooltip,
            setResponseLevel : setResponseLevel
        }

        return gameTooltips;


	}]);
})
