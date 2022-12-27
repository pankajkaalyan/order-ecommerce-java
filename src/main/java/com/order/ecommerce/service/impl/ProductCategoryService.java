package com.order.ecommerce.service.impl;

import com.order.ecommerce.dto.ProductCategoryDto;
import com.order.ecommerce.entity.ProductCategory;
import com.order.ecommerce.exception.ResourceNotFound;
import com.order.ecommerce.mapper.ProductCategoryMapper;
import com.order.ecommerce.repository.IProductCategoryRepository;
import com.order.ecommerce.service.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCategoryService implements IProductCategoryService {

    private final IProductCategoryRepository productCategoryRepository;
    private final ProductCategoryMapper productCategoryMapper = Mappers.getMapper(ProductCategoryMapper.class);

    @Override
    public List<ProductCategoryDto> getProductCategories() {
        List<ProductCategory> categorieslist = (List<ProductCategory>) productCategoryRepository.findAll();
        if (categorieslist == null || categorieslist.isEmpty()) {
            log.info("No category found");
            return null;
        }
        log.info("Successfully found {} categories", categorieslist.size());
        return categorieslist.stream().map(productCategoryMapper::toProductCategoryDto).collect(Collectors.toList());
    }

    @Override
    public ProductCategoryDto findProductCategoryById(String productCategoryId) {
        Optional<ProductCategory> productCategory = productCategoryRepository.findById(productCategoryId);
        if(productCategory.isEmpty()){
            throw new ResourceNotFound("Invalid Product Category : " + productCategoryId);
        }
        return productCategoryMapper.toProductCategoryDto(productCategory.get());
    }

    @Override
    public ProductCategoryDto addCategory(ProductCategoryDto productCategoryDto) {
        log.info("Creating Product Category with categoryId = {}", productCategoryDto.getCategoryId());
        ProductCategory entity = productCategoryMapper.toProductCategoryEntity(productCategoryDto);
        entity.setCreatedAt(LocalDate.now());
        entity = productCategoryRepository.save(entity);
        log.info("Successfully saved category with id = {} on {}", entity.getCategoryId(), entity.getCreatedAt());
        productCategoryDto = productCategoryMapper.toProductCategoryDto(entity);
        return productCategoryDto;
    }
}
