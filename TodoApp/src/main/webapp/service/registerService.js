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
	
	
	
	
	details.service=function(url,method,user,token){
		return $http({
		    method: method,
		    url: url,
		    data:user,
		    headers: {
		        'login': token
		    }
		
		});
	}
	return details;

});