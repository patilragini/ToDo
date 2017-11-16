//16 NOV
package com.bridgelabz.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.Dao.UserDao;
import com.bridgelabz.Model.UserDetails;
import com.bridgelabz.Service.UserService;
import com.bridgelabz.utility.FBConnnection;
import com.bridgelabz.utility.Token;
import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class FBConroller {
	@Autowired
	FBConnnection fbConnection;

	@Autowired
	UserService userService;

	@Autowired
	UserDao userDao;

	@RequestMapping(value = "/loginWithFB")
	public void facebookConnection(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String fbLoginURL = fbConnection.getURI();
		System.out.println("fbLoginURL  " + fbLoginURL);

		response.sendRedirect(fbLoginURL);
	}

	@RequestMapping(value = "/connectFB")
	public void connectFacebook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String error = request.getParameter("error");
		System.out.println("::::::::::::::::::FACE BOOK DATA:::::::::::::::::::::");
		
		if (error != null ) {
			System.out.println(error);
			response.sendRedirect("userlogin");
		}
		
		String code = request.getParameter("code");
		System.out.println("CODE::" + code);
		String fbAccessToken = fbConnection.getAccessToken(code);
		System.out.println("TOKEN ::\n ---> " + fbAccessToken);
		JsonNode profileData = fbConnection.getUserProfile(fbAccessToken);
		System.out.println(profileData);
		String fbEmail = profileData.get("email").asText();
		String fbName = profileData.get("name").asText();
		UserDetails user = userService.getUserByEmail(fbEmail);
		System.out.println("USER:" + user);
		if (user == null) {
			UserDetails fbUser = new UserDetails();
			fbUser.setName(fbName);
			fbUser.setEmail(profileData.get("email").asText());
			fbUser.setActivated(1);
			int id = userService.createUser(fbUser);
//			System.out.println("SCUSSES REGISTRATION FB USE:" + id);
//			UserDetails s = userService.loginUser(fbUser);
	//		System.out.println("fb reg::" + s);
			String token = Token.generateToken(fbEmail, id);
			response.setHeader("login", token);
			System.out.println("Login with FB done!!");
			//ADD REDIRECCT TO HOME PAGE i.e TODO PAGE

		} else {
			System.out.println("********USER IS REGISTERED!!!****************");
			response.sendRedirect("http://localhost:8082/TodoApp/#!/login");
			//ADD REDIRECCT TO LOGIN PAGE i.e TODO LOGIN PAGE *******
		}
		String profileImage = profileData.get("picture").get("data").get("url").asText();
		System.out.println("EMAIL: " + profileData.get("email").asText());
		System.out.println("NAME: " + profileData.get("name").asText());
		System.out.println(profileImage);
		System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::");
		response.sendRedirect("http://localhost:8082/TodoApp/#!/home");

	}
}
