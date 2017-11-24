var app = angular.module('TodoApp');

app.controller('logoutController', function($scope, logoutService, $location) {
	$scope.logoutUser = function() {
		var result = logoutService.logoutUser($scope.user, $scope.error);
		result.then(function(response) {
			console.log(response.headers('login'));
			localStorage.removeItem('login');
			$location.path("/home");
	}, function(response) {
			alert("Eroor");
			});
	}
});



