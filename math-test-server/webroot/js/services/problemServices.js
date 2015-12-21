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
				if(params.type == "SIMPLE"){
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
				}else{
					return $http.get(path+
						"?type="+params.type+							
						"&a="+JSON.stringify(params.a)+
						"&x1="+JSON.stringify(params.x1)+
						"&x2="+JSON.stringify(params.x2)+
						"&x3="+JSON.stringify(params.x3))
					.then(returnData);						
				}
								
			}
		}

		return problemServices;	
    	
    }]);
})
