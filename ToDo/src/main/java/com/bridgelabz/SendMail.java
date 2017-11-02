package com.bridgelabz;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SendMail {
	//interface MailSender
	
	private MailSender mailSender;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendMail(String from,String to,String subject,String msg){
		SimpleMailMessage message= new SimpleMailMessage();		
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);
		System.out.println("Mail Send Scussfully!!!");
	}
	
}
