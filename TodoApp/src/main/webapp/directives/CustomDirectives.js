var todoApp = angular.module('TodoApp');
todoApp.directive('topNavBar', function() {
		return{
			templateUrl : 'pages/TopNavBar.html'
		}

	
});
todoApp.directive('sideNavBar', function() {
	return{
		templateUrl : 'pages/SideNavBar.html'
	}
});