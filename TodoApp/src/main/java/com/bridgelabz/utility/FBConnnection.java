package com.bridgelabz.utility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FBConnnection {
	public static final String APP_ID = "159040634830938";
	public static final String Secret_Id = "3ff01421af88cc0e22d33b090d304bc1";
	public static final String Redirect_URI = "http://localhost:8082/TodoApp/connectFB";

	public String getURI() {
		String facebookLoginURL = "";
		try {
			facebookLoginURL = "http://www.facebook.com/dialog/oauth?" + "client_id=" + APP_ID + "&redirect_uri="
					+ URLEncoder.encode(Redirect_URI, "UTF-8") + "&state=123&response_type=code"
					+ "&scope=public_profile,email";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return facebookLoginURL;
	}

	public String getAccessToken(String code) {
		String accessTokenURL = "";
		try {
			accessTokenURL = "https://graph.facebook.com/v2.9/oauth/access_token?" + "client_id=" + APP_ID
					+ "&redirect_uri=" + URLEncoder.encode(Redirect_URI, "UTF-8") + "&client_secret=" + Secret_Id
					+ "&code=" + code;
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ResteasyClient restCall = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = restCall.target(accessTokenURL);

		Form form = new Form();
		form.param("client_id", APP_ID);
		form.param("client_secret", Secret_Id);
		form.param("redirect_uri", Redirect_URI);
		form.param("code", code);
		form.param("grant_type", "authorization_code");
		Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(form));
		System.out.println(response);
		String token = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		String accessToken = null;
		try {
			accessToken = mapper.readTree(token).get("access_token").asText();
		} catch (IOException e) {
			e.printStackTrace();
		}
		restCall.close();
		return accessToken;
	}

	public JsonNode getUserProfile(String accessToken) {

		String fbgetUserURL = "https://graph.facebook.com/v2.9/me?access_token=" + accessToken
				+ "&fields=id,name,email,picture";
		ResteasyClient restCall = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = restCall.target(fbgetUserURL);

		String headerAuth = "Bearer " + accessToken;
		Response response = target.request().header("Authorization", headerAuth).accept(MediaType.APPLICATION_JSON)
				.get();

		// GooglePojo profile = response.readEntity(GooglePojo.class); 
		String profile = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode fbProfile = null;
		try {
			fbProfile = mapper.readTree(profile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		restCall.close();
		return fbProfile;
	}
}
/*






$scope.collborate = function(note, user) {
	var obj = {};
	console.log(note);
	obj.note = note;
	obj.ownerId = user;
	obj.shareWithId = $scope.shareWith;

	var url = 'collaborate';
	var token = localStorage.getItem('token');
	var users = noteService.service(url, 'POST', token, obj);
	users.then(function(response) {

		console.log("Inside collborator");
		console.log(response.data);
		$scope.users = response.data;
		$scope.note.collabratorUsers = response.data;

	}, function(response) {
		$scope.users = {};

	});
	console.log("Returned");
	console.log(collborators);
	console.log(users);

}

$scope.getOwner = function(note) {
	var url = 'getOwner';
	var token = localStorage.getItem('token');
	var users = noteService.service(url, 'POST', token, note);
	users.then(function(response) {

		$scope.owner = response.data;

	}, function(response) {
		$scope.users = {};
	});
}

$scope.removeCollborator = function(note, user, index) {
	var obj = {};
	var url = 'removeCollborator';
	obj.note = note;
	obj.ownerId = {
		'email' : ''
	};
	obj.shareWithId = user;
	var token = localStorage.getItem('token');
	var users = noteService.service(url, 'POST', token, obj);
	users.then(function(response) {
		$scope.collborate(note, $scope.owner);

		console.log(response.data);

	}, function(response) {
		console.log(response.data);

	});
}

*/
