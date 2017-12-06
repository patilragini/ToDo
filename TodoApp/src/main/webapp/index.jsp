<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="https://unpkg.com/angular-toastr/dist/angular-toastr.css" />

<!------------------------- angular  ---------------------------->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>
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

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>   
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
	
	
	
	
	<script src="https://unpkg.com/angular-toastr/dist/angular-toastr.tpls.js"></script>
	

<!-- or -->
<!-- <script src="https://cdn.jsdelivr.net/npm/angular-toastr@2/dist/angular-toastr.tpls.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/angular-toastr@2/dist/angular-toastr.css"> -->
<!------------------------- responsive bootstrap ---------------------------->

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"	href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.min.css">
<meta name="viewport" content="width=device-width, initial-scale=1">


<script src="https://connect.facebook.net/enUS/all.js"></script>

<!-------------------------   Script .js ---------------------------->

<script type="text/javascript" src="script/TodoApp.js"></script>


<!-- <script type="text/javascript">
    $(".form_datetime").datetimepicker({
        format: "dd MM yyyy - hh:ii"
    });
</script>   -->

<!-------------------------   Controllers .js ---------------------------->

<script type="text/javascript" src="controller/loginController.js"></script>
<script type="text/javascript" src="controller/logoutController.js"></script>

<script type="text/javascript" src="controller/registerController.js"></script>
<script type="text/javascript" src="controller/homeController.js"></script>
<script type="text/javascript" src="controller/resetController.js"></script>
<script type="text/javascript" src="controller/dummyController.js"></script>



<!-------------------------   sevices .js ---------------------------->

<script type="text/javascript" src="service/loginService.js"></script>
<script type="text/javascript" src="service/logoutService.js"></script>

<script type="text/javascript" src="service/registerService.js"></script>
<script type="text/javascript" src="service/forgotpasswordService.js"></script>
<script type="text/javascript" src="service/noteService.js"></script>
<script type="text/javascript" src="service/dummyservice.js"></script>


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
<script type="text/javascript" src="js/FilterLabel.js"></script>


<title>Todo App</title>
<link rel="shortcut icon" href="images/KeepIcon.svg" />


</head>
<body ng-app="TodoApp">
	<div ui-view></div>
	</body>

</html>