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
	
		$scope.showSidebar=function(){
		if($scope.width=='0px'){
			$scope.width='200px';
			$scope.leftmargin="300px";
		}else{
			$scope.width='0px';
			$scope.leftmargin="0px";
		}
		
	}
});
