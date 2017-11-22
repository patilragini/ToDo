var todoApp = angular.module('TodoApp');

todoApp.factory('noteService', function($http, $location) {
	var noteList = {};
	noteList.showNotes = function(token) {
		return $http({
			method : 'POST',
			url : 'showNotes',
			headers : {
				'login' : token
			}

		});
	}

	notes.addNote = function(token, note) {

		console.log(note);

		return $http({

			method : 'POST',

			url : 'saveNote',

			data : note,

			headers : {

				'login' : token

			}
		});
	}
	return noteList;

}

);