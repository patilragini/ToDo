var app = angular.module('TodoApp');

app.controller('homeController', 
		function($scope, loginService, $location) {
	$scope.loginUser = function() {
		var result = loginService.loginUser($scope.user, $scope.error);
		result.then(function(response) {
			//console.log("Login Sucessfull");
			$state.go("home");
		}, function(response) {
			alert("Eroor");
			//console.log("Home page error");			
		});
	}
	
	
});
