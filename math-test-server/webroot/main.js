(function() {
	'use strict';

	require
			.config({
				paths : {
					// General dependencies
					'angular' : 'lib/angular/angular',
					'angular-route' : 'lib/angular/angular-route',
					'angular-animate' : 'lib/angular/angular-animate',
					'jquery-ui' : 'lib/jquery/jquery-ui-1.11.4-custom/jquery-ui.min',
					'bootstrap' : 'lib/bootstrap/js/bootstrap.min',
					'ng-dialog' : 'lib/ng-dialog/ngDialog',
					// Local dependencies
					'app' : 'js/app'
				},

				shim : {
					'angular' : {
						exports : 'angular'
					},
					'angular-animate': {
						deps : ['angular']
					},
					'ng-dialog': {
						deps : ['angular']
					},
					'jquery-ui' : {
						deps : [ '/lib/jquery/jquery-ui-1.10.4.custom/js/jquery-1.10.2.js' ]
					},
					'bootstrap' : {
						deps : [ '/lib/jquery/jquery-ui-1.10.4.custom/js/jquery-1.10.2.js' ]
					}					
				}
				
			});

	require([ 'js/bootstrap' ]);
})();
