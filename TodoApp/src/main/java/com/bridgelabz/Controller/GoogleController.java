//15 NOV
package com.bridgelabz.Controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bridgelabz.Dao.UserDao;
import com.bridgelabz.Model.UserDetails;
import com.bridgelabz.Service.UserService;
import com.bridgelabz.utility.GoogleConnection;
import com.bridgelabz.utility.Token;
import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class GoogleController {
	@Autowired
	GoogleConnection googleConnection;
	@Autowired
	UserService userService;

	@Autowired
	UserDao userDao;

	@RequestMapping(value = "loginWithGoogle")
	public void googleConnection(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println(" in googleLoginURL ");
		String unid = UUID.randomUUID().toString();
		request.getSession().setAttribute("STATE", unid);
		String googleLoginURL = googleConnection.getGoogleAuthURL(unid);
		// System.out.println("googleLoginURL " + googleLoginURL);
		response.sendRedirect(googleLoginURL);
	}

	@RequestMapping(value = "connectgoogle")
	public void connectionGoogleData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sessionState = (String) request.getSession().getAttribute("STATE");
		String googlestate = request.getParameter("state");

		if (sessionState == null || !sessionState.equals(googlestate)) {
			response.sendRedirect("loginWithGoogle");
		}

		String error = request.getParameter("error");
		if (error != null ) {
			response.sendRedirect("userlogin");
		}
		System.out.println(":::::::::::::GOOGLE ACCOUNT DATA::::::::::::::::::");
		String authCode = request.getParameter("code");
		String googleaccessToken = googleConnection.getAccessToken(authCode);
		System.out.println("TOKEN googleaccessToken " +googleaccessToken);
		System.out.println("ACCESS TOKEN COPY THIS TO GET DATA!!!\n" + googleaccessToken);
		JsonNode profileData = googleConnection.getUserProfile(googleaccessToken);

		System.out.println(profileData);

		String googleEmail = profileData.get("emails").get(0).get("value").asText();
		String googleName = profileData.get("displayName").asText();
		System.out.println("here::"+googleName);
		UserDetails user = userService.getUserByEmail(googleEmail);
		System.out.println("USER:" + user);
		if (user == null) {
			UserDetails googleUser = new UserDetails();
			googleUser.setName(googleName);
			googleUser.setEmail(googleEmail);
			googleUser.setActivated(1);
			googleUser.setProfileUrl(profileData.get("image").get("url").asText());
			int id = userService.createUser(googleUser);
			System.out.println("SCUSSES REGISTRATION  of googleUser:" + id);
			// UserDetails s = userService.loginUser(fbUser);
			// System.out.println("fb reg::" + s);
			String token = Token.generateToken(googleEmail, id);
			System.out.println("TOKEN ::\n ---> " +token);
			response.setHeader("login", token);
			System.out.println("Login with Google done!!");
		} else {
			System.out.println("********USER IS REGISTERED!!!****************");			
			response.sendRedirect("http://localhost:8082/TodoApp/#!/home");
			return;
			// ADD REDIRECCT TO LOGIN PAGE i.e TODO LOGIN PAGE *******
		}

		System.out.println("google profile :" + profileData);
		System.out.println("google profile :" + profileData.get("displayName"));
		System.out.println("user email in google login :" + profileData.get("emails").get(0).get("value").asText()); // asText()
		System.out.println("google profile :" + profileData.get("image").get("url"));
		response.sendRedirect("http://localhost:8082/TodoApp/#!/home");
		System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::");
		

	}
}
