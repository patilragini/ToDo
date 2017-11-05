package com.bridgelabz.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SendMail {
	//interface MailSender
	@Autowired
	private static MailSender mailSender;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public static void sendMail(String from,String to,String subject,String msg){
		SimpleMailMessage message= new SimpleMailMessage();		
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		System.out.println("Sending mail...");
		mailSender.send(message);
		System.out.println("Mail Send Scussfully!!!");
	}

	
	
}
