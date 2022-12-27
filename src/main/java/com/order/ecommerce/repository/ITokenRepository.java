package com.order.ecommerce.repository;

import com.order.ecommerce.entity.AppUser;
import com.order.ecommerce.entity.AuthenticationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITokenRepository extends JpaRepository<AuthenticationToken, String> {
    AuthenticationToken findByToken(String code);
    AuthenticationToken findByUser(AppUser user);
}