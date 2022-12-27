package com.order.ecommerce.dto.user;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class SignUpResponseDto {
    @NotNull
    private String userId;
    @NotNull
    private String message;
    @NotNull
    private String status;
}
