var todoApp = angular.module('TodoApp');
//['ui.bootstrap']
todoApp.controller('homeController', function($scope, loginService,noteService,$uibModal, $location) {
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
	
	$scope.showModalPin = function(note) {
		$scope.note = note;
		modalInstance = $uibModal.open({
			templateUrl : 'pages/modelNotePin.html',
			scope : $scope,
			size : 'md'
		});
	};
	$scope.showModel = function(note) {
		$scope.note = note;
		modalInstance = $uibModal.open({
			templateUrl : 'pages/homeModel.html',
			scope : $scope,
			size : 'md'
		});
	};
	
	
	$scope.showDiv=false;
	
	$scope.show=function(){
		$scope.showDiv=true;		
	};	
	$scope.hide=function(){
		$scope.showDiv=true;		
	};	
	/*show Notes*/

	$scope.showNotes = function() {
		var token = localStorage.getItem('login');
		var notes = noteService.showNotes(token);
		notes.then(function(response) {
			$scope.notes = response.data;
		}, function(response) {
			console.log("ERROR IN SHOWING NOTE");
		});
		$scope.notes = notes;
	}
	
	/*save Notes*/
	
	$scope.saveNote = function() {
			var token = localStorage.getItem('login');
			console.log("in create notes ");
			var notes = noteService.saveNote(token, $scope.note);
			notes.then(function(response) {
				$scope.showNotes();
			}, function(response) {
				console.log("ERROR IN SAVING NOTE");
			});
		}
	/*update Notes*/	
	$scope.updateNote = function(note) {
			var token = localStorage.getItem('login');			
			var notes = noteService.updateNote(token,note);
			notes.then(function(response) {
				$scope.showNotes();
			}, function(response) {
				$scope.showNotes();
			});
		}
	

	/*delete Note and add to trash*/
	$scope.deleteNote = function(note) {
		var token = localStorage.getItem('login');
		note.inTrash=true;
		console.log(notes);
		var notes = noteService.deleteNote(token,note);
		notes.then(function(response) {
			$scope.showNotes();
		}, function(response) {
			$scope.showNotes();
		});
	}
	
	
	
	/*delete Note forever*/
	$scope.deleteForeverNote = function(note) {
		var token = localStorage.getItem('login');	
		var notes = noteService.deleteForeverNote(token,note);
		notes.then(function(response) {
			$scope.showNotes();
		}, function(response) {
			$scope.showNotes();
		});
	}
	
	

	/*Restore note from trash*/
	$scope.restoreNote = function(note) {
		var token = localStorage.getItem('login');
		note.inTrash=false;		
		var notes = noteService.restoreNote(token,note);
		notes.then(function(response) {
			$scope.showNotes();
		}, function(response) {
			$scope.showNotes();
		});
	}
	
	
	
	/*note pin*/
	$scope.pinNote = function(note) {
		var token = localStorage.getItem('login');
		console.log("pin note");
		note.pinned=true;		
		var notes = noteService.updateNote(token,note);
		notes.then(function(response) {
			console.log("pin note");
			$scope.showNotes();
		}, function(response) {
			$scope.showNotes();
		});
	}
	
	/*note Un pin*/
	
	$scope.unpinNote = function(note) {
		var token = localStorage.getItem('login');
		console.log("Un pin note");
		note.pinned=false;		
		var notes = noteService.updateNote(token,note);
		notes.then(function(response) {
			$scope.showNotes();
		}, function(response) {
			$scope.showNotes();
		});
	}
	
	
	/*note Archive */
	$scope.ArchiveNote = function(note) {
		var token = localStorage.getItem('login');
		console.log('archive note');
		note.archive=true;		
		var notes = noteService.updateNote(token,note);
		notes.then(function(response) {
			console.log(note);
			$scope.showNotes();
		}, function(response) {
			$scope.showNotes();
		});
	}
	
	/*note Archive */
	$scope.unArchiveNote = function(note) {
		var token = localStorage.getItem('login');
		note.archive=false;		
		var notes = noteService.updateNote(token,note);
		notes.then(function(response) {
			$scope.showNotes();
		}, function(response) {
			$scope.showNotes();
		});
	}
	
	$scope.colors=[
		{"color":'#e74c3c',"path":'images/Red.png'},
		{"color":'#fcff77',"path":'images/lightyellow.png'},{"color":'#80ff80',"path":'images/green.png'},{"color":'#0099ff',"path":'images/blue.png'},
		{"color":'#1a53ff',"path":'images/darkblue.png'},{"color":'#9966ff',"path":'images/purple.png'
		},{"color":'#ff99cc',"path":'images/pink.png'},{"color":'#bfbfbf',"path":'images/grey.png'},{"color":'#ffffff',"path":'images/white.png'}
		
	];
	
	
	
		
});
