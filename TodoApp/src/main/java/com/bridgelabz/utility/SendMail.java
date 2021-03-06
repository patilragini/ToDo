package com.bridgelabz.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SendMail {
	// interface MailSender
	
	@Autowired
	private static MailSender mailSender;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public static void sendMail(String to, String subject, String msg) {
		SimpleMailMessage message = new SimpleMailMessage();	
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		System.out.println("Sending mail...");
		try {
			mailSender.send(message);
			System.out.println("Mail Send Scussfully!!!");
		} catch (Exception e) {
			System.out.println("Mail not send");
		}

	}

}
