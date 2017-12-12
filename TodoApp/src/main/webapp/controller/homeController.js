var todoApp = angular.module('TodoApp');

todoApp
		.controller(
				'homeController',
				function($scope, toastr, $interval, loginService,
						registerService, noteService, $uibModal, $location,
						$state, $http, $timeout, $filter) {

					$scope.loginUser = function() {
						var result = loginService.loginUser($scope.user,
								$scope.error);
						result.then(function(response) {
							$state.go("home");
						}, function(response) {
							alert("Wrong user name password");
						});
					}

					$scope.viewImage = localStorage.getItem('View');
					if ($scope.viewImage == "list") {
						$scope.widthOfNote = "col-md-12 col-sm-12 col-xs-12 col-lg-12";
						console.log("in list");
					} else {
						console.log("in grid");
						$scope.widthOfNote = "col-md-6 col-sm-10 col-xs-12 col-lg-3";
					}

					$scope.changeContainerView = function() {
						if ($scope.viewType == "images/list1.png") {
							$scope.viewType = "images/gridePxart.png";
							localStorage.setItem('View', "grid");
							$scope.widthOfNote = "col-md-6 col-sm-10 col-xs-12 col-lg-3";

						} else {
							$scope.viewType = "images/list1.png";
							$scope.widthOfNote = "col-md-12 col-sm-12 col-xs-12 col-lg-12";
							localStorage.setItem('View', "list");
						}
					}

					urls = [];

					$scope.checkUrlList = function(note) {
						var urlPattern = /(http|ftp|https):\/\/[\w-]+(\.[\w-]+)+([\w.,@?^=%&amp;:\/~+#-]*[\w@?^=%&amp;\/~+#-])?/gi;
						var url = note.description.match(urlPattern);
						var link = [];
						var j = 0;
						note.url = [];
						note.link = [];
						console.log(url);
						if (url != null || url != undefined) {
							for (var i = 0; i < url.length; i++) {
								console.log("url length");
								console.log(url.length);
								note.url[i] = url[i];
								addlabel = noteService.getUrl(url[i]);
								addlabel.then(function(response) {
									j++;
									if (note.size == undefined) {
										note.size = 0;
									}
									console.log(note.size);
									var responseData = response.data;
									link[note.size] = {
										title : responseData.title,
										url : note.url[note.size],
										imageUrl : responseData.imageUrl,
										domain : responseData.domain
									}
									note.link[note.size] = link[note.size];
									note.size = note.size + 1;
									console.log(note.link);
								}, function(response) {
								});
							}
						}
					}

					$scope.addlabel = function() {
						console.log($scope.newLabel);
						console
								.log("LABEL NAME ::"
										+ $scope.newLabel.labelName);
						if ($scope.newLabel != null) {
							var url = 'addLabel';
							var token = localStorage.getItem('login');
							var addlabel = noteService.service(url, 'POST',
									token, $scope.newLabel);
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
						var delLabel = noteService.service(url, 'POST', token,
								label);
						delLabel.then(function(response) {
							getUser();
						});
					}

					$scope.updateLabel = function(label) {
						if (label != null) {
							var token = localStorage.getItem('login');
							console.log("update label called");
							var url = 'updateLabel';
							var addlabel = noteService.service(url, 'POST',
									token, label);
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
							$scope.width = '300px';
							$scope.leftmargin = "150px";
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
					

					$scope.removeImage = function(note) {
						if (note.image != null) {
							note.image=null;
							$scope.updateNote(note);
						}
					}
					
					function dataURItoBlob(dataURI) {
						console.log("data uri **********");
						var binary = atob(dataURI.split(',')[1]);
						var mimeString = dataURI.split(',')[0].split(':')[1]
								.split(';')[0];
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

						var userDetails = registerService.service(url, 'POST',
								user, token);
						console.log("645:: ");
						console.log(userDetails);

						/*
						 * userDetails.then(function(response) { getUser(); },
						 * function(response) { getUser(); $scope.error =
						 * response.data.message;
						 * 
						 * });
						 */

					}

					$scope.$on("fileProgress", function(e, progress) {
						$scope.progress = progress.loaded / progress.total;
					});

					$scope.type = {};
					$scope.type.image = '';
					// GET CURRENT LOGIN USER
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
						var users = noteService.service(url, 'POST', token,
								note);
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

					var getEmailList = function() {
						var token = localStorage.getItem('login');
						var url = "getEmailUserlist";
						var users = noteService.service(url, 'GET', token);
						users.then(function(response) {
							$scope.userList = response.data;
						}, function(response) {
							console.log(response.data);
							console.log("HERE");
						});
					}

					getEmailList();

					// get list of collaborated users
					var collborators = [];
					$scope.getUserlist = function(note, user) {
						var details = {};
						// console.log("here::" + note);
						details.note = note;
						details.ownerId = user;
						// console.log(user);
						details.shareWithId = {};
						// console.log(details);
						var url = 'collaborate';
						var token = localStorage.getItem('login');
						var users = noteService.service(url, 'POST', token,
								details);
						// console.log("fddfsfd" + users);
						users.then(function(response) {
							// console.log("inside collaborator");
							// console.log(response.data);
							$scope.users = response.data;
							note.collabratorUsers = response.data;
							// console.log(note.collabratorUsers);

						}, function(response) {
							$scope.users = {};
							collborators = response.data;
							// console.log("inside collaborator response" +
							// collborators);

						});
						// console.log("Returned");
						// console.log(collborators);
						// console.log(users);
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
						var users = noteService
								.service(url, 'POST', token, obj);
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
						var users = noteService
								.service(url, 'POST', token, obj);
						users.then(function(response) {
							$scope.collborate(note, $scope.owner);
							console.log(response.data);
						}, function(response) {
							console.log(response.data);

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

					/* save Notes */

					$scope.saveNote = function(noteNew) {
						var token = localStorage.getItem('login');
						console.log("in create notes ");
						noteNew.pinned = false;
						noteNew.archived = false;
						noteNew.archive = false;
						var notes = noteService.saveNote(token, noteNew);

						notes.then(function(response) {
							$scope.noteNew.title = "";
							$scope.noteNew.description = "";
							$scope.showNotes();
							$scope.showDiv = false;
						}, function(response) {
							$scope.noteNew.title = "";
							$scope.noteNew.description = "";
							$scope.showNotes();
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

					/* Notes pin ,unpin, archive, unarchive */
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
						$scope.navColor = "#4323d1";
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
					
					/* Notes reminder Delete */
					$scope.editReminder = function(note, reminder) {
						var token = localStorage.getItem('login');
						note.reminderStatus = reminder;
						note.remainder = null;
						var notes = noteService.updateNote(token, note);
						notes.then(function(response) {
							$scope.showNotes();
						}, function(response) {
							$scope.showNotes();
						});
					};

					$scope.tomorrowReminder = function(note) {
						var currentDateTime = $filter('date')(
								new Date().getTime() + 24 * 60 * 60 * 1000,
								'MM/dd/yyyy');
						console.log("currentDateTime tommorow"
								+ currentDateTime);
						note.remainder = currentDateTime + " 8:00 AM";
						console
								.log("currentDateTime tommorow"
										+ note.remainder);
						var token = localStorage.getItem('login');
						var notes = noteService.updateNote(token, note);
					}

					$scope.nextWeekReminder = function(note) {
						$scope.currentDateTime = $filter('date')(
								new Date().getTime() + 7 * 24 * 60 * 60 * 1000,
								'MM/dd/yyyy');
						note.remainder = $scope.currentDateTime + " 8:00 AM";
						console.log("NEXT WEEK ::" + note.remainder);
						var token = localStorage.getItem('login');
						var notes = noteService.updateNote(token, note);
					}

					$scope.datetimepicker = function(note) {
						var token = localStorage.getItem('login');
						console.log("in date time picker");
						$('#mypicker').datetimepicker();
						var remainder = $('#mypicker').val();
						note.remainder = new Date(remainder);
						// note.remainder = $filter('date')(new
						// Date(remainder),'MM/dd/yyyy h:mm a');
						if (note.remainder != null) {
							$scope.updateNote(token, note);
						}
						console.log("mypicker ::::" + remainder);

					}

					function remainderCheck() {
						$interval(function() {
							var currentDate = $filter('date')(new Date(),
									'MM/dd/yyyy h:mm a');
							console.log("currentDate::::" + currentDate);
							var i = 0;
							for (i; i < $scope.notes.length; i++) {
								console.log($scope.notes);
								var dateString2 = (new Date(
										$scope.notes[i].remainder));
								// console.log("database date 1::"+dateString2);
								var dateString3 = $filter('date')(
										new Date(dateString2),
										'MM/dd/yyyy h:mm a');
								if (dateString3 === currentDate) {
									$scope.mypicker = dateString2;
									console.log("reminder !!!!! ");
									toastr.success('Remainder check notes!!!');
									$scope.notes[i].reminderStatus = true;
									var token = localStorage.getItem('login');
									var notes1 = noteService.updateNote(token,
											$scope.notes[i]);
								} else {
									console.log("no remainder");
								}

							}
						}, 20000);
					}
					remainderCheck();

					/*
					 * $scope.remainderCheck=function() { $interval(function(){
					 * var i=0; for(i;i<$scope.notes.length;i++){ var
					 * currentDate=$filter('date')(new Date(),'MM/dd/yyyy hh:mm
					 * '); console.log(currentDate);
					 * console.log(scope.notes[i].remainder);
					 * if($scope.notes[i].remainder == currentDate){
					 * toastr.success('Remainder ', 'check note'); } } },1000); }
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
