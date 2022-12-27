package com.order.ecommerce.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "ecommerce_product")
public class Product implements Serializable {

    @Id
    @Column(name = "product_id", nullable = false, unique = true)
    private String productId;

    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "createdAt", nullable = false)
    private LocalDate createdAt;

    @OneToMany(targetEntity = OrderItem.class, fetch = FetchType.LAZY, mappedBy = "product")
    private List<OrderItem> orderItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "category_id", name = "category_id")
    private ProductCategory productCategory;
}
