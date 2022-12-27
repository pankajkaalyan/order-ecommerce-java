package com.order.ecommerce.service;

import com.order.ecommerce.dto.ResponseDto;
import com.order.ecommerce.dto.user.*;

import java.util.List;

public interface IUserService {
    SignUpResponseDto signUp(SignupRequestDto signupDto);
    ResponseDto activate(String code);
    List<UserDto> findAll();

    SignInResponseDto signIn(SignInRequestDto signInDto);

    ResponseDto updateUser(String token, String oldPassword, String newPassword);

    List<String> getRoles(String token);
}
