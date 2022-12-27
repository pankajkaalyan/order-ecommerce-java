package com.order.ecommerce.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponseDto {
    private String status;
    private String token;

}
