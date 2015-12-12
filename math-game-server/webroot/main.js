(function() {
	'use strict';

	require
			.config({
				paths : {
					// General dependencies
					'angular' : 'lib/angular/angular',
					'angular-route' : 'lib/angular/angular-route',
					'jquery-ui' : 'lib/jquery/jquery-ui-1.11.4-custom/jquery-ui.min',
					'bootstrap' : 'lib/bootstrap/js/bootstrap.min',
					'angular-nvd3' : 'lib/angular-nvd3-1.0.5/dist/angular-nvd3',
					'd3' : 'lib/d3',
					'nvd3' : 'lib/nv.d3',
//					'sockJS' : 'lib/sockjs-1.0.3',
					
					// Local dependencies
					'app' : 'js/app'
				},

				shim : {
					'angular' : {
						exports : 'angular'
					},
					'jquery-ui' : {
						deps : [ '/lib/jquery/jquery-ui-1.10.4.custom/js/jquery-1.10.2.js' ]
					},
					'bootstrap' : {
						deps : [ '/lib/jquery/jquery-ui-1.10.4.custom/js/jquery-1.10.2.js' ]
					},
					
					
					'nvd3' : {
						deps : ['d3']
					},
					'angular-nvd3' : {
						deps : ['angular', 'nvd3', 'd3']
					},
					
					'app' : {
						deps : ['angular', 
						        'nvd3',
						        'd3',
						        'angular-nvd3',
						        'js/controllers',
						        'lib/sockjs-1.0.3',
								'js/services/service',
								'js/services/game-services',
								'js/services/game-handler-service',
								'js/controllers/admin-ctrl',
								'js/controllers/admin-game-list-ctrl',
								'js/controllers/admin-instances-ctrl',
								'js/controllers/admin-instance-stats',								
								'js/controllers/player-ctrl',
								'angular-route', 
								'bootstrap' ]
					}
				}
			});

	require([ 'js/bootstrap' ]);
})();
