(function() {
	'use strict';

	define(['js/controllers/testController', 'js/services/problemServices', 'ng-dialog' ], function(controllers) {
		var app = angular.module('app', ['controllers', 'services', 'ngDialog']);
	
			
		app.directive("mathjaxBind", function() {
		    return {
		        restrict: "A",
		        controller: ["$scope", "$element", "$attrs", function($scope, $element, $attrs) {
		            $scope.$watch($attrs.mathjaxBind, function(value) {
		                var $script = angular.element("<script type='math/tex'>")
		                    .html(value == undefined ? "" : value);
		                $element.html("");
		                $element.append($script);
		                MathJax.Hub.Queue(["Reprocess", MathJax.Hub, $element[0]]);
		            });
		        }]
		    };
		});
		
		
		app.directive('bsDropdown', function ($compile) {
		    return {
		        restrict: 'E',
		        scope: {
		            items: '=dropdownData',
		            doSelect: '&selectVal',
		            selectedItem: '=preselectedItem'
		        },
		        link: function (scope, element, attrs) {
		            var html = '';
		            switch (attrs.menuType) {
		                case "button":
		                    html += '<div class="btn-group"><button class="btn button-label btn-default">Action</button><button class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>';
		                    break;
		                default:
		                    html += '<div class="dropdown"><a class="dropdown-toggle" role="button" data-toggle="dropdown"  href="javascript:;">Dropdown<b class="caret"></b></a>';
		                    break;
		            }
		            html += '<ul class="dropdown-menu"><li ng-repeat="item in items"><a tabindex="-1" data-ng-click="selectVal(item)">{{item.name}}</a></li></ul></div>';
		            element.append($compile(html)(scope));
		            for (var i = 0; i < scope.items.length; i++) {
		                if (scope.items[i].id === scope.selectedItem) {
		                    scope.bSelectedItem = scope.items[i];
		                    break;
		                }
		            }
		            scope.selectVal = function (item) {
		                switch (attrs.menuType) {
		                    case "button":
		                        $('button.button-label', element).html(item.name);
		                        break;
		                    default:
		                        $('a.dropdown-toggle', element).html('<b class="caret"></b> ' + item.name);
		                        break;
		                }
		                scope.doSelect({
		                    selectedVal: item.id
		                });
		            };
		            scope.selectVal(scope.bSelectedItem);
		        }
		    };
		});
		
		
		
		
		
		
		
		
		
		
	});

})();
