var todoApp = angular.module('TodoApp');

todoApp.filter('filterLabel', function() {
	return function(note, labelName) {
		var filteredLabelNotes = [];
		
		console.log(note);
		
		for (var i = 0; i < note.length; i++) {
			var notel = note[i];
			console.log("for"+notel);

			var labelsNote = notel.labels;
			console.log(labelsNote.length);
			for (var j = 0; j < labelsNote.length; j++) {
				if (labelName === labelsNote[j].labelName) {
					console.log(filteredLabelNotes);
					filteredLabelNotes.push(notel);
				}
			}
		}

		return filteredLabelNotes;
	}
});
todoApp.filter('urlFilter', function () {

    var urlPattern = /(http|ftp|https):\/\/[\w-]+(\.[\w-]+)+([\w.,@?^=%&amp;:\/~+#-]*[\w@?^=%&amp;\/~+#-])?/gi;

    return function (text, target) {
    	console.log("in filter");
        return text.replace(urlPattern, '<a target="' + target + '" href="$&">$&</a>');
        
    };

});