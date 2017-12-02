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
todoApp.directive('contenteditable', ['$sce', function($sce) {

	  return {

	  restrict: 'A',  // only activate on element attribute

	  require: '?ngModel', // get a hold of NgModelController

	  link: function(scope, element, attrs, ngModel) {

	      if (!ngModel) return; // do nothing if no ng-model

	        // Specify how UI should be updated
	      ngModel.$render = function() {

	        element.html($sce.getTrustedHtml(ngModel.$viewValue || ''));

	        read(); 

	      };
	        // Listen for change events to enable binding
	      element.on('blur keyup change', function() {

	        scope.$evalAsync(read);// initialize

	      });
	        // Write data to the model
	      function read() {

	        var html = element.html();

	        
	        // When we clear the content editable the browser leaves a <br> behind
	          // If strip-br attribute is provided then we strip this out
	        if ( attrs.stripBr && html == '<br>' ) {

	          html = '';

	        }
	        if ( attrs.stripDiv && html == '<div>' &&html=='</div>') {

		          html = '';

		        }

	        ngModel.$setViewValue(html);

	      }

	    }

	  };

	}]);
//your directive
todoApp.directive("fileread", [
  function() {
    return {
      scope: {
        fileread: "="
      },
      link: function(scope, element, attributes) {
        element.bind("change", function(changeEvent) {
          var reader = new FileReader();
          reader.onload = function(loadEvent) {
            scope.$apply(function() {
              scope.fileread = loadEvent.target.result;
            });
          }
          reader.readAsDataURL(changeEvent.target.files[0]);
        });
      }
    }
  }
]);
toDo.directive("ngFileSelect", function(fileReader, $timeout) {
	return {
		scope : {
			ngModel : '='
		},
		link : function($scope, el) {
			function getFile(file) {
				fileReader.readAsDataUrl(file, $scope).then(function(result) {
					$timeout(function() {
						$scope.ngModel = result;
					});
				});
			}

			el.bind("change", function(e) {
				var file = (e.srcElement || e.target).files[0];
				getFile(file);
			});
		}
	};
});

toDo.factory("fileReader", function($q, $log) {
	var onLoad = function(reader, deferred, scope) {
		return function() {
			scope.$apply(function() {
				deferred.resolve(reader.result);
			});
		};
	};

	var onError = function(reader, deferred, scope) {
		return function() {
			scope.$apply(function() {
				deferred.reject(reader.result);
			});
		};
	};

	var onProgress = function(reader, scope) {
		return function(event) {
			scope.$broadcast("fileProgress", {
				total : event.total,
				loaded : event.loaded
			});
		};
	};

	var getReader = function(deferred, scope) {
		var reader = new FileReader();
		reader.onload = onLoad(reader, deferred, scope);
		reader.onerror = onError(reader, deferred, scope);
		reader.onprogress = onProgress(reader, scope);
		return reader;
	};

	var readAsDataURL = function(file, scope) {
		var deferred = $q.defer();

		var reader = getReader(deferred, scope);
		reader.readAsDataURL(file);

		return deferred.promise;
	};

	return {
		readAsDataUrl : readAsDataURL
	};
});
/*
todoApp.directive('datetimepicker', function() {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            console.log('call datetimepicker directive...');
            var picker = element.datetimepicker({
                dateFormat: 'dd/MM/yyyy hh:mm:ss'
            });
            ngModelCtrl.$render(function() {
                console.log('ngModelCtrl.$viewValue@'+ngModelCtrl.$viewValue);
                picker.setDate(ngModelCtrl.$viewValue || '');
            });
            picker.on('dp.change', function(e) {
                console.log('dp.change'+e.date);              
                scope.$apply(function(){
                    ngModelCtrl.$setViewValue(e.date);
                });
            });
        }
    };
});*/