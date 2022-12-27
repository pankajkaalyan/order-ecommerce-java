package com.order.ecommerce.mapper;

import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.dto.user.UserDto;
import com.order.ecommerce.entity.AppUser;
import com.order.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "userStatus" , ignore = true)
    @Mapping(target = "role" , ignore = true)
    AppUser toUserEntity(UserDto userDto);
    UserDto toUserDto(AppUser user);
}
