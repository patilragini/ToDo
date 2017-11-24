var toDo = angular.module('TodoApp');

toDo.factory('logoutService', function($http, $location) {

	var details = {};
	
	details.logoutUser = function(user) {
		return $http({
			method : "POST",
			url : 'logout',
			data : user
		})
	}
	return details;

});