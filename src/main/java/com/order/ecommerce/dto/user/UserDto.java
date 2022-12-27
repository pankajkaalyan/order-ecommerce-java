package com.order.ecommerce.dto.user;

import com.order.ecommerce.enums.Role;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserDto {

    private String id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private Role role;
    @NotNull
    private String password;
}
