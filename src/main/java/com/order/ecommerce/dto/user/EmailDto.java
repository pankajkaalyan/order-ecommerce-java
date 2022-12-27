package com.order.ecommerce.dto.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EmailDto {
    private String emailTo;
    private String emailFrom;
    private String subject;
    private String emailBody;
    private LocalDateTime dateTime;
}
