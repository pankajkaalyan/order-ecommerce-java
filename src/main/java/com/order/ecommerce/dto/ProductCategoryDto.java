package com.order.ecommerce.dto;

import com.order.ecommerce.entity.Product;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ProductCategoryDto {

    @NotNull
    private String categoryId;
    @NotNull
    private String categoryName;

}
