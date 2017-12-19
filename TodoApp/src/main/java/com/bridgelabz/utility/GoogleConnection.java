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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GoogleConnection {

	public static final String CLIENT_Id = "3fhhffghghfghgfh.ogleusercontent.com";
	public static final String Secret_Id = "rkO3hfghfghfgha811JNO";
	public static final String Redirect_URI = "http://localhost:8082/TodoApp/connectgoogle";

	// Access token in header
	public static String Gmail_GET_USER_URL = "https://www.googleapis.com/plus/v1/people/me";

	public static String getGoogleAuthURL(String unid) {

		String googleLoginURL = "";

		try {
			googleLoginURL = "https://accounts.google.com/o/oauth2/auth?client_id=" + CLIENT_Id + "&redirect_uri="
					+ URLEncoder.encode(Redirect_URI, "UTF-8") + "&state=" + unid
					+ "&response_type=code&scope=profile email&approval_prompt=force&access_type=offline";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// System.out.println("inside google authentication" + googleLoginURL);
		return googleLoginURL;
	}

	public static String getAccessToken(String authCode) {

		String accessTokenURL = "https://accounts.google.com/o/oauth2/token";
		// System.out.println(accessTokenURL);
		ResteasyClient restCall = new ResteasyClientBuilder().build();
		// System.out.println(restCall);
		ResteasyWebTarget target = restCall.target(accessTokenURL);
		// System.out.println(target);
		Form f = new Form();
		f.param("client_id", CLIENT_Id);
		f.param("client_secret", Secret_Id);
		f.param("redirect_uri", Redirect_URI);
		f.param("code", authCode);
		f.param("grant_type", "authorization_code");
		// using post request we are sending an data to web service and getting
		// json response back
		Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(f));

		String token = response.readEntity(String.class);

		ObjectMapper mapper = new ObjectMapper();
		String acc_token = null;
		try {
			acc_token = mapper.readTree(token).get("access_token").asText();
		} catch (IOException e) {
			e.printStackTrace();
		}
		restCall.close();
		return acc_token;
	}

	public static JsonNode getUserProfile(String accessToken) {

		 System.out.println("gmail details " + Gmail_GET_USER_URL);
		ResteasyClient restCall = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = restCall.target(Gmail_GET_USER_URL);

		String headerAuth = "Bearer " + accessToken;
		Response response = target.request().header("Authorization", headerAuth).accept(MediaType.APPLICATION_JSON)
				.get();

		String profile = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode Googleprofile = null;
		try {
			Googleprofile = mapper.readTree(profile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		restCall.close();
		return Googleprofile;
	}
}
