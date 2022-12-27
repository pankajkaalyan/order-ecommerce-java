package com.order.ecommerce.mapper;

import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "productCategory", ignore = true)
    Product toProductEntity(ProductDto productDto);

    @Mapping(source = "productCategory.categoryId" , target = "productCategory")
    ProductDto toProductDto(Product product);
}
