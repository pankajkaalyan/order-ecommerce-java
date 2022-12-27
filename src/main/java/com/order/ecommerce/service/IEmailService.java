package com.order.ecommerce.service;

import com.order.ecommerce.dto.user.EmailDto;

public interface IEmailService {
	void sendMail(EmailDto emailDto);
}
