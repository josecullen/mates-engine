define(["js/services"], function(services){
	
	services.factory('problemServices', ['$http', '$log', function($http, $log){
		var path = "/math/test/v1/problem";

		var returnData = function(response){
	        return response.data;
		};

		var problemServices = {
			

			get: function(params){
				$log.info("problemServices.get \t params:");
				$log.info(params);
				return $http
					.get(path+
						"?type="+params.type+
						"&form="+escape(params.form)+
						"&min="+params.min+
						"&max="+params.max+
						"&sign="+params.sign+
						"&div="+params.div+
						"&operations="+params.operations)
					.then(returnData);					
			}
		}

		return problemServices;	
    	
    }]);
})
