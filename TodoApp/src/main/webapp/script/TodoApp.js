var app = angular.module('TodoApp', ['ui.router','ui.bootstrap', 'ngSanitize','ui.bootstrap.datepicker','toastr'/*,'interval'*/]);

app.config([ '$stateProvider', '$urlRouterProvider',
		function($stateProvider, $urlRouterProvider) {

			$stateProvider.state('login', {
				url : '/login',
				templateUrl : 'pages/Login.html',
				controller : 'loginController'
			})
			.state('logout', {
				url : '/logout',
				templateUrl : 'pages/Login.html',
				controller : 'logoutController'
			})
			.state('register', {
				url : '/register',
				templateUrl : 'pages/RegistrationPage.html',
				controller : 'registerController'
			})
			.state('forgetpassword', {
				url : '/forgetpassword',
				templateUrl : 'pages/forgetpassword.html',
				controller : 'resetController'
			})
			.state('resetPassword', {
				url : '/resetpassword/:t',
				templateUrl : 'pages/resetpassword.html',
				controller : 'resetController'
			})
			.state('trash', {
				url : '/trash',
				templateUrl : 'pages/trash.html',
				controller : 'homeController'
			})
			.state('archive', {
				url : '/archive',
				templateUrl : 'pages/archive.html',
				controller : 'homeController'
			})
			.state('search', {
				url : '/archive',
				templateUrl : 'pages/Search.html',
				controller : 'homeController'
			})
			.state('home', {
				url : '/home',
				templateUrl : 'pages/home.html',
				controller : 'homeController'
			})
			.state('dummy', {
				url : '/dummy',
				templateUrl : 'pages/dummy.html',
				controller : 'dummycontroller'
			}).state('labels', {
				url : '/{labelName}',
				templateUrl : 'pages/Labels.html',
				controller : 'homeController'
			}).state('remainder', {
				url : '/remainder',
				templateUrl : 'pages/remainder.html',
				controller : 'homeController'
			});
			
			
			$urlRouterProvider.otherwise('login');
			
}]);