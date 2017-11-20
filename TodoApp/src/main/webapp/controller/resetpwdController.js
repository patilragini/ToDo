var toDoApp = angular.module('TodoApp');

toDoApp.controller('resetController', function($scope, forgotpasswordService,
		$location) {

	$scope.sendLink = function (response) {
		//var reset= response.get(reset);
		var httpSendLink = forgotpasswordService.sendLinkToEmail($scope.user);
		httpSendLink.then(function(CustomResponse) {
			
			if (CustomResponse.data.status == 5) {
//				clonsole.log("INVALID");
				$scope.CustomResponse = 'INVALID USER!!!';
			} else if (CustomResponse.data.status == -2) {
//				clonsole.log("MAIL SENT FAILED");
				$scope.CustomResponse = 'MAIL NOT SENT!!!';
			} else if (CustomResponse.data.status == 2) {
//			localStorage.setItem('reset',reset);						
//			    localStorage.setItem('reset', user.data.token);
				$location.path('/resetpassword');
			}
		});
	}

	$scope.resetPassword = function() {
		var httpReset = forgotpasswordService.resetPassword($scope.user);
		httpReset.then(function(response) {

			if (response.data.status == 5) {
				$scope.response = 'INVALID EMAIL';
			} else if (response.data.status == 5) {
				$scope.response = 'PASSWORD NOT UPDATED';
			} else if (response.data.status == 2) {
//				localStorage.getItem('reset',request.headers('reset'));
/*				localStorage.getItem('reset');
*/				$location.path('/login');
			}
		});
	}
});