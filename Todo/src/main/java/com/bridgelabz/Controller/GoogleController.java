package com.bridgelabz.Controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bridgelabz.utility.GoogleConnection;
import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class GoogleController {
	@Autowired
	GoogleConnection googleConnection;

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
		if (error != null && error.trim().isEmpty()) {
			response.sendRedirect("userlogin");
		}
		System.out.println(":::::::::::::GOOGLE ACCOUNT DATA::::::::::::::::::");
		String authCode = request.getParameter("code");
		String googleaccessToken = googleConnection.getAccessToken(authCode);
		System.out.println("ACCESS TOKEN COPY THIS TO GET DATA!!!\n" + googleaccessToken);
		JsonNode profile = googleConnection.getUserProfile(googleaccessToken);
		System.out.println("google profile :" + profile);
		System.out.println("google profile :" + profile.get("displayName"));
		System.out.println("user email in google login :" + profile.get("emails").get(0).get("value").asText()); // asText()
		System.out.println("google profile :" + profile.get("image").get("url"));
		response.sendRedirect("http://localhost:8081/Todo/#!/home");
		System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::");

	}
}
