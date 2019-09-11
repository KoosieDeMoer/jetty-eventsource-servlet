'use strict';
angular.module("InterceptorManagement", []).controller("InterceptorController",
		function($scope, $http) {


			$scope.message = {};

			// Now load the data from server
			_refreshNodeData();



			/* Private Methods */
			// HTTP GET- get all countries collection
			function _refreshNodeData() {
				$http({
					method : 'GET',
					url : '/Interceptor/read'
				}).then(function successCallback(response) {
					$scope.message = response.data;
				}, function errorCallback(response) {
					_error(response);
				});
			}

			function _success(response) {
				$scope.errorMessage = false;
  			 document.body.style.cursor='default';
			}

			function _error(response) {
				$scope.errorMessage = response.data;
				 document.body.style.cursor='default';
			}
			;
		});
