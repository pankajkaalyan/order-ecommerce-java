package com.order.ecommerce.service.impl;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import com.order.ecommerce.dto.user.EmailDto;
import com.order.ecommerce.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Transactional
public class EmailService implements IEmailService {
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendMail(EmailDto emailDto) {
		String flag = "failure";
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo(emailDto.getEmailTo());
			helper.setText(emailDto.getEmailBody());
			helper.setSubject(emailDto.getSubject());
			helper.setPriority(1);
			helper.setSentDate(new Date());
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
