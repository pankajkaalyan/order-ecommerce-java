package com.order.ecommerce.repository;

import com.order.ecommerce.entity.ProductCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductCategoryRepository extends CrudRepository<ProductCategory,String> {
}
