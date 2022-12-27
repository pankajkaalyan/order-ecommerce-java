package com.order.ecommerce.service.impl;

import com.order.ecommerce.entity.AppUser;
import com.order.ecommerce.entity.AuthenticationToken;
import com.order.ecommerce.enums.UserStatus;
import com.order.ecommerce.exception.AuthenticationFailedException;
import com.order.ecommerce.repository.ITokenRepository;
import com.order.ecommerce.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final ITokenRepository tokenRepository;

    @Override
    @Transactional
    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }
    @Override
    @Transactional(readOnly = true)
    public AuthenticationToken findByToken(String code) {
       return tokenRepository.findByToken(code);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationToken findTokenByUser(AppUser user) {
        return tokenRepository.findByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public void authenticateToken(String token) throws AuthenticationFailedException {
        if (token==null || token.isBlank()) {
            log.error("Auth token is blank");
            throw new AuthenticationFailedException("AUTH_TOKEN_NOT_PRESENT");
        }
        AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
        if (authenticationToken == null || authenticationToken.getUser() == null) {
            log.error("Auth token is not valid");
            throw new AuthenticationFailedException("AUTH_TOKEN_NOT_VALID");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void verifyTokenAndUserStatus(String token) throws AuthenticationFailedException {
        if (token==null || token.isBlank()) {
            log.error("Auth token is blank");
            throw new AuthenticationFailedException("AUTH_TOKEN_NOT_PRESENT");
        }
        AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
        if (authenticationToken == null || authenticationToken.getUser() == null) {
            log.error("Auth token is not valid");
            throw new AuthenticationFailedException("AUTH_TOKEN_NOT_VALID");
        }
        AppUser user = authenticationToken.getUser();
        if(!user.getUserStatus().equals(UserStatus.ACTIVE)){
            log.error("UserStatus is not active. UserStatus is {}",user.getUserStatus());
            throw new AuthenticationFailedException("UserStatus is not active. UserStatus is " + user.getUserStatus());
        }
    }
}
