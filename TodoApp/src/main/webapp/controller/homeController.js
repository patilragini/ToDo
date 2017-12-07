var todoApp = angular.module('TodoApp');

todoApp.controller('homeController', function($scope, toastr, $interval,
		loginService, registerService, noteService, $uibModal, $location,
		$state, $http) {
	$scope.loginUser = function() {
		var result = loginService.loginUser($scope.user, $scope.error);
		result.then(function(response) {
			$state.go("home");
		}, function(response) {
			alert("Wrong user name password");
		});
	}

	$scope.addlabel = function() {
		console.log($scope.newLabel);
		console.log("LABEL NAME ::" + $scope.newLabel.labelName);
		if ($scope.newLabel != null) {
			var url = 'addLabel';
			var token = localStorage.getItem('login');
			var addlabel = noteService.service(url, 'POST', token,
					$scope.newLabel);
			addlabel.then(function(response) {
				$scope.newLabel.labelName = "";
				getUser();
			});
		}
	}

	$scope.deletelabel = function(label) {
		console.log(label);
		console.log("in deletelabel" + label);
		var url = 'deletelabel';
		var token = localStorage.getItem('login');
		var delLabel = noteService.service(url, 'POST', token, label);
		delLabel.then(function(response) {
			getUser();
		});
	}

	$scope.updateLabel = function(label) {
		if (label!=null) {
			var token = localStorage.getItem('login');
			console.log("update label called");
			var url = 'updateLabel';
			var addlabel = noteService.service(url, 'POST', token, label);
			addlabel.then(function(response) {

			}, function(response) {

			});
		}
	}

	$scope.Labelmodal = function() {
		modalInstance = $uibModal.open({
			templateUrl : 'pages/ModalLabel.html',
			scope : $scope,
			size : 'md'
		});
	};

	$scope.toggleLabelOfNote = function(note, label) {
		var token = localStorage.getItem('login');
		var index = -1;
		var i = 0;
		for (i = 0, len = note.labels.length; i < len; i++) {
			if (note.labels[i].labelName === label.labelName) {
				index = i;
				break;
			}
		}
		if (index == -1) {
			note.labels.push(label);
			var notes = noteService.updateNote(token, note);
		} else {
			note.labels.splice(index, 1);
			noteService.updateNote(token, note);
		}
	}

	$scope.showSidebar = function() {
		if ($scope.width == '0px') {
			$scope.width = '250px';
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

	$scope.uploadme;

	$scope.uploadImage = function(note) {
		var fd = new FormData();
		var imgBlob = dataURItoBlob($scope.uploadme);
		console.log("uploadme:: " + $scope.uploadme);
		fd.append('file', imgBlob);
		$http.post('imageURL', fd, {
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		})
		var img = $scope.uploadme;
		if (img != null) {

		}
	}
	function dataURItoBlob(dataURI) {
		console.log("data uri **********");
		var binary = atob(dataURI.split(',')[1]);
		var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
		var array = [];
		for (var i = 0; i < binary.length; i++) {
			array.push(binary.charCodeAt(i));
		}
		return new Blob([ new Uint8Array(array) ], {
			type : mimeString
		});
	}

	$scope.openImageUploader = function(type, typeOfImage) {
		$scope.type = type;
		$scope.typeOfImage = typeOfImage;
		$('#image').trigger('click');
		return false;
	}

	$scope.stepsModel = [];

	$scope.imageUpload = function(element) {
		var reader = new FileReader();
		console.log("elements!!!!!!" + element);
		reader.onload = $scope.imageIsLoaded;
		reader.readAsDataURL(element.files[0]);
		console.log(element.files[0]);
	}

	$scope.imageIsLoaded = function(e) {
		$scope.$apply(function() {
			$scope.stepsModel.push(e.target.result);
			console.log(e.target.result);
			var imageSrc = e.target.result;

			if ($scope.typeOfImage == 'user') {
				console.log("User pic");
				$scope.type.profileUrl = imageSrc;
				updateUser($scope.type);

			} else {
				$scope.type.image = imageSrc;
				console.log(e.target.result);
				console.log("here note " + imageSrc);
				$scope.updateNote($scope.type);
			}
		});
	};

	var updateUser = function(user) {
		var url = 'changeUsreProfilePic';
		console.log("update user::" + user.name);
		console.log(user);
		var token = localStorage.getItem('login');

		var userDetails = registerService.service(url, 'POST', user, token);
		console.log("645:: ");
		console.log(userDetails);

		/*
		 * userDetails.then(function(response) { getUser(); },
		 * function(response) { getUser(); $scope.error = response.data.message;
		 * 
		 * });
		 */

	}

	$scope.$on("fileProgress", function(e, progress) {
		$scope.progress = progress.loaded / progress.total;
	});

	$scope.type = {};
	$scope.type.image = '';
//GET CURRENT LOGIN USER
	var getUser = function() {
		var token = localStorage.getItem('login');
		var url = 'getuser';
		var user = noteService.service(url, 'Get', token);

		user.then(function(response) {
			var User = response.data;
			console.log("get user" + User);
			if (User.profileUrl == null) {
				User.profileUrl = "images/defaultpic.jpg";
				console.log(User.name);
				$scope.user = User
			}
			// console.log(User.profileUrl);
			$scope.user = User;

		}, function(response) {

		});

	}
	getUser();
	
	
	
	
	var getEmailList=function(){
		var token = localStorage.getItem('login');
		var url = "getEmailUserlist";
		var users = noteService.service(url, 'GET',token);
		users.then(function(response) {
			$scope.userList=response.data;
		}, function(response) {
			console.log(response.data);
			console.log("HERE");			
		});
	}
	
	getEmailList();
	
	

	
	
	
	
	// COLABORATOR
	$scope.openCollboarate = function(note, user) {
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

	//get list of collaborated users
	var collborators = [];
	$scope.getUserlist = function(note, user) {
		var details = {};
//		console.log("here::" + note);
		details.note = note;
		details.ownerId = user;
//		console.log(user);
		details.shareWithId = {};
//		console.log(details);
		var url = 'collaborate';
		var token = localStorage.getItem('login');
		var users = noteService.service(url, 'POST', token, details);
//		console.log("fddfsfd" + users);
		users.then(function(response) {
//			console.log("inside collaborator");
//			console.log(response.data);
			$scope.users = response.data;
			note.collabratorUsers = response.data;
//			console.log(note.collabratorUsers);

		}, function(response) {
			$scope.users = {};
			collborators = response.data;
//			console.log("inside collaborator response" + collborators);

		});
//		console.log("Returned");
//		console.log(collborators);
//		console.log(users);
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
			$scope.note.title = "";
			$scope.note.description = "";
			$scope.showNotes();
			$scope.showDiv = false;
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
		console.log("in delete forever");
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
	
	/* Notes pin ,unpin,  archive, unarchive */
	$scope.pin = function(note, pinned) {
		var token = localStorage.getItem('login');
		note.pinned = pinned;
		var notes = noteService.updateNote(token, note);
		notes.then(function(response) {
			$scope.showNotes();
		}, function(response) {
			$scope.showNotes();
		});
	};

	$scope.archive = function(note, archived) {
		var token = localStorage.getItem('login');
		note.archive = archived;
		console.log(archived);
		noteService.updateNote(token, note);
	}

	/*
	 * note pin $scope.pinNote = function(note) { var token =
	 * localStorage.getItem('login'); console.log("pin note"); note.pinned =
	 * true; var notes = noteService.updateNote(token, note);
	 * notes.then(function(response) { console.log("pin note");
	 * $scope.showNotes(); }, function(response) { $scope.showNotes(); }); }
	 * 
	 * note Un pin
	 * 
	 * $scope.unpinNote = function(note) { var token =
	 * localStorage.getItem('login'); console.log("Un pin note"); note.pinned =
	 * false; var notes = noteService.updateNote(token, note);
	 * notes.then(function(response) { $scope.showNotes(); }, function(response) {
	 * $scope.showNotes(); }); }
	 * 
	 * note Archive $scope.ArchiveNote = function(note) { var token =
	 * localStorage.getItem('login'); console.log('archive note'); note.archive =
	 * true; var notes = noteService.updateNote(token, note);
	 * notes.then(function(response) { console.log(note); $scope.showNotes(); },
	 * function(response) { $scope.showNotes(); }); }
	 * 
	 * 
	 * note Archive $scope.unArchiveNote = function(note) { var token =
	 * localStorage.getItem('login'); note.archive = false; var notes =
	 * noteService.updateNote(token, note); notes.then(function(response) {
	 * $scope.showNotes(); }, function(response) { $scope.showNotes(); }); }
	 */

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

	} else if ($state.current.name == "search") {
		$scope.navColor = "#000057";
		$scope.navBrand = "ToDo App";
		$scope.navBrandColor = "white";
	} else {
		$scope.navLabel = $location.path().substr(1);
		$scope.navColor = "#517a82";
		$scope.navBrandColor = "white";
		console.log("1132:::: " + $scope.navLabel);
		$scope.navBrand = $scope.navLabel;
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
				// console.log("currentDate" + currentDate);
				// console.log($scope.notes[i].title);
				var dateString = (new Date($scope.notes[i].remainder));
				// console.log(dateString);
				var dateString2 = new Date(dateString).toISOString().slice(0,
						19).replace('T', ' ');

				// console.log("database date::"+dateString2);
				if (dateString2 === currentDate) {
					$scope.mypicker = dateString2;
					// console.log($scope.notes[i].description);
					// console.log("reminder !!!!! " +dateString2);

					toastr.success('Remainder check notes!!!');
					dateString2 = null;
					$scope.updateNote(note);

				}
				// console.log("dskjhdjkh");

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
