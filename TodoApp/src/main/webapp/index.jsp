<!DOCTYPE html>
<html>
<head>
<!------------------------- angular  ---------------------------->

<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.6/angular.js"></script>

<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-router/1.0.3/angular-ui-router.min.js"></script>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script 
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.8/angular-sanitize.js"></script>

	<script type="text/javascript"  src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/2.5.0/ui-bootstrap-tpls.js"></script>
<!------------------------- responsive bootstrap ---------------------------->

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	

<meta name="viewport" content="width=device-width, initial-scale=1">

<!-------------------------   Script .js ---------------------------->

<script type="text/javascript" src="script/TodoApp.js"></script>

<!-------------------------   Controllers .js ---------------------------->

<script type="text/javascript" src="controller/loginController.js"></script>
<script type="text/javascript" src="controller/logoutController.js"></script>

<script type="text/javascript" src="controller/registerController.js"></script>
<script type="text/javascript" src="controller/homeController.js"></script>
<script type="text/javascript" src="controller/resetController.js"></script>


<!-------------------------   sevices .js ---------------------------->

<script type="text/javascript" src="service/loginService.js"></script>
<script type="text/javascript" src="service/logoutService.js"></script>

<script type="text/javascript" src="service/registerService.js"></script>
<script type="text/javascript" src="service/forgotpasswordService.js"></script>
<script type="text/javascript" src="service/noteService.js"></script>

<!-------------------------   css  .css ---------------------------->

<link rel="stylesheet" href="css/login.css">
<link rel="stylesheet" href="css/home.css">
<link rel="stylesheet" href="css/forgetpassword.css">
<link rel="stylesheet" href="css/resetpassword.css">
<link rel="stylesheet" href="css/Cards.css">
<link rel="stylesheet" href="css/registrationPage.css">

<link rel="stylesheet" type="text/css" href="css/sidenav.css">


<!-------------------------   custom Directives ---------------------------->

<script type="text/javascript" src="directives/CustomDirectives.js"></script>

<!-- <title>Todo App</title>
<link rel="shortcut icon" href="images/KeepIcon.svg" /> -->


</head>
<body ng-app="TodoApp">
	<div ui-view></div>
	</body>

</html>