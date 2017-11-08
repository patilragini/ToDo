package com.bridgelabz.utility;

import java.security.MessageDigest;

public class MD5Encrypt {

	public static String encrypt(String password) {
		 String generatedPassword=null;
			try {
				MessageDigest md= MessageDigest.getInstance("MD5");
				md.update(password.getBytes());
				byte[] bytes = md.digest();
				System.out.println(bytes);
				StringBuilder stringBulder = new StringBuilder();
				for(int i=0;i<bytes.length;i++) {
					stringBulder.append(Integer.toString((bytes[i] & 0xff)+ 0x100,16).substring(1));
				}
				System.out.println(stringBulder);
				generatedPassword = stringBulder.toString();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return generatedPassword;
	}
}
