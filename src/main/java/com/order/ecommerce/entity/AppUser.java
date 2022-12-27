package com.order.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.order.ecommerce.dto.OrderDto;
import com.order.ecommerce.enums.Role;
import com.order.ecommerce.enums.UserStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "ecommerce_user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AppUser {
    @Id
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "userStatus")
    private UserStatus userStatus;

//    @JsonIgnore
//    @OneToMany(mappedBy = "user",
//            fetch = FetchType.LAZY)
//    private List<OrderDto> orders;

}

