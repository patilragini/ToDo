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

todoApp.directive('datetimepicker', function() {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            console.log('call datetimepicker link...');
            var picker = element.datetimepicker({
                dateFormat: 'dd/MM/yyyy hh:mm:ss'
            });

            //ngModelCtrl.$setViewValue(picker.getDate());

            //model->view
            ngModelCtrl.$render(function() {
                console.log('ngModelCtrl.$viewValue@'+ngModelCtrl.$viewValue);
                picker.setDate(ngModelCtrl.$viewValue || '');
            });

            //view->model
            picker.on('dp.change', function(e) {
                console.log('dp.change'+e.date);              
                scope.$apply(function(){
                    ngModelCtrl.$setViewValue(e.date);
                });
            });
        }
    };
});