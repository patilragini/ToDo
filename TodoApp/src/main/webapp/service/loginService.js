var toDo = angular.module('ToDoApp');

toDo.factory('loginService', function($http, $location) {

	var details = {};
	
	details.loginUser = function(user) {
		return $http({
			method : "POST",
			url : 'Login',
			data : user
		})
	}
	return details;

});