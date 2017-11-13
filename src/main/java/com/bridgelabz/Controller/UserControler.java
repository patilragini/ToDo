//NOv 13/17
package com.bridgelabz.Controller;

import java.util.List;

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

import com.bridgelabz.Dao.UserDao;
import com.bridgelabz.Model.UserDetails;
import com.bridgelabz.Service.UserService;
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
	public ResponseEntity<String> registrationUser(@RequestBody UserDetails user, HttpServletRequest request,
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
					String url = "http://localhost:8081/Todo/activate/" + id;
					String to = "patilrag21@gmail.com";
					String msg = "Click on link to activate account  " + url;
					String subject = "Subject abc";
					/*
					 * String token = Token.generateToken("Activation", email,
					 * user.getId()); response.setHeader("Activation", token);
					 */
					SendMail.sendMail(to, subject, msg);
					return new ResponseEntity<String>("Mail send", HttpStatus.OK);
				}
				return new ResponseEntity<String>("Email Exists", HttpStatus.CONFLICT);

			} else
				return new ResponseEntity<String>("Invalid data", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("Error in Input", HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> loginUser(@RequestBody UserDetails user, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		String email = user.getEmail();
		String password = user.getPassword();
		String error2 = Validation.checkemail(email);
		String error4 = Validation.checkpassword(password);
		if (error2 == "valid" && error4 == "valid") {
			String passwordEncrypt = MD5Encryption.encrypt(password);
			user.setPassword(passwordEncrypt);
			
			user = userService.loginUser(user);
			
			UserDetails user1=userService.getUserByEmail(email);
			System.out.println(user1);
			
			if(user1!=null){
			if (user1.getActivated() > 0) {
				System.out.println("HERE:" + user1 + "\n ACTIVATED::" + user1.getActivated());
				//session temperoty remove session use when token remove and invalidate done scussfully
				//session = request.getSession();
				//session.setAttribute(session.getId(), user);
				//session.setAttribute("user", user);
				//token generate
				String token = Token.generateToken(email, user1.getId());
				response.setHeader("login", token);
				System.out.println(Token.verify(token));
				System.out.println("login successful!!!");
				return new ResponseEntity<String>("Login Done!!!", HttpStatus.OK);
			}
			return new ResponseEntity<String>("User Not Activated !!!", HttpStatus.CONFLICT);
			}
			else
				return new ResponseEntity<String>("User do not exists!!!", HttpStatus.CONFLICT);	
		} else {
			System.out.println("Error in Input!!!");
			return new ResponseEntity<String>("Input error !!! !!!", HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/activate/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> activate(@PathVariable int id, HttpSession session, HttpServletRequest request) {
		UserDetails user = userService.getUserById(id);
		if (user != null) {
			Boolean status = userService.updateActivation(id);
			return new ResponseEntity<String>("Activation done", HttpStatus.OK);
		}
		return new ResponseEntity<String>("user not valid!!!", HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<String> logout(HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		session.removeAttribute("user");
		// session.removeAttribute("token"); remove token logic 
		return new ResponseEntity<String>("Logout done", HttpStatus.OK);
	}

	@RequestMapping(value = "/pswdRestLink", method = RequestMethod.POST)
	public ResponseEntity<String> passwordRestLink(@RequestBody UserDetails user, HttpServletRequest request,
			HttpSession session,HttpServletResponse response) {

		String email = user.getEmail();
		String error2 = Validation.checkemail(email);
		if (error2 == "valid") {
			UserDetails userbyEmail = userService.getUserByEmail(email);
			if (userbyEmail != null) {
				String token = Token.generateToken(email,userbyEmail.getId());
				response.setHeader("reset ::", token);	
				//
				String url = "http://localhost:8081/Todo/upatePassword";
				String to = "patilrag21@gmail.com";
				String msg = "Click on link to reset password  " + url+"  \n Enter below code in authentication:"+token;
				String subject = "Reset Password";		
				SendMail.sendMail( to, subject, msg);
				return new ResponseEntity<String>("Mail send", HttpStatus.OK);
			}
			return new ResponseEntity<String>("user not found", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("Enter proper Email", HttpStatus.CONFLICT);
	}
	@RequestMapping(value = "/upatePassword", method = RequestMethod.POST)
	public ResponseEntity<String> upatePassword(@RequestBody UserDetails userForm, HttpSession session, HttpServletRequest request) {
		String token1 = request.getHeader("reset");
		System.out.println("here::"+token1);
		int Tokenid = Token.verify(token1);
		String password=userForm.getPassword();
		String error4 = Validation.checkpassword(password);
		if ( error4 == "valid") {			
			UserDetails user=userService.getUserById(Tokenid);
			String passwordEncrypt = MD5Encryption.encrypt(password);
			user.setPassword(passwordEncrypt);
			boolean updateStatus = userService.updateUser(user);
			if (updateStatus)
				return new ResponseEntity<String>("user password updated", HttpStatus.OK);
			else
				return new ResponseEntity<String>("user password updated", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Invalid Password  wrong!!!", HttpStatus.CONFLICT);
	}
}