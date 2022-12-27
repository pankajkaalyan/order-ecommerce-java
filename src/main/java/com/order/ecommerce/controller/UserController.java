package com.order.ecommerce.controller;

import com.order.ecommerce.dto.ResponseDto;
import com.order.ecommerce.dto.user.*;
import com.order.ecommerce.enums.Role;
import com.order.ecommerce.exception.AuthenticationFailedException;
import com.order.ecommerce.exception.CustomValidationException;
import com.order.ecommerce.exception.DuplicateResourceFound;
import com.order.ecommerce.service.IAuthenticationService;
import com.order.ecommerce.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService userService;
    private final IAuthenticationService authenticationService;

    @PostMapping("/signup")
    @Operation(summary = "Sign Up", description = "Sign up as a USER for ecommerce app")
    public ResponseEntity<SignUpResponseDto> Signup(@RequestBody SignupRequestDto signupDto) throws DuplicateResourceFound {
        validateSignUpDto(signupDto);
        SignUpResponseDto responseDto = userService.signUp(signupDto);
        log.info("user created with userId :: {} ",responseDto.getUserId());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/activate")
    @Operation(summary = "Activate a new user", description = "Activate a new user for ecommerce app")
    public ResponseEntity<ResponseDto> activate(@RequestParam(value = "code") String token) {
        log.info("validating token");
        authenticationService.authenticateToken(token);
        return new ResponseEntity<>(userService.activate(token), HttpStatus.OK);
    }

    @PostMapping("/signIn")
    @Operation(summary = "Sign In", description = "Login as user for ecommerce app")
    public ResponseEntity<SignInResponseDto> SignIn(@RequestBody SignInRequestDto signInDto) {
        validateSignInDto(signInDto);
        return new ResponseEntity<>(userService.signIn(signInDto),HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    @Operation(summary = "Change Password", description = "Change password for ecommerce app account.")
    public ResponseEntity<ResponseDto> changePassword(@RequestParam(value = "token") String token,
                                      @RequestParam(value = "oldPassword") String oldPassword,
                                      @RequestParam(value = "newPassword") String newPassword) {
        validatePassword(oldPassword,newPassword);
        log.info("validating token");
        authenticationService.verifyTokenAndUserStatus(token);
        return new ResponseEntity<>(userService.updateUser(token,oldPassword,newPassword),HttpStatus.OK);
    }

    @GetMapping("/role")
    @Operation(summary = "Get Roles of a user", description = "Get all roles of a user for ecommerce app")
    public ResponseEntity<List<String>> getRoles(@RequestParam("token") String token){
        log.info("verifying token");
        authenticationService.verifyTokenAndUserStatus(token);
        List<String> roles = userService.getRoles(token);
        log.info("Roles for the token provide :: {}",roles);
        return new ResponseEntity<>(roles,HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all users.",description = "Get all users. Only Super user can access this api.")
    public List<UserDto> getAllUsers(@RequestParam("token") String token){
        boolean validRole = userService.getRoles(token)
                                        .stream()
                                        .anyMatch(role -> role.equals(Role.SUPER.toString()));
        if(!validRole){
            throw new AuthenticationFailedException("ONLY SUPER ROLE USER CAN ACCESS THIS API");
        }
        return userService.findAll();
    }

    private void validatePassword(String oldPassword, String newPassword) {
        validateArgument(oldPassword == null || oldPassword.isEmpty(), "old password cannot be null or empty");
        validateArgument(newPassword == null || newPassword.isEmpty(), "new password cannot be null or empty");
        validateArgument(newPassword.equals(oldPassword), "old & new password cannot be same");
    }

    private void validateSignUpDto(SignupRequestDto signupDto) {
        validateArgument(signupDto == null, "SignupDto cannot be null");
        validateArgument(signupDto.getFirstName() == null || signupDto.getFirstName().isEmpty(), "First Name cannot be null or empty");
        validateArgument(signupDto.getLastName() == null || signupDto.getLastName().isEmpty(), "Last Name cannot be null or empty");
        validateArgument(signupDto.getEmail() == null || signupDto.getEmail().isEmpty(), "Email cannot be null");
        validateArgument(signupDto.getPassword() == null || signupDto.getPassword().isEmpty(), "Password cannot be empty");
    }
    private void validateSignInDto(SignInRequestDto signInRequestDto) {
        validateArgument(signInRequestDto == null, "SignInRequestDto cannot be null");
        validateArgument(signInRequestDto.getEmail() == null || signInRequestDto.getEmail().isEmpty(), "Email cannot be null or empty");
        validateArgument(signInRequestDto.getPassword() == null || signInRequestDto.getPassword().isEmpty(), "Password cannot be empty");
    }

    private void validateArgument(boolean condition, String message) {
        if (condition) {
            log.error("Error while processing request with message = {}", message);
            throw new CustomValidationException(message);
        }
    }

}
