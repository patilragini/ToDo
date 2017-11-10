package com.bridgelabz.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encryption{
	/*public static void main(String[] args) {
		String plainText = "password123";
		String decrypt=encrypt(plainText);
		System.out.println("Decrypted password:"+decrypt);			
		}*/

	public static String encrypt(String plainText) {
		String cypherText = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("SHA");
			// Add password bytes to digest
			md.update(plainText.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			cypherText = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println(cypherText);
		return cypherText;

	}

}