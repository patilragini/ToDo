package com.bridgelabz.utility;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.bridgelabz.Model.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;

public class Token {
	private static String Key = "mykey";

	public static String generateToken(String email, int id) {
		String token = "";
		long currentTime = System.currentTimeMillis();
		// 2 days
		long expireTime = currentTime + (60000 * 60 * 24 * 2);
		Date date = new Date(currentTime);
		Date expireDate = new Date(expireTime);

		token = Jwts.builder().setId(String.valueOf(id)).setIssuedAt(date).signWith(SignatureAlgorithm.HS256, Key)
				.setExpiration(expireDate).compact();
		System.out.println("Token :: " + token);
		return token;

	}

	public static int verify(String jwt) throws ExpiredJwtException {
		// This line will throw an exception if it is not a signed JWS (as
		try {
			Claims claims = Jwts.parser().setSigningKey(Key).parseClaimsJws(jwt).getBody();
			if (claims.isEmpty())
				return 0;
			else {
				System.out.println("ID: " + claims.getId());
				System.out.println("Subject: " + claims.getSubject());
				System.out.println("Issuer: " + claims.getIssuer());
				System.out.println("Expiration: " + claims.getExpiration());
				return Integer.parseInt(claims.getId());
			}

		} catch (ExpiredJwtException e) {
			System.out.println("Token expired!!!");
			return 0;
		}catch (MissingClaimException e) {
			e.printStackTrace();
		    return  0;
		} catch (Exception e) {
			e.printStackTrace();
			 return 0;
		}
	}

	/*
	 * public static void main(String[] args) { UserDetails user = null; String
	 * token=Token.generateToken("ragini@gmail.com", 2);
	 * System.out.println(token); System.out.println("HERE"+token);
	 * System.out.println(verify(token)); }
	 */

}