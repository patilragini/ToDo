var app = angular.module('TodoApp', ['ui.router','ui.bootstrap']);

app.config([ '$stateProvider', '$urlRouterProvider',
		function($stateProvider, $urlRouterProvider) {

			$stateProvider.state('login', {
				url : '/login',
				templateUrl : 'pages/Login.html',
				controller : 'loginController'
			});
			
			$stateProvider.state('logout', {
				url : '/logout',
				templateUrl : 'pages/Login.html',
				controller : 'logoutController'
			});
		
			$stateProvider.state('register', {
				url : '/register',
				templateUrl : 'pages/RegistrationPage.html',
				controller : 'registerController'
			});

			
			$stateProvider.state('forgetpassword', {
				url : '/forgetpassword',
				templateUrl : 'pages/forgetpassword.html',
				controller : 'resetController'
			});
			
			$stateProvider.state('resetpassword', {
				url : '/resetpassword',
				templateUrl : 'pages/resetpassword.html',
				controller : 'resetController'
			});
			
			$stateProvider.state('trash', {
				url : '/trash',
				templateUrl : 'pages/trash.html',
				controller : 'homeController'
			});
			
			$stateProvider.state('archive', {
				url : '/archive',
				templateUrl : 'pages/archive.html',
				controller : 'homeController'
			});
			
			$stateProvider.state('home', {
				url : '/home',
				templateUrl : 'pages/home.html',
				controller : 'homeController'
			});
			
			$urlRouterProvider.otherwise('login');
			
}]);