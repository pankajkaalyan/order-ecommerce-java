package com.order.ecommerce.service;

import com.order.ecommerce.entity.AppUser;
import com.order.ecommerce.entity.AuthenticationToken;

public interface IAuthenticationService {
    void saveConfirmationToken(AuthenticationToken authenticationToken);
    AuthenticationToken findByToken(String code);

    AuthenticationToken findTokenByUser(AppUser user);

    void authenticateToken(String token);
    void verifyTokenAndUserStatus(String token);
}
