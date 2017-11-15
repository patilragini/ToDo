var toDo = angular.module('TodoApp');

toDo.factory('registerService', function($http, $location) {

	var details = {};
	
	details.registerUser = function(user) {
		return $http({
			method : "POST",
			url : 'register',
			data : user
		})
	}
	return details;

});