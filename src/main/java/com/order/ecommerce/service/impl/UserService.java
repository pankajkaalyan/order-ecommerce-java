package com.order.ecommerce.service.impl;

import com.order.ecommerce.dto.ResponseDto;
import com.order.ecommerce.dto.user.*;
import com.order.ecommerce.entity.AppUser;
import com.order.ecommerce.entity.AuthenticationToken;
import com.order.ecommerce.enums.Role;
import com.order.ecommerce.enums.UserStatus;
import com.order.ecommerce.exception.AuthenticationFailedException;
import com.order.ecommerce.exception.DuplicateResourceFound;
import com.order.ecommerce.exception.InvalidStatusException;
import com.order.ecommerce.mapper.UserMapper;
import com.order.ecommerce.repository.IUserRepository;
import com.order.ecommerce.service.IAuthenticationService;
import com.order.ecommerce.service.IEmailService;
import com.order.ecommerce.service.IUserService;
import com.order.ecommerce.util.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    private final IAuthenticationService authenticationService;
    private final IEmailService emailService;
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);


    @Override
    @Transactional
    public SignUpResponseDto signUp(SignupRequestDto signupDto) {
        log.info("checking email for existing user = {}", signupDto.getEmail());
        Optional<AppUser> optionalUser = userRepository.findByEmail(signupDto.getEmail());
        if(!optionalUser.isEmpty()){
            log.error("User with this emailId have already been registered : {}} ",signupDto.getEmail());
            throw new DuplicateResourceFound("User with this emailId have already been registered : " + signupDto.getEmail());
        }

        String encryptedPassword = hashPassword(signupDto.getPassword());
        String userId = "" + signupDto.getFirstName().toUpperCase().charAt(0) +
                        signupDto.getLastName().toUpperCase().charAt(0) +
                        String.format("%08d", new Random().nextInt(99999999));
        AppUser user = AppUser.builder()
                .id(userId)
                .firstName(signupDto.getFirstName())
                .lastName(signupDto.getLastName())
                .email(signupDto.getEmail())
                .password(encryptedPassword)
                .userStatus(UserStatus.INACTIVE)
                .role(Role.USER)
                .build();
        log.info("creating user with id :: {}",user);
        userRepository.save(user);

        String tokenId = "TKN" + "-" + String.format("%08d", new Random().nextInt(99999999));
        final AuthenticationToken authenticationToken = AuthenticationToken.builder()
                .id(tokenId)
                .createdAt(LocalDate.now())
                .user(user)
                .token(UUID.randomUUID().toString())
                .build();
        log.info("creating authentication token for user :: {}",user);
        authenticationService.saveConfirmationToken(authenticationToken);

        EmailDto email = EmailDto.builder()
                .emailTo(signupDto.getEmail())
                .emailBody("Welcome to ecommerce platform.\nPlease find the verification token to activate your account.\nAuthentication token is :: " + authenticationToken.getToken())
                .dateTime(LocalDateTime.now())
                .subject("Verification Email")
                .build();
        emailService.sendMail(email);
        log.info("Email Sent to :: {}", signupDto.getEmail());
        return SignUpResponseDto.builder()
                .userId(userId)
                .message(AppConstants.NEW_USER_CREATED_MSG)
                .status("SUCCESS")
                .build();
    }

    @Override
    @Transactional
    public ResponseDto activate(String code) {
        log.info("verifying token" ,code);
        AuthenticationToken token =  authenticationService.findByToken(code);
        AppUser user = token.getUser();
        UserStatus status = user.getUserStatus();
        String message = null;
        switch (status){
            case BLOCKED -> {
                message = AppConstants.USER_IS_BLOCKED_TEMPORARILY;
            }
            case SUSPENDED -> {
                message = AppConstants.USER_IS_SUSPENDED;
            }
            case ACTIVE -> {
                message = AppConstants.USER_IS_ALREADY_ACTIVATED;
            }
            case INACTIVE -> {
                user.setUserStatus(UserStatus.ACTIVE);
                userRepository.save(user);
                message = AppConstants.USER_ACTIVATED;
            }
            default -> {
                log.error("User status is invalid :: {}",status );
                throw new InvalidStatusException("INVALID STATUS : " +status);
            }
        }
        log.info("response :: {}",message);
        return ResponseDto.builder()
                .message(message)
                .status("SUCCESS")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public SignInResponseDto signIn(SignInRequestDto signInDto) {
        log.info("verifying email :: {}",signInDto.getEmail());
        Optional<AppUser> optionalUser = userRepository.findByEmail(signInDto.getEmail());
        if(optionalUser.isEmpty()){
            log.error("Invalid email id :: {}",signInDto.getEmail());
            throw new AuthenticationFailedException("INVALID EMAIL ID : " + signInDto.getEmail());
        }
        AppUser user = optionalUser.get();
        if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
            log.error("Invalid password :: {}",signInDto.getPassword());
            throw  new AuthenticationFailedException("INVALID PASSWORD : " + signInDto.getPassword());
        }

        AuthenticationToken token = authenticationService.findTokenByUser(user);
        if(token==null || token.getToken()==null || token.getToken().isEmpty()) {
            log.error("Token not found");
            throw new AuthenticationFailedException("TOKEN NOT FOUND.");
        }
        log.info("Token fetched :: {}",token.getToken());
        return SignInResponseDto.builder()
                .token(token.getToken())
                .status("SUCCESS")
                .build();
    }

    @Override
    @Transactional
    public ResponseDto updateUser(String token, String oldPassword, String newPassword) {
        AuthenticationToken authenticationToken = authenticationService.findByToken(token);
        AppUser user = authenticationToken.getUser();
        if(!user.getPassword().equals(hashPassword(oldPassword))){
            log.error("old password doesn't match with new password.");
            throw new AuthenticationFailedException("OLD PASSWORD DOES NOT MATCH : " + oldPassword);
        }
        user.setPassword(hashPassword(newPassword));
        userRepository.save(user);
        log.info("password updated successfully.");
        return ResponseDto.builder()
                .message("PASSWORD CHANGED SUCCESSFULLY.")
                .status("SUCCESS")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getRoles(String token) {
        log.info("verifying token");
        AuthenticationToken authenticationToken = authenticationService.findByToken(token);
        if(authenticationToken == null || authenticationToken.getUser() == null){
            log.info("invalid token :: {}" ,token);
            throw new AuthenticationFailedException("INVALID TOKEN : " + token);
        }
        AppUser user = authenticationToken.getUser();
        log.info("fetching all roles for the token");
        return Arrays.asList(user.getRole().toString());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<AppUser> list =  userRepository.findAll();
        if(list.isEmpty()){
            log.info("No user(s) found");
            return null;
        }
        log.info("Successfully found {} users", list.size());
        return list.stream().map(userMapper::toUserDto).toList();
    }

    String hashPassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}
