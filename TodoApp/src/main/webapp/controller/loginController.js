var app = angular.module('TodoApp');

app.controller('loginController', function($scope, loginService, $location) {
	$scope.loginUser = function() {
		var a = loginService.loginUser($scope.user, $scope.error);
		a.then(function(response) {
			console.log("home page");
			alert("Home page");
			// console.log(response.data);
			$state.go("home");
		}, function(response) {
			alert("Eroor");
			console.log("Home page error");
		});
	}
});