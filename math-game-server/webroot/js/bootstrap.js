(function() {
    'use strict';

    require(['angular'], function(angular) {
        require(['app'], function() {
            angular
                .element(document)
                .ready(function() {
                    angular.bootstrap(document, ['app']);
                });
        });
    });
})();
