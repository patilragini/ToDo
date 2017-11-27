var todoApp = angular.module('TodoApp');
todoApp.controller('dummycontroller',function($location,dummyservice){
	var dummyservice=dummyservice.service();
	
	dummyservice.then(function(response){
		localStorage.setItem('login',response.data.message);
		$location.path('/home');
	},function(response){
		
	});
	dummyservice();
});