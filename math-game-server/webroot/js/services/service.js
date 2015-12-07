
define(["angular", "js/services"], function(angular, services){

    services.factory('write', [
       function(){
            return function(text){
            	console.log(text);
            };
       }
    ]);
})