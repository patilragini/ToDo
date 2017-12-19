var todoApp = angular.module('TodoApp');

todoApp.factory('noteService', function($http, $location) {
	var noteList = {};
	
	
	noteList.uploadImageInFile=function(url,method,token,note){
		
		return $http({
			method : 'POST',
			url : url,
			data:note,
			headers : {
				'login' : token
			}
		
		});
	}
	
	noteList.showNotes = function(token) {
		return $http({
			method : 'POST',
			url : 'showNotes',
			headers : {
				'login' : token
			}
		});
	}
	noteList.service=function(url,method,token,note){
		return $http({
		    method: method,
		    url: url,
		    data:note,
		    headers: {
		        'login': token
		    }
		
		});
		
	}
	
	noteList.getUrl=function(urlToSend){
		return $http({
		    method: 'POST',
		    url: 'getUrlData',
		    headers: {
		        'url': urlToSend
		    }
		
		});
	}

/*	noteList.service = function(token, note,url) {
		console.log("inservice++"+note);
		return $http({
			method : 'POST',
			url : url,
			data : note,
			headers : {
				'login' : token
			}
		});
	}*/
	
	
	noteList.saveNote = function(token, note) {
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
	
	noteList.updateNote = function(token, note) {
		console.log("IN SERVICE"+note+" ::"+note.id);
		return $http({
			method : 'POST',
			url : 'updateNote/'+note.id,
			data : note,
			headers : {
				'login' : token
			}
		});
	}
	
	noteList.deleteNote = function(token, note) {
		console.log(note);
		return $http({
			method : 'POST',
			url : 'updateNote/'+note.id,
			data : note,
			headers : {
				'login' : token
			}
		});
	}

	noteList.deleteForeverNote = function(token, note) {
		console.log(note);
		return $http({
			method : 'POST',
			url : 'deleteForeverNote/'+note.id,
			data : note,
			headers : {
				'login' : token
			}
		});
	}
	
	noteList.restoreNote = function(token, note) {
		console.log(note);
		return $http({
			method : 'POST',
			url : 'updateNote/'+note.id,
			data : note,
			headers : {
				'login' : token
			}
		});
	}
	

	return noteList;

}

);