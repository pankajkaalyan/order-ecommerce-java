package com.order.ecommerce.repository;

import com.order.ecommerce.entity.Address;
import com.order.ecommerce.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByEmail(String email);
}