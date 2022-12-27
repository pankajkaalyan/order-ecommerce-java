package com.order.ecommerce.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "ecommerce_product_category")
public class ProductCategory implements Serializable {

    @Id
    @Column(name = "category_id", nullable = false, unique = true)
    private String categoryId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productCategory")
    private List<Product> product;
}
