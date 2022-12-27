package com.order.ecommerce.mapper;

import com.order.ecommerce.dto.ProductCategoryDto;
import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.entity.Product;
import com.order.ecommerce.entity.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductCategoryMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "product" , ignore = true)
    ProductCategory toProductCategoryEntity(ProductCategoryDto productCategoryDto);

    //@Mapping(target = "", source = "createdAt", ignore = true)
    ProductCategoryDto toProductCategoryDto(ProductCategory productCategory);
}
