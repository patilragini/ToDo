//saturday
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
	public void connectFacebook(HttpServletRequest request, HttpServletResponse response) {
		String error = request.getParameter("error");
		System.out.println("::::::::::::::::::FACE BOOK DATA:::::::::::::::::::::");
		System.out.println(error);
		String code = request.getParameter("code");
		System.out.println("CODE::" + code);
		String fbAccessToken = fbConnection.getAccessToken(code);
		System.out.println("FB TOKEN\n" + fbAccessToken);

		JsonNode profileData = fbConnection.getUserProfile(fbAccessToken);
		System.out.println(profileData);
		String googleEmail = profileData.get("email").asText();
		String googleName = profileData.get("name").asText();
		UserDetails user = userService.emailValidation(googleEmail);
		System.out.println("USER:"+user);
		if (user == null) {
			UserDetails googleUser = new UserDetails();
			googleUser.setName(googleName);
			googleUser.setEmail(profileData.get("email").asText());
			googleUser.setActivated(1);
			String password = "123456";
			googleUser.setPassword(password);
			System.out.println("GOOGLE PAS::"+googleUser.getPassword());
			int id = userService.createUser(googleUser);
			System.out.println("SCUSSES REGISTRATION OG GOOGLE USE:" + id);
			UserDetails s=userService.loginUser(googleUser);
			System.out.println("fb reg::"+s);
			String token = Token.generateToken(googleEmail, id);
			response.setHeader("login", token);
		} else {
			System.out.println("USER IS REGISTERED!!!");
			userService.loginUser(user);
		}
		String profileImage = profileData.get("picture").get("data").get("url").asText();
		System.out.println("EMAIL: " + profileData.get("email").asText());
		System.out.println("NAME: " + profileData.get("name").asText());
		System.out.println(profileImage);
		System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::");

	}
}
