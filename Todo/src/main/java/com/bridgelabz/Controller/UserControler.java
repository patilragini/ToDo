package com.bridgelabz.Controller;

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
import com.bridgelabz.utility.SendMail;


@RestController
public class UserControler{
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/register", method= RequestMethod.POST ,produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> registrationUser(@RequestBody UserDetails user) {
		System.out.println("in registerration User post:"+user.getEmail()+""+user.getName()+""+user.getPassword()
		+"  "+user.getPhoneNumber());
		if (user.getEmail()!=null && user.getName()!=null &&user.getPhoneNumber()!=null && user.getPassword()!=null) {
			System.out.println("abc");			
			int id=userService.createUser(user);
			System.out.println("ID Status:"+id);
			System.out.println(" user: "+user.getName());
			if(id>0){
				String url="http://localhost:8080/Todo/activate/1";
				String from="tryjava2110@gmail.com";//do not change for now
				String to="patilrag21@gmail.com";
				String msg="Click here    link";
				String subject="Subject abc";				
				SendMail.sendMail(from, to, subject, msg);
				System.out.println("mail send!!!");
				}	
			return new ResponseEntity<String>(HttpStatus.OK);
		}		
		return new ResponseEntity<String>(HttpStatus.CONFLICT);	
	}
	
	
	@RequestMapping(value="/login", method= RequestMethod.POST)
	public ResponseEntity<String> loginUser(@RequestBody UserDetails user,HttpServletRequest request,HttpSession session) {
		System.out.println("email "+user.getEmail()+" password "+user.getPassword());
		user=userService.loginUser(user);
		if(user != null){
			System.out.println("login successful!!!");
			return new ResponseEntity<String> (HttpStatus.OK);
		}else
			System.out.println("login unsuccessful!!!");
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
	}
	
	//to activate
	@RequestMapping(value="/activate/{id}", method= RequestMethod.GET)
	public void activate(@PathVariable int id ){
		
		
		
	}
	
	
}