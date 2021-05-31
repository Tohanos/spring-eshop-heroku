package com.kravchenko.springeshop.mapper;

import com.kravchenko.springeshop.domain.Product;
import com.kravchenko.springeshop.dto.ProductDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
	ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

	Product toProduct(ProductDTO dto);

	@InheritInverseConfiguration
	ProductDTO fromProduct(Product product);

	List<Product> toProductList(List<ProductDTO> productDTOS);

	List<ProductDTO> fromProductList(List<Product> products);
}