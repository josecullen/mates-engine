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
					'app' : {
						deps : [ 'angular', 'js/controllers',
								'js/services/service',
								'js/services/game-services',
								'js/services/game-handler-service',
								'js/controllers/admin-ctrl',
								'js/controllers/admin-game-list-ctrl',
								'js/controllers/admin-instances-ctrl',
								'js/controllers/player-ctrl',
								'angular-route', 'bootstrap' ]
					}
				}
			});

	require([ 'js/bootstrap' ]);
})();
