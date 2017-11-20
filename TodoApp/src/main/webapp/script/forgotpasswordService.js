var toDoApp = angular.module('TodoApp');

toDoApp.factory('forgotpasswordService', function($http, $location) {
	var reset = {};

	reset.sendLinkToEmail = function(user) {
		return $http({
			method : "POST",
			url : 'forgotpassword',
			data : user,
//			setheaders: {
//			    'reset': reset }
//		 headers: new HttpHeaders().set('reset', 'reset'),
		 
		})
		
	}

	reset.resetPassword = function(user) {
		console.log(user);
		return $http({
			method : "POST",
			url : 'resetpassword',
			data : user
		})
	}
	return reset;
});