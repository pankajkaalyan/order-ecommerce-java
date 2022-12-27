package com.order.ecommerce.entity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "ecommerce_token")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationToken {

    @Id
    @Column(name = "id")
    private String id;
    private String token;
    @Column(name = "createdAt")
    private LocalDate createdAt;
    @OneToOne(targetEntity = AppUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private AppUser user;
}
