<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Login Page</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/index.css"> 
</head>


<body>
	<div class="container">
		<fieldset>
			<img src="" alt="Google Logo" class="googleimg" align="left">
			<h2>Sign in</h2>
			<h3>to continue to Gmail</h3>
			<form action="loginServlet" method="post">
				<div class="form-group">

					<div>
						<br>
						<div class="form-group">
							<label>Email Id</label> <input type="email" name="email"
								placeholder="Email Id" class="form-control" required />
						</div>
						<br>
						<div class="form-group">
							<label>Password:</label> <input type="password" name="userpass"
								class="form-control" placeholder="Enter Password" required/>
						</div>
						<% String name=(String)session.getAttribute("errorLogin");
						if (name!=null)
							out.print(name);	
							session.removeAttribute("errorLogin");												
						%>
						<br>
						<div class="form-group">
							<a href="RegistrationPage.jsp">Create
								account</a> <input type="submit" class="btn btn-default"
								value="Login" name="action" />
						</div>
					</div>
				</div>
			</form>

		</fieldset>
	</div>
</body>
</html>
