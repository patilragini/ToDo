var app = angular.module('TodoApp');

app.controller('loginController', function($scope, loginService, $location) {
	$scope.loginUser = function() {
		var result = loginService.loginUser($scope.user, $scope.error);
		result.then(function(response) {
			//console.log("home page");
			// console.log(response.data);
//			localStorage.setItem('login',login);
			console.log(response.headers('login'));
			localStorage.setItem('login',response.headers('login'));
			$location.path("/home");
			//$state.go("home");
			
		}, function(response) {
			alert("Eroor");
			//console.log("Home page error");
		});
	}
});