var app = angular.module('TodoApp', ['ui.router', 'ngSanitize']);

app.config([ '$stateProvider', '$urlRouterProvider',
		function($stateProvider, $urlRouterProvider) {

			$stateProvider.state('login', {
				url : '/login',
				templateUrl : 'pages/Login.html',
				controller : 'loginController'
			});
			
}]);