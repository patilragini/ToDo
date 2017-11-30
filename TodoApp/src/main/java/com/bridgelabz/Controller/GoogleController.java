//22 nov
package com.bridgelabz.Controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bridgelabz.Dao.UserDao;
import com.bridgelabz.Model.UserDetails;
import com.bridgelabz.Service.UserService;
import com.bridgelabz.utility.CustomResponse;
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
		String unid = UUID.randomUUID().toString();
		request.getSession().setAttribute("STATE", unid);
		String googleLoginURL = googleConnection.getGoogleAuthURL(unid);
		response.sendRedirect(googleLoginURL);
	}

	@RequestMapping(value = "connectgoogle")
	public void connectionGoogleData(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		String sessionState = (String) request.getSession().getAttribute("STATE");
		String googlestate = request.getParameter("state");

		if (sessionState == null || !sessionState.equals(googlestate)) {
			response.sendRedirect("loginWithGoogle");
			response.sendRedirect("http://localhost:8082/TodoApp/#!/login");
			return;
		}

		String error = request.getParameter("error");
		if (error != null) {
			response.sendRedirect("userlogin");
		}
		String authCode = request.getParameter("code");
		String googleaccessToken = googleConnection.getAccessToken(authCode);
		/*System.out.println("TOKEN googleaccessToken " + googleaccessToken);
		System.out.println("ACCESS TOKEN COPY THIS TO GET DATA!!!\n" + googleaccessToken);*/
		JsonNode profileData = googleConnection.getUserProfile(googleaccessToken);

		System.out.println(profileData);

		String googleEmail = profileData.get("emails").get(0).get("value").asText();
		String googleName = profileData.get("displayName").asText();
		UserDetails userExist = userService.getUserByEmail(googleEmail);
		if (userExist == null) {
			UserDetails googleUser = new UserDetails();
			googleUser.setName(googleName);
			googleUser.setEmail(googleEmail);
			googleUser.setActivated(1);
			googleUser.setProfileUrl(profileData.get("image").get("url").asText());
			int id = userService.createUser(googleUser);
			String token = Token.generateToken(id);
			response.setHeader("login", token);
			session.setAttribute("login", token);
			System.out.println("Login with Google done!!");
			// response.sendRedirect("http://localhost:8082/TodoApp/#!/home");
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
			// ADD REDIRECCT TO LOGIN PAGE i.e TODO LOGIN PAGE *******
		}
	}
	
	/* open collaborator modal 
	$scope.showModelCollaborator = function(note) {
		
		modalInstance = $uibModal.open({
			templateUrl : 'pages/collaboratorNoteModel.html',
			scope : $scope
		});
	}
	
	//COLABORATOR
	$scope.openCollboarate = function(note, user) {
		$scope.note = note;
		$scope.user = user;
		modalInstance = $uibModal.open({
			templateUrl : 'pages/collaboratorNoteModel.html',
			scope : $scope,

		});
	}
	
	
	var collborators = [];
	$scope.getUserlist = function(note, user) {
		var details = {};
		console.log("here::"+note);
		details.note = note;
		details.ownerId = user;
		console.log(user);
		details.shareWithId = {};
		console.log(details);

		var url = 'collaborate';
		var token = localStorage.getItem('login');
		var users = noteService.service(url, 'POST', token, details);
		console.log("fddfsfd"+users);
		users.then(function(response) {

			console.log("dsdsjhfkdhfkdjhfkjh@@@@@@");
			console.log(response.data);
			$scope.users = response.data;
			note.collabratorUsers = response.data;

		}, function(response) {
			$scope.users = {};
			collborators = response.data;

		});
		console.log("Returned");
		console.log(collborators);
		console.log(users);
		return collborators;
	}



	*/

}
