var todoApp = angular.module('TodoApp');

todoApp.controller('homeController', function($scope, loginService,noteService, $location) {
	$scope.loginUser = function() {
		var result = loginService.loginUser($scope.user, $scope.error);
		result.then(function(response) {
			$state.go("home");
		}, function(response) {
			alert("Eroor");
		});
	}
	$scope.showSidebar = function() {
		if ($scope.width == '0px') {
			$scope.width = '200px';
			$scope.leftmargin = "300px";
		} else {
			$scope.width = '0px';
			$scope.leftmargin = "0px";
		}
	}
	
	/*show Notes*/

	$scope.showNotes = function() {
		var token = localStorage.getItem('login');
		console.log(token);
		var notes = noteService.showNotes(token);
		console.log(notes);
		notes.then(function(response) {
			$scope.notes = response.data;
		}, function(response) {
			$scope.error = response.data.message;
		});
		$scope.notes = notes;
	}
	
	/*save Notes*/
	
	 $scope.createNote = function() {
			var token = localStorage.getItem('login');
			console.log("in create notes ");
			var notes = noteService.saveNote(token, $scope.note);
			notes.then(function(response) {
				showNotes();
			}, function(response) {
				showNotes();
				$scope.error = response.data.message;
			});
		}
	 showNotes();
	
});
