var todoApp = angular.module('TodoApp');
todoApp.directive('topNavBar', function() {
		return{
			templateUrl : 'pages/TopNavBar.html'
		}

	
});
/*app.directive('autolinker',['$interpolate', function($interpolate) {

    //var lastCharIsSpace = customHelpers.lastCharIsSpace;

    return {
      restrict: 'A',
      require: ['ngModel'],
      priority: 1000, // to ensure that the ngModel has been edited by the ta-bind first
      link: function(scope, element, attrs, controllers) {
        var ngModel = controllers[0];

        // Applied when the model changes through user input
        var linker = function(raw) {
          
          var check_text = element.text();
          var lastChar = check_text.substr(check_text.length - 1);
          if(/\s+$/.test(lastChar)){}else{
            return raw;
          }

          var content = Autolinker.link(raw);

          // only update when we have actually autolinked something
          if(raw !== content) {
            ngModel.$viewValue = content;

            
            element.innerHTML= content;
            scope.htmlcontent = $interpolate(content)(scope)
            //taSelection.setSelectionToElementEnd(element[0]);

            //ngModel.$updateHTML(content);
          }

          return content;
        }

        // unshift to be first parser
        ngModel.$parsers.unshift(linker);
      }
    };
    
  }]);
*/
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