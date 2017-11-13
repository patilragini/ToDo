function validateform() {
	var isvalid = true;
	var regexName = "[a-zA-Z0-9\\s]{2,}";
	var regexPhoneNumber = "[0-9]";
	var regexEmail = "[a-z0-9]{1,}[@]{1}[a-z]{1,}[.]{1}[a-z]{1,}";

	document.getElementById("name").innerHTML = "";
	document.getElementById("email").innerHTML = "";
	document.getElementById("phoneNumber").innerHTML = "";
	document.getElementById("password").innerHTML = "";

	var name = document.myform.name.value;
	var password = document.myform.password.value;
	var phoneNumber = document.myform.phoneNumber.value;
	var email = document.myform.email.value;

	if (name == null || name == "") {
		document.getElementById("name").innerHTML = "Invalid input";
		alert("Name invalid !!!");
		isvalid = false;
	}
	if (email == null || email == "") {
		document.getElementById("email").innerHTML = "Invalid input";
		alert("Invalid email !!!");
		isvalid = false;
	}
	if (phoneNumber.length != 10) {
		document.getElementById("phoneNumber").innerHTML = "Invalid input";
		alert("Invalid phone number !!!");
		isvalid = false;
	}
	if (password.length < 6) {
		document.getElementById("password").innerHTML = "Invalid input";
		alert("Password must be at least 6 characters long !!!");
		isvalid = false;
	}
	return isvalid;
}
function checkemail()
{
	  var email=document.getElementById( "UserEmail" ).value; 
	  if(email)
	   {
$.ajax({
	  url: "Check",type: "post", data: {email:document.getElementById( "UserEmail" ).value},
	  success: function(response){
		  if(response=="{success: true}")
	      $("#email_status").html('Existing account');
		  else  if(response=="{success: false}")
			  $("#email_status").html('Account valid');
		  else
		  $("#email_status").html('Account invalid');
	  },
	  error:function(){
		    $("#email_status").html('there is error while submit'+response);
	  }   
	}); 
}
	   else
	   {
	    $( '#email_status' ).html("");
	    return false;
	   }
}