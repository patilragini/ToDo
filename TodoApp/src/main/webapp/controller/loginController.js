var app = angular.module('TodoApp');

app.controller('loginController', function($scope, loginService, $location) {
	$scope.loginUser = function() {
		var result = loginService.loginUser($scope.user, $scope.error);
		result.then(function(response) {
			//console.log("home page");
			alert("Home page");
			// console.log(response.data);
			$location.path("/home");
			//$state.go("home");
		}, function(response) {
			alert("Eroor");
			//console.log("Home page error");
		});
	}
});