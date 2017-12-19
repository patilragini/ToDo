package com.bridgelabz.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
//		System.out.println("fbLoginURL  " + fbLoginURL);
		response.sendRedirect(fbLoginURL);
	}

	@RequestMapping(value = "/connectFB")
	public void connectFacebook(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
		String error = request.getParameter("error");
//		System.out.println("::::::::::::::::::FACE BOOK DATA:::::::::::::::::::::");
		
		if (error != null ) {
			System.out.println(error);
			response.sendRedirect("userlogin");
			response.sendRedirect("http://localhost:8082/TodoApp/#!/login");
			return;
		}
		
		String code = request.getParameter("code");
//		System.out.println("CODE::" + code);
		String fbAccessToken = fbConnection.getAccessToken(code);
//		System.out.println("TOKEN ::\n ---> " + fbAccessToken);
		JsonNode profileData = fbConnection.getUserProfile(fbAccessToken);
		String fbEmail = profileData.get("email").asText();
		String fbName = profileData.get("name").asText();
		UserDetails userExist = userService.getUserByEmail(fbEmail);
		if (userExist == null) {
			UserDetails fbUser = new UserDetails();
			fbUser.setName(fbName);
			fbUser.setEmail(profileData.get("email").asText());
			fbUser.setActivated(1);
//			fbUser.setProfileUrl(profileData.get("image").get("url").asText());
			int id = userService.createUser(fbUser);
			String token = Token.generateToken(id);
			response.setHeader("login", token);
			session.setAttribute("login", token);			
			response.setHeader("login", token);
			System.out.println("Login with FB done!!");
			response.sendRedirect("http://localhost:8082/TodoApp/#!/dummy");
			return;

		} else {
			int id = userExist.getId();
			String token = Token.generateToken(id);
			response.setHeader("login", token);
			session.setAttribute("login", token);
			System.out.println("********USER IS REGISTERED EXISTING!!!****************");
			response.sendRedirect("http://localhost:8082/TodoApp/#!/dummy");
			return;
		}

	}
}
