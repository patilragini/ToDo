var app = angular.module('TodoApp');


app.controller('registerController', function($scope, registerService,$location){
	$scope.registerUser = function(){
		var a=registerService.registerUser($scope.user,$scope.error);
			a.then(function(response) {
				console.log("register done scussfully");
				alert("register done scussfully check mail to activate account");
				$location.path('/home')
			},function(response){
				alert("registration falied");
				console.log("in Register controller");
			});
	}
});
