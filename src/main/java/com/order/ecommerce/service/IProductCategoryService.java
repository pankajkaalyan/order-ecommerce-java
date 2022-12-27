package com.order.ecommerce.service;

import com.order.ecommerce.dto.ProductCategoryDto;
import com.order.ecommerce.entity.ProductCategory;

import java.util.List;

public interface IProductCategoryService {
    List<ProductCategoryDto> getProductCategories();

    ProductCategoryDto findProductCategoryById(String productCategoryId);

    ProductCategoryDto addCategory(ProductCategoryDto productCategoryDto);
}
