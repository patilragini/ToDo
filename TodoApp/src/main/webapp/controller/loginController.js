var app = angular.module('TodoApp');

app.controller('loginController', function($scope,toastr,$interval, loginService, $location) {
	
	$scope.loginUser = function() {
		var result = loginService.loginUser($scope.user, $scope.error);
		result.then(function(response) {
			console.log(response.headers('login'));
			localStorage.setItem('login',response.headers('login'));
			toastr.success('login scussfull','');

			$location.path("/home");
			toastr.success('error in login','dsa');
	}, function(response) {
		toastr.success('error in login','dsa');
			});
	}
	
});
