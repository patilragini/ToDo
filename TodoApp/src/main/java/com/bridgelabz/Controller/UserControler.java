//22 nov
package com.bridgelabz.Controller;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.Model.Label;
import com.bridgelabz.Model.UserDetails;
import com.bridgelabz.Service.UserService;

import com.bridgelabz.utility.CustomResponse;
import com.bridgelabz.utility.MD5Encryption;
import com.bridgelabz.utility.SendMail;
import com.bridgelabz.utility.Token;
import com.bridgelabz.utility.Validation;

@RestController
public class UserControler {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Void> registrationUser(@RequestBody UserDetails user, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		if (user.getEmail() != null && user.getName() != null && user.getPhoneNumber() != null
				&& user.getPassword() != null) {
			String name = user.getName();
			String email = user.getEmail();
			String phoneNumber = user.getPhoneNumber();
			String password = user.getPassword();

			String error1 = Validation.checkName(name);
			String error2 = Validation.checkemail(email);
			String error3 = Validation.checkphoneNumber(phoneNumber);
			String error4 = Validation.checkpassword(password);

			if (error1 == "valid" && error2 == "valid" && error3 == "valid" && error4 == "valid") {
				String passwordEncrypt = MD5Encryption.encrypt(password);
				user.setPassword(passwordEncrypt);
				int id = userService.createUser(user);
				if (id > 0) {
					String url = "http://localhost:8082/TodoApp/activate/" + id;
					String to = "patilrag21@gmail.com";
					String msg = "Click on link to activate account  " + url;
					String subject = "Subject abc";
					/*
					 * String token = Token.generateToken("Activation", email,
					 * user.getId()); response.setHeader("Activation", token);
					 */
					SendMail.sendMail(to, subject, msg);
					System.out.println("mail send");
					return new ResponseEntity<Void>(HttpStatus.OK);
				}
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);

			} else
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Void> loginUser(@RequestBody UserDetails user, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		String email = user.getEmail();
		String password = user.getPassword();
		String error2 = Validation.checkemail(email);
		String error4 = Validation.checkpassword(password);
		if (error2 == "valid" && error4 == "valid") {
			String passwordEncrypt = MD5Encryption.encrypt(password);
			user.setPassword(passwordEncrypt);
			user = userService.loginUser(user);
			if (user != null) {
				UserDetails user1 = userService.getUserByEmail(email);
				if (user1.getActivated() > 0) {
					session = request.getSession();
					session.setAttribute(session.getId(), user);
					session.setAttribute("user", user);
					String token = Token.generateToken(user1.getId());
					response.setHeader("login", token);
					return new ResponseEntity<Void>(HttpStatus.OK);
				} else {
					return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
				}
			} else
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/activate/{id}", method = RequestMethod.GET)
	public ResponseEntity<Void> activate(@PathVariable int id, HttpSession session, HttpServletRequest request) {
		UserDetails user = userService.getUserById(id);
		if (user != null) {
			Boolean status = userService.updateActivation(id);

			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<Void> logout(HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		session.removeAttribute("user");
		session.removeAttribute("token");
		System.out.println("in log out javas");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public CustomResponse passwordRestLink(@RequestBody UserDetails user, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		CustomResponse customResponse = new CustomResponse();

		String email = user.getEmail();
		String error2 = Validation.checkemail(email);
		if (error2 == "valid") {
			UserDetails userbyEmail = userService.getUserByEmail(email);
			if (userbyEmail != null) {
				String token = Token.generateToken(userbyEmail.getId());
				response.setHeader("token", token);
				String url = "http://localhost:8082/TodoApp/" + "#!/resetpassword/" + token;
				String to = "patilrag21@gmail.com";
				String msg = "Click on link to reset password  " + url + "  \n Enter below code in authentication:"
						+ token;
				String subject = "Reset Password";
				SendMail.sendMail(to, subject, msg);
				customResponse.setMessage("Email sent!!!");
				customResponse.setStatus(2);
				return customResponse;
				// return new ResponseEntity<String>("Mail send",
				// HttpStatus.OK);
			} else {
				System.out.println("invalid email");
				customResponse.setMessage("Please enter valid emailID");
				customResponse.setStatus(5);
				return customResponse;
				// return new ResponseEntity<String>("user not found",
				// HttpStatus.CONFLICT);
			}
		}

		customResponse.setMessage("Please enter valid emailID");
		customResponse.setStatus(5);
		return customResponse;
	}

	@RequestMapping(value = "/resetpassword/{token:.+}", method = RequestMethod.POST)
	public CustomResponse upatePassword(@PathVariable("token") String token, @RequestBody UserDetails userForm,
			HttpSession session, HttpServletRequest request) {
		CustomResponse customResponse = new CustomResponse();
		if (token != null) {
			int Tokenid = Token.verify(token);
			String password = userForm.getPassword();
			String error4 = Validation.checkpassword(password);
			if (error4 == "valid") {
				UserDetails user = userService.getUserById(Tokenid);

				String passwordEncrypt = MD5Encryption.encrypt(password);
				user.setPassword(passwordEncrypt);
				boolean updateStatus = userService.updateUser(user);
				if (updateStatus) {
					customResponse.setMessage("Reset password is success :");
					customResponse.setStatus(2);
					return customResponse;
					// return new ResponseEntity<String>("user password
					// updated", HttpStatus.OK);
				} else {
					customResponse.setMessage("Password could not be changed");
					customResponse.setStatus(-2);
					return customResponse;
					// return new ResponseEntity<String>("Error",
					// HttpStatus.OK);
				}
			} else {
				customResponse.setMessage("Error in email");
				customResponse.setStatus(5);
				return customResponse;
				// return new ResponseEntity<String>("Invalid Password
				// wrong!!!", HttpStatus.CONFLICT);
			}
		} else {
			customResponse.setMessage("Inavlid tokaen");
			customResponse.setStatus(-2);
			return customResponse;
		}
	}

	@RequestMapping(value = "/getToken")
	public ResponseEntity<CustomResponse> getToken(HttpSession session) {
		CustomResponse response = new CustomResponse();
		// System.out.println("in get dummy::------>>>>>>>>>>");
		response.setMessage((String) session.getAttribute("login"));
		// System.out.println(response.getMessage());
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/getuser", method = RequestMethod.GET)
	public ResponseEntity<UserDetails> getUser(HttpServletRequest request) {
		String token = request.getHeader("login");
		UserDetails user = userService.getUserById(Token.verify(token));
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.ok(user);
		}
	}

	@RequestMapping(value = "/changeUsreProfilePic", method = RequestMethod.POST)
	public CustomResponse changeUsreProfilePic(@RequestBody UserDetails user, HttpServletRequest request) {
		CustomResponse customResponse = new CustomResponse();
				String token = request.getHeader("login");
		UserDetails userLogin = userService.getUserById(Token.verify(token));
		if (user.getId() == userLogin.getId()) {
			boolean status = userService.updateUser(user);
			if (status) {
				System.out.println("User updated !!!");
				customResponse.setMessage("User updated !!!");
				return customResponse;
			}
			customResponse.setMessage("Error in updating user!!!");
			return customResponse;
		} else {
			System.out.println("User Not updated");
			customResponse.setMessage("Error in updating user!!!");
			return customResponse;

		}

	}
/*		
	@RequestMapping(value = "/addLabel", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> addLabel(@RequestBody Label label,HttpServletRequest request){
		CustomResponse response=new CustomResponse();
		String token =request.getHeader("login");
	
		System.out.println("in add label"+token);
		UserDetails user=userService.getUserById(Token.verify(token));
		if(user!=null){
			
			Set<Label> labels=userService.getAllLabels(user.getId());
			if(labels!=null){
			    while(itr.hasNext())
			     {
			    	System.out.println("inside");
			     {
				 response.setMessage("Problem occured");
				 return ResponseEntity.ok(response);
			}
		}else{
			response.setMessage("Token expired");
			 return ResponseEntity.ok(response);
		  
		}
	}
//	deleteLable

	@RequestMapping(value = "/deletelabel", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> 	deleteLabel(@RequestBody Label label,HttpServletRequest request){
		CustomResponse response=new CustomResponse();
		String token =request.getHeader("login");
	System.out.println("in delete label"+label);
		UserDetails user=userService.getUserById(Token.verify(token));
		if(user!=null){			
			Set<Label> labels=userService.getAllLabels(user.getId());
			if(labels!=null){
			
				boolean id=userService.deleteLable(label);
				
				if(id){
					response.setMessage("Label removed !!!");
					return ResponseEntity.ok(response);
				}else{
					 response.setMessage("ERROR");
					 return ResponseEntity.ok(response);
				}
				}
		}else{
			response.setMessage("Token expired !!!");
			 return ResponseEntity.ok(response);
		  
		}
		response.setMessage("Token Issue !!!");
		 return ResponseEntity.ok(response);		
	}*/
	
	
	
}
