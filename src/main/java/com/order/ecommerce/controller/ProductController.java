package com.order.ecommerce.controller;

import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.entity.ProductCategory;
import com.order.ecommerce.enums.Role;
import com.order.ecommerce.exception.AuthenticationFailedException;
import com.order.ecommerce.exception.CustomValidationException;
import com.order.ecommerce.service.IAuthenticationService;
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
@RequestMapping("/api/v1/products")
public class ProductController {

    private final IProductService productService;
    private final IUserService userService;
    private final IAuthenticationService authenticationService;

    /**
     * Creates a product
     * @param productDto
     * @return
     */
    @PostMapping
    @Operation(summary = "Create a product", description = "Create a product")
    public ProductDto createProduct(@RequestBody ProductDto productDto, @RequestParam("token") String token) {
        validateArgument(productDto);
        /*
        NEW CODE ::
        Only SUPER or ADMIN user can create a new product.
         */
        boolean validRole = userService.getRoles(token)
                .stream()
                .anyMatch(role -> role.equals(Role.SUPER.toString()) || role.equals(Role.ADMIN.toString()));
        if(!validRole){
            throw new AuthenticationFailedException("ONLY SUPER OR ADMIN ROLE USER CAN CREATE A PRODUCT.");
        }
        /*
        NEW CODE :: ENDS HERE
         */
        return productService.createProduct(productDto);
    }

    /**
     * Finds all products
     * @return
     */
    @GetMapping
    @Operation(summary = "Find all products", description = "Find all products")
    public List<ProductDto> getAllProducts(@RequestParam("token") String token) {
        authenticationService.verifyTokenAndUserStatus(token);
        return productService.getAllProducts();
    }

    /**
     * Finds product by id
     * @param productId
     * @return
     */
    @GetMapping("/{productId}")
    @Operation(summary = "Find a product", description = "Find a product by id")
    public ProductDto findProductById(@PathVariable(name = "productId") String productId,
                                      @RequestParam("token") String token) {
        validateArgument(productId == null || productId.isEmpty(), "Product Id cannot be null or empty");
        authenticationService.verifyTokenAndUserStatus(token);
        return productService.findProductById(productId);
    }

    private void validateArgument(ProductDto productDto) {
        validateArgument(productDto == null, "Product cannot be null");
        validateArgument(productDto.getProductId() == null || productDto.getProductId().isEmpty(), "Product Id cannot be null or empty");
        validateArgument(productDto.getSku() == null || productDto.getSku().isEmpty(), "Product Sku cannot be null or empty");
        validateArgument(productDto.getTitle() == null || productDto.getTitle().isEmpty(), "Product Title cannot be null");
        validateArgument(productDto.getDescription() == null || productDto.getDescription().isEmpty(), "Product Description cannot be empty");
        validateArgument(productDto.getProductCategory() == null || productDto.getProductCategory().isEmpty(), "Product Category cannot be empty");
    }

    private void validateArgument(boolean condition, String message) {
        if (condition) {
            log.error("Error while processing request with message = {}", message);
            throw new CustomValidationException(message);
        }
    }
}