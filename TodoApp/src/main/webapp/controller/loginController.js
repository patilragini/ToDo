var app = angular.module('TodoApp');
app.controller('loginController', function($scope, loginService,$location){
	$scope.loginUser = function(){
		var a=loginService.loginUser($scope.user,$scope.error);
			a.then(function(response) {
				console.log(response.data);
				$location.path('/home')
			},function(response){
				$scope.error=response.data;
				console.log(response.data);
			});
	}
});