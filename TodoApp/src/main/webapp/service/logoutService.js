var toDo = angular.module('TodoApp');

toDo.factory('logoutService', function($http, $location) {

	$scope.logoutUser = function(user) {
		return $http({
			method : "POST",
			url : 'logout',
			data : user
		})
	}
	
});