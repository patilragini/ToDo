var app = angular.module('TodoApp', ['ui.router','ui.bootstrap', 'ngSanitize','ui.bootstrap.datepicker','toastr'/*,'interval'*/]);

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
			
			$stateProvider.state('resetPassword', {
				url : '/resetpassword/:t',
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
			
			$stateProvider.state('search', {
				url : '/archive',
				templateUrl : 'pages/search.html',
				controller : 'homeController'
			});
			
			$stateProvider.state('home', {
				url : '/home',
				templateUrl : 'pages/home.html',
				controller : 'homeController'
			});
			$stateProvider.state('dummy', {
				url : '/dummy',
				templateUrl : 'pages/dummy.html',
				controller : 'dummycontroller'
			});
			
			$urlRouterProvider.otherwise('login');
			
}]);