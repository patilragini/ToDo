package com.bridgelabz.utility;

import java.util.Date;

import com.auth0.jwt.exceptions.JWTCreationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Token {
	private String key="ragini";
	public static String generateToken(String type,String email, int id) {
		String token="";
		try {
			long currentTime=System.currentTimeMillis();
			//2 days
			long expireTime=currentTime+(60000*60*24*2);
			Date date=new Date(currentTime);
			Date expireDate=new Date(expireTime);
		  
		    token =Jwts.builder().setId(String.valueOf(id))
                    .setIssuedAt(date)
                    .signWith(SignatureAlgorithm.HS256,key)
                    .setExpiration(expireDate)
                    .compact();
		    System.out.println("Token :: "+token+ "Type:: " +type);
		}  catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
		return token;
	}
	
	public static final String  key="ragini";
	public static int verifyToken(String accessToken ) {
		try {
			JwtParser parser = Jwts.parser();
			Claims claims = parser.setSigningKey(key).parseClaimsJws(accessToken).getBody();
			return Integer.parseInt(claims.getIssuer());			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	
}
