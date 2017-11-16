var app = angular.module('TodoApp', ['ui.router']);

app.config([ '$stateProvider', '$urlRouterProvider',
		function($stateProvider, $urlRouterProvider) {

			$stateProvider.state('login', {
				url : '/login',
				templateUrl : 'pages/Login.html',
				controller : 'loginController'
			});
		
			$stateProvider.state('register', {
				url : '/register',
				templateUrl : 'pages/RegistrationPage.html',
				controller : 'registerController'
			});

			$stateProvider.state('home', {
				url : '/home',
				templateUrl : 'pages/home.html',
				controller : 'homeController'
			});
			
			
			$urlRouterProvider.otherwise('login');
			
}]);