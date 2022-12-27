package com.order.ecommerce.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignInRequestDto {
    private String email;
    private String password;
}
