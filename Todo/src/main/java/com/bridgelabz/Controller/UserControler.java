//wed night
package com.bridgelabz.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.bridgelabz.Model.UserDetails;
import com.bridgelabz.Service.UserService;
import com.bridgelabz.utility.MD5Encrypt;
import com.bridgelabz.utility.SendMail;
import com.bridgelabz.utility.Token;

@RestController
public class UserControler {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> registrationUser(@RequestBody UserDetails user) {
		if (user.getEmail() != null && user.getName() != null && user.getPhoneNumber() != null
				&& user.getPassword() != null) {
			//validation***** if
			
			String encryptedPassword = MD5Encrypt.encrypt(user.getPassword());
			user.setPassword(encryptedPassword);
			
			
			int id = userService.createUser(user);
			System.out.println("ID Status:" + id);
			if (id > 0) {
				String url = "http://localhost:8080/Todo/activate/" + id;
				String from = "tryjava2110@gmail.com";// do not change for now
				String to = "patilrag21@gmail.com";
				String msg = "Click on link to activate account  " + url;
				String subject = "Subject abc";
				
				String token=Token.generateToken("RegisterationActivation",user.getEmail(), id);

				SendMail.sendMail(from, to, subject, msg);
				return new ResponseEntity<String>("Mail send", HttpStatus.OK);
			}
			return new ResponseEntity<String>("Email Exists", HttpStatus.CONFLICT);

		}
		return new ResponseEntity<String>("Error in Input", HttpStatus.CONFLICT);
	
	
	
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> loginUser(@RequestBody UserDetails user, HttpServletRequest request) {
		System.out.println("email: " + user.getEmail() + " password: " + user.getPassword() + "Activattion:"
				+ user.getActivated());
		String email = user.getEmail();
		System.out.println("email: " + email);
		
		String encryptedPassword = MD5Encrypt.encrypt(user.getPassword());
		user.setPassword(encryptedPassword);		
		user = userService.loginUser(user);
		
		if (user != null) {
			String token=Token.generateToken("Login",user.getEmail(), user.getId());

			HttpSession session = request.getSession();
			session.setAttribute(session.getId(), user);
			session.setAttribute("user", user);
			System.out.println("login successful!!!");
			return new ResponseEntity<String>("Login Scussfull!!!",HttpStatus.OK);
		} else {
			System.out.println("login unsuccessful!!!");
			return new ResponseEntity<String>("User not Activated !!!", HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/activate/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> activate(@PathVariable int id) {
		UserDetails user = userService.getUserById(id);
		System.out.println("status of user: " + user + " " + user.getName() + " " + user.getId());
		int statusId = user.getId();
		System.out.println("id:" + statusId);
		if (user != null) {
			Boolean status = userService.updateActivation(id);
			return new ResponseEntity<String>("Activation done", HttpStatus.OK);

		}
		return new ResponseEntity<String>("Activation failed!!!", HttpStatus.CONFLICT);

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<String> logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		System.out.println("before" + session.getAttribute("user"));
		session.removeAttribute("user");
		System.out.println("after" + session.getAttribute("user"));
		return new ResponseEntity<String>("Logout done", HttpStatus.OK);

	}

}
