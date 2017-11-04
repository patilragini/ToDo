package com.bridgelabz.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.Model.UserDetails;
import com.bridgelabz.Service.UserService;


@RestController
public class UserControler{
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })//, consumes = MediaType.APPLICATION_JSON_VALUE add if not working
	public ResponseEntity<String> registrationUser(@RequestBody UserDetails user) {
		System.out.println("in registerration User post"+user+"!!!");
		if (user.getEmail()!=null && user.getName()!=null &&user.getPhoneNumber()!=null&&user.getPassword()!=null) {
			userService.createUser(user);					
			System.out.println(" user"+user);
			return new ResponseEntity<String>(HttpStatus.OK);			
		}
		else
		return new ResponseEntity<String>(HttpStatus.CONFLICT);	
	}
	
	@RequestMapping(value="/login", method= RequestMethod.POST)
	public ResponseEntity<String> loginUser(@RequestBody UserDetails user,HttpServletRequest request,HttpSession session) {
		System.out.println("email "+user.getEmail()+" password "+user.getPassword());
		user=userService.loginUser(user);
		if(user != null){
			System.out.println("login successful!!!");
			return new ResponseEntity<String> (HttpStatus.OK);
		}else{
			System.out.println("login unsuccessful!!!");
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
	}
	
	
}