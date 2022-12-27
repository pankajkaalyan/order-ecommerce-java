package com.order.ecommerce.service.impl;

import com.order.ecommerce.dto.ProductCategoryDto;
import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.entity.Product;
import com.order.ecommerce.entity.ProductCategory;
import com.order.ecommerce.exception.ResourceNotFound;
import com.order.ecommerce.mapper.ProductMapper;
import com.order.ecommerce.repository.IProductCategoryRepository;
import com.order.ecommerce.repository.IProductRepository;
import com.order.ecommerce.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final IProductCategoryRepository productCategoryRepository;
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Creating Product with productId = {}", productDto.getProductId());
        Product entity = productMapper.toProductEntity(productDto);
        entity.setCreatedAt(LocalDate.now());
        /*
        NEW CODE ::
        logic for checking product category for provided product.
         */
        ProductCategory productCategory = productCategoryRepository.findById(productDto.getProductCategory())
                .orElseThrow(() -> new ResourceNotFound("Invalid Category Id : " + productDto.getProductCategory()));
        entity.setProductCategory(productCategory);
        /*
        NEW CODE ENDS HERE
         */
        Product savedProduct = productRepository.save(entity);
        log.info("Successfully saved product with id = {} on {}", savedProduct.getProductId(), savedProduct.getCreatedAt());
        ProductDto x = productMapper.toProductDto(savedProduct);
        return x;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto findProductById(String productId) {
        log.info("Finding product for productId = {}", productId);
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            log.info("Cannot find product with id = {}", productId);
            throw  new ResourceNotFound("PRODUCT NOT FOUND :: " + productId);
        }
        log.info("Successfully found product for productId = {}", productId);
        return productMapper.toProductDto(product.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findAllById(List<String> ids) {
        log.info("Finding products for ids = {}", ids);
        List<Product> productList = (List<Product>) productRepository.findAllById(ids);
        if (productList == null || productList.isEmpty()) {
            log.info("No product(s) found for ids = {}", ids);
            return null;
        }
        log.info("Successfully found {} products", productList.size());
        return productList.stream().map(productMapper::toProductDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        List<Product> list = (List<Product>) productRepository.findAll();
        if(list.isEmpty()){
            log.info("No product(s) found");
            return null;
        }
        log.info("Successfully found {} products", list.size());
        return list.stream().map(productMapper::toProductDto).toList();
    }
}
