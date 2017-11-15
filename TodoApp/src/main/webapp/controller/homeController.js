var app = angular.module('TodoApp');

app.controller('homeController', function($scope, homeService,$location){
	$scope.loginUser = function(){
		var a=homeService.loginUser($scope.user,$scope.error);
			a.then(function(response) {
				console.log("Login Sucessfull");
				
			},function(response){

			});
	}
});