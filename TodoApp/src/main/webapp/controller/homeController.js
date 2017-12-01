var todoApp = angular.module('TodoApp');

todoApp.controller('homeController', function($scope, toastr, $interval,
		loginService, noteService, $uibModal, $location, $state) {
	$scope.loginUser = function() {
		var result = loginService.loginUser($scope.user, $scope.error);
		result.then(function(response) {
			$state.go("home");
		}, function(response) {
			alert("Wrong user name password");
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
	
	/*$scope.ListView = true;

	$scope.ListViewToggle = function() {
		if ($scope.ListView == true) {
			$scope.ListView = false;
			listGrideView();
		} else {
			$scope.ListView = true;
			listGrideView();
		}
	}

	listGrideView();

	function listGrideView() {
		if ($scope.ListView) {
			var element = document
					.getElementsByClassName('note');
			for (var i = 0; i < element.length; i++) {
				element[i].style.width = "800px";
			}
		} else {
			var element = document
					.getElementsByClassName('card');
			for (var i = 0; i < element.length; i++) {
				element[i].style.width = "400px";
			}
		}
	}*/
	/*
	var getUser = function() {
		var token = localStorage.getItem('login');
		var url = 'getuser';
		var UserDetails = noteService.service(url, 'Get', token);
		console.log("log::"+UserDetails);
		UserDetails.then(function(response) {			
			var Userdetails = response.data;
			console.log(Userdetails);
			if (Userdetails!= null) {
				console.log(Userdetails.name);
				$scope.Userdetails = Userdetails;

			}
			$scope.Userdetails = Userdetails;

		}, function(response) {
			$scope.Userdetails = Userdetails;

		});
	}
	getUser();*/
	

	var getUser = function() {
		var token = localStorage.getItem('login');
		var url = 'getuser';
		var user = noteService.service(url, 'Get', token);

		user.then(function(response) {
			var User = response.data;
			console.log("get user"+User);
			if (User.profileUrl == null) {
				User.profileUrl = "images/default-Profile.png";
				console.log(User.name);
				$scope.user = User
			}
			console.log(User.profileUrl);
			$scope.user = User;

		}, function(response) {

		});

	}
	getUser();	
	
	//COLABORATOR
	$scope.openCollboarate = function(note,user) {
		$scope.note = note;
		$scope.user = user;
		modalInstance = $uibModal.open({
			templateUrl : 'pages/collaboratorNoteModel.html',
			scope : $scope,

		});
	}
	
	/* get owner */
	$scope.getOwner = function(note) {
		var url = 'getOwner';
		var token = localStorage.getItem('login');
		console.log(token);
		var users = noteService.service(url, 'POST', token, note);
		users.then(function(response) {
			$scope.owner = response.data;
		}, function(response) {
			$scope.users = {};
		});
	}
	
	
	
	/* show Notes */

	$scope.showNotes = function() {
		var token = localStorage.getItem('login');

		var notes = noteService.showNotes(token);
		notes.then(function(response) {
			$scope.notes = response.data;
		}, function(response) {
			console.log("ERROR IN SHOWING NOTE");
		});
		}
	
	// make copy of note
	$scope.copy = function(note) {
		note.pinned = false;
		note.archived = false;
		note.archive = false;
		note.remainder = "";
		var token = localStorage.getItem('login');
		var a = noteService.saveNote(token, note);
		a.then(function(response) {
			$scope.showNotes();
		}, function(response) {
		});
	}
	

	
	
	
	
	
	
	
	
	
	
	
	var collborators = [];
	$scope.getUserlist = function(note, user) {
		var details = {};
		console.log("here::"+note);
		details.note = note;
		details.ownerId = user;
		console.log(user);
		details.shareWithId = {};
		console.log(details);
		var url = 'collaborate';
		var token = localStorage.getItem('login');
		var users = noteService.service(url, 'POST', token, details);
		console.log("fddfsfd"+users);
		users.then(function(response) {
			console.log("inside collaborator");
			console.log(response.data);
			$scope.users = response.data;
			note.collabratorUsers = response.data;
			console.log(note.collabratorUsers);

		}, function(response) {
			$scope.users = {};
			collborators = response.data;
			console.log("inside collaborator response"+collborators);

		});
		console.log("Returned");
		console.log(collborators);
		console.log(users);
		return collborators;
	}



	$scope.collborate = function(note, user) {
		var obj = {};
		console.log(note);
		obj.note = note;
		obj.ownerId = user;
		obj.shareWithId = $scope.shareWith;

		var url = 'collaborate';
		var token = localStorage.getItem('login');
		var users = noteService.service(url, 'POST', token, obj);
		users.then(function(response) {

			console.log("Inside collborator");
			console.log(response.data);
			$scope.users = response.data;
			$scope.note.collabratorUsers = response.data;

		}, function(response) {
			$scope.users = {};

		});
		console.log("Returned");
		console.log(collborators);
		console.log(users);

	}

	

	$scope.removeCollborator = function(note, user) {
		var obj = {};
		var url = 'removeCollborator';
		obj.note = note;
		obj.ownerId = {
			'email' : ''
		};
		obj.shareWithId = user;
		var token = localStorage.getItem('login');
		var users = noteService.service(url, 'POST', token, obj);
		users.then(function(response) {
			$scope.collborate(note, $scope.owner);

			console.log(response.data);

		}, function(response) {
			console.log(response.data);

		});
	}
	
	
	


	/* save Notes */

	$scope.saveNote = function(note) {
		var token = localStorage.getItem('login');
		console.log("in create notes ");
		note.pinned = false;
		note.archived = false;
		note.archive = false;
		var notes = noteService.saveNote(token, note);
		
		notes.then(function(response) {
			$scope.note.title="";
			$scope.note.description="";
			$scope.showNotes();
			$scope.showDiv=false;
		}, function(response) {
			console.log("ERROR IN SAVING NOTE");
		});
	}
	/* update Notes */
	$scope.updateNote = function(note) {
		var token = localStorage.getItem('login');
		var notes = noteService.updateNote(token, note);
		notes.then(function(response) {
			$scope.showNotes();
		}, function(response) {
			$scope.showNotes();
		});
	}

	/* delete Note and add to trash */
	$scope.deleteNote = function(note) {
		var token = localStorage.getItem('login');
		note.inTrash = true;
		console.log(notes);
		var notes = noteService.deleteNote(token, note);
		notes.then(function(response) {
			$scope.showNotes();
		}, function(response) {
			$scope.showNotes();
		});
	}

	/* delete Note forever */
	$scope.deleteForeverNote = function(note) {
		var token = localStorage.getItem('login');
		var notes = noteService.deleteForeverNote(token, note);
		notes.then(function(response) {
			$scope.showNotes();
		}, function(response) {
			$scope.showNotes();
		});

	}

	/* Restore note from trash */
	$scope.restoreNote = function(note) {
		var token = localStorage.getItem('login');
		note.inTrash = false;
		var notes = noteService.restoreNote(token, note);
		notes.then(function(response) {
			$scope.showNotes();
		}, function(response) {
			$scope.showNotes();
		});
	}

	/* note pin */
	$scope.pinNote = function(note) {
		var token = localStorage.getItem('login');
		console.log("pin note");
		note.pinned = true;
		var notes = noteService.updateNote(token, note);
		notes.then(function(response) {
			console.log("pin note");
			$scope.showNotes();
		}, function(response) {
			$scope.showNotes();
		});
	}

	/* note Un pin */

	$scope.unpinNote = function(note) {
		var token = localStorage.getItem('login');
		console.log("Un pin note");
		note.pinned = false;
		var notes = noteService.updateNote(token, note);
		notes.then(function(response) {
			$scope.showNotes();
		}, function(response) {
			$scope.showNotes();
		});
	}

	/* note Archive */
	$scope.ArchiveNote = function(note) {
		var token = localStorage.getItem('login');
		console.log('archive note');
		note.archive = true;
		var notes = noteService.updateNote(token, note);
		notes.then(function(response) {
			console.log(note);
			$scope.showNotes();
		}, function(response) {
			$scope.showNotes();
		});
	}


	/* note Archive */
	$scope.unArchiveNote = function(note) {
		var token = localStorage.getItem('login');
		note.archive = false;
		var notes = noteService.updateNote(token, note);
		notes.then(function(response) {
			$scope.showNotes();
		}, function(response) {
			$scope.showNotes();
		});
	}

	$scope.colors = [ {
		"color" : '#f26f75',
		"path" : 'images/Red.png'
	}, {
		"color" : '#fcff77',
		"path" : 'images/lightyellow.png'
	}, {
		"color" : '#80ff80',
		"path" : 'images/green.png'
	}, {
		"color" : '#9ee0ff',
		"path" : 'images/blue.png'
	}, {
		"color" : '#7daaf2',
		"path" : 'images/darkblue.png'
	}, {
		"color" : '#9966ff',
		"path" : 'images/purple.png'
	}, {
		"color" : '#ff99cc',
		"path" : 'images/pink.png'
	}, {
		"color" : '#bfbfbf',
		"path" : 'images/grey.png'
	}, {
		"color" : '#ffffff',
		"path" : 'images/white.png'
	}

	];

	if ($state.current.name == "home") {
		$scope.navColor = "#ffbb00";
		$scope.navBrand = "ToDo App";
		$scope.navBrandColor = "black";
	} else if ($state.current.name == "trash") {
		$scope.navColor = "#636363"
		$scope.navBrand = "Trash";
		$scope.navBrandColor = "white";

	} else if ($state.current.name == "archive") {
		$scope.navColor = "#607d8b";
		$scope.navBrand = "Archive";
		$scope.navBrandColor = "white";

	}
	if ($state.current.name == "search") {
		$scope.navColor = "#000057";
		$scope.navBrand = "ToDo App";
		$scope.navBrandColor = "white";
	}

	// remainder functions

	$scope.datetimepicker = function(note) {
		console.log("in date time picker");
		$('#mypicker').datetimepicker();
		var remainder = $('#mypicker').val();
		note.remainder = new Date(remainder);
		$scope.updateNote(note);
		console.log(remainder);
	}

	function remainderCheck() {
		$interval(function() {
			var i = 0;
			for (i; i < $scope.notes.length; i++) {
				var currentDate = moment().format('YYYY-MM-DD HH:mm');
//			console.log("currentDate" + currentDate);
//				console.log($scope.notes[i].title);
				var dateString = (new Date($scope.notes[i].remainder));
//				console.log(dateString);
				var dateString2=new Date(dateString).toISOString().slice(0, 19).replace('T', ' ');
				
//				console.log("database date::"+dateString2);
				if (dateString2  === currentDate) {
					$scope.mypicker=dateString2;
//					console.log($scope.notes[i].description);
//					console.log("reminder !!!!! " +dateString2);					
				
					toastr.success('Remainder check notes!!!');
					dateString2=null;
					$scope.updateNote(note);

						
				}
//				console.log("dskjhdjkh");

			}
		}, 10000);
	}
	remainderCheck();

	/*
	 * $scope.remainderCheck=function() { $interval(function(){ var i=0; for(i;i<$scope.notes.length;i++){
	 * var currentDate=$filter('date')(new Date(),'MM/dd/yyyy hh:mm ');
	 * console.log(currentDate); console.log(scope.notes[i].remainder);
	 * if($scope.notes[i].remainder == currentDate){ toastr.success('Remainder ',
	 * 'check note'); } } },1000); }
	 */

	$scope.fbShare = function(note) {
		FB.init({
			appId : '159040634830938',
			status : true,
			cookie : true,
			xfbml : true,
			version : 'v2.4'
		});

		FB.ui({
			method : 'share_open_graph',
			action_type : 'og.likes',
			action_properties : JSON.stringify({
				object : {
					'og:title' : note.title,
					'og:description' : note.description
				}
			})
		}, function(response) {
			if (response && !response.error_message) {
				alert('Posting completed.');
			} else {
				alert('Error while posting.');
			}
		});
	};
	

	$scope.showDiv = false;

	$scope.show = function() {
		$scope.showDiv = true;
	};
	$scope.hide = function() {
		$scope.showDiv = true;
	};



});
