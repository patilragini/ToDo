var todoApp = angular.module('TodoApp');

todoApp.factory('dummyservice', function($http, $location) {
	
var data={};
data.service=function(){
	return $http({
		method:'GET',
		url:'getToken',
	})
	}
return data;
});