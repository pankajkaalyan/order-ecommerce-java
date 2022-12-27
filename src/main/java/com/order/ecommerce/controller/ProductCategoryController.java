package com.order.ecommerce.controller;

import com.order.ecommerce.dto.ProductCategoryDto;
import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.entity.ProductCategory;
import com.order.ecommerce.enums.Role;
import com.order.ecommerce.exception.AuthenticationFailedException;
import com.order.ecommerce.exception.CustomValidationException;
import com.order.ecommerce.service.IAuthenticationService;
import com.order.ecommerce.service.IProductCategoryService;
import com.order.ecommerce.service.IProductService;
import com.order.ecommerce.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-category")
public class ProductCategoryController {

    private final IProductCategoryService productCategoryService;
    private final IUserService userService;
    private final IAuthenticationService authenticationService;

    /*
    Only ADMIN or MANAGER can create a Product Category.
     */
    @PostMapping
    @Operation(summary = "Add Product Categories", description = "Add a new product category")
    public ProductCategoryDto addCategory(@RequestBody ProductCategoryDto productCategoryDto,
                                          @RequestParam("token") String token){
        validateArgument(productCategoryDto.getCategoryId() == null || productCategoryDto.getCategoryId().isEmpty(), "Product Category Id cannot be null or empty");
        validateArgument(productCategoryDto.getCategoryName() == null || productCategoryDto.getCategoryName().isEmpty(), "Product Category Name cannot be null or empty");
        authenticationService.authenticateToken(token);
        boolean validRole = userService.getRoles(token).stream()
                .anyMatch(role -> role.equals(Role.ADMIN.toString()) || role.equals(Role.MANAGER.toString()));
        if(!validRole){
            throw new AuthenticationFailedException("ONLY ADMIN OR MANAGER ROLE USER CAN CREATE A PRODUCT CATEGORY");
        }
        return productCategoryService.addCategory(productCategoryDto);
    }


    @GetMapping
    @Operation(summary = "Get All Product Categories", description = "Get all product categories")
    public List<ProductCategoryDto> getProductCategories(@RequestParam("token") String token) {
        authenticationService.verifyTokenAndUserStatus(token);
        return productCategoryService.getProductCategories();
    }

    /**
     * Finds product by id
     * @param productId
     * @return
     */
    @GetMapping("/{productCategoryId}")
    @Operation(summary = "Find a product category", description = "Find a product category by id")
    public ProductCategoryDto findProductById(@PathVariable(name = "productCategoryId") String productCategoryId,
                                              @RequestParam("token") String token) {
        validateArgument(productCategoryId == null || productCategoryId.isEmpty(), "Product Category Id cannot be null or empty");
        authenticationService.verifyTokenAndUserStatus(token);
        return productCategoryService.findProductCategoryById(productCategoryId);
    }

    private void validateArgument(ProductDto productDto) {
        validateArgument(productDto == null, "Product cannot be null");
        validateArgument(productDto.getProductId() == null || productDto.getProductId().isEmpty(), "Product Id cannot be null or empty");
        validateArgument(productDto.getSku() == null || productDto.getSku().isEmpty(), "Product Sku cannot be null or empty");
        validateArgument(productDto.getTitle() == null || productDto.getTitle().isEmpty(), "Product Title cannot be null");
        validateArgument(productDto.getDescription() == null || productDto.getDescription().isEmpty(), "Product Description cannot be empty");
    }

    private void validateArgument(boolean condition, String message) {
        if (condition) {
            log.error("Error while processing request with message = {}", message);
            throw new CustomValidationException(message);
        }
    }
}