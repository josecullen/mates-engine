'use strict';

define(['js/controllers', 'ng-dialog'], function( controllers){
    /* Controllers Modules */
    controllers.controller('testController',[ '$scope', '$log', 'problemServices', 'ngDialog',
    	function($scope, $log, problemServices, ngDialog){
    	
    	$scope.test = {
    		title: "Título",
    		subtitle: "Subtítulo",
    		problems: []    
    	};
    	$scope.testInstanceIndex = 0;
    	$scope.testInstances = [];
    	$scope.selectedTestInstance = {};
    	$scope.selectedProblem = {};




    	$scope.addProblem = function(){
    		var problem = {
    			consign : "Consigna",
    			exercises: [],
    			addExercise : function(){
		    		$scope.exercise = {
		    			instance: {
		    				problemExpression : "problem"
		    			},
		    			config: {
		    				type: "SIMPLE",
				    		form: "a - b",
				    		min: 0,
				    		max: 10,
				    		sign: 0.5,
				    		div: 2,
				    		operations: "*-",
				    		a: { min: 1, max: 4, sign: 0.2, div: 1 },
				    		x1: { min: 1, max: 4, sign: 0.2, div: 1 },
				    		x2: { min: 1, max: 4, sign: 0.2, div: 1 },
				    		x3: { min: 1, max: 4, sign: 0.2, div: 1 }
		    			}		    		
		       		};
		       		this.exercises.push($scope.exercise);
    			}
    		};

    		$scope.selectedTestInstance.problems.push(problem);
    		$scope.selectedProblem = problem;
    	};

    	
    	$scope.setSelected = function(problem) {
    		$log.info("change problem selection");
    		$scope.selectedProblem = problem;
    	}
    	

       	$scope.refreshExercise = function(exercise){
       		problemServices.get(exercise.config).then(function(response){
       			exercise.instance = response;
       		});
       	}


		$scope.exerciseConfig = function (exercise) {
			ngDialog.open({
				template: 'firstDialog',
				controller: ['$scope', function($scope) {
				 	$scope.exercise = exercise;


				}],
				className: 'ngdialog-theme-default ngdialog-theme-custom'
			});
		};

		$scope.addTestInstance = function(){
			$scope.newInstance = angular.copy($scope.test);
			$scope.testInstances.push($scope.newInstance);
		}

		$scope.refreshTestInstance = function(testInstance){
			testInstance.problems.forEach(function(problem){
				problem.exercises.forEach(function(exercise){
					$scope.refreshExercise(exercise);
				});
			});
		}

		$scope.plusTestInstanceIndex = function(){
			$log.info($scope.testInstanceIndex);
			$scope.testInstanceIndex = $scope.testInstanceIndex +1;
			$scope.selectedTestInstance = $scope.testInstances[$scope.testInstanceIndex];
			$log.info($scope.testInstanceIndex);
		}

		$scope.minusTestInstanceIndex = function(){
			$log.info($scope.testInstanceIndex);
			$scope.testInstanceIndex = $scope.testInstanceIndex -1;
			$scope.selectedTestInstance = $scope.testInstances[$scope.testInstanceIndex];
			$log.info($scope.testInstanceIndex);
		}


	   	$scope.testInstances.push($scope.test);
		$scope.selectedTestInstance = $scope.test;
    	$scope.addProblem();

    }]);

});
