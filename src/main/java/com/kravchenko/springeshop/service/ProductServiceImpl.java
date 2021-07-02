package com.kravchenko.springeshop.service;

import com.kravchenko.springeshop.dao.ProductRepository;
import com.kravchenko.springeshop.domain.Bucket;
import com.kravchenko.springeshop.domain.Product;
import com.kravchenko.springeshop.domain.User;
import com.kravchenko.springeshop.dto.BucketDTO;
import com.kravchenko.springeshop.dto.ProductDTO;
import com.kravchenko.springeshop.mapper.ProductMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductMapper mapper = ProductMapper.MAPPER;

	private final ProductRepository productRepository;
	private final UserService userService;
	private final BucketService bucketService;
	private final SimpMessagingTemplate template;

	public ProductServiceImpl(ProductRepository productRepository, UserService userService, BucketService bucketService, SimpMessagingTemplate template) {
		this.productRepository = productRepository;
		this.userService = userService;
		this.bucketService = bucketService;
		this.template = template;
	}

	@Override
	public List<ProductDTO> getAll() {
		return mapper.fromProductList(productRepository.findAll());
	}

	@Override
	@Transactional
	public void addToUserBucket(Long productId, String username) {
		User user = userService.findByName(username);
		if (user == null) {
			throw new RuntimeException("User not found - " + username);
		}

		Bucket bucket = user.getBucket();
		if (bucket == null) {
			Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
			user.setBucket(newBucket);
			userService.save(user);
		} else {
			bucketService.addProducts(bucket, Collections.singletonList(productId));
		}
		updateUserBucket(username);
	}

	@Override
	@Transactional
	public void addProduct(ProductDTO dto) {
		Product product = mapper.toProduct(dto);
		Product savedProduct = productRepository.save(product);
		template.convertAndSend("/topic/products", mapper.fromProduct(savedProduct));
	}

	@Override
	public void updateUserBucket(String username) {
		template.convertAndSend("/topic/bucket", bucketService.getBucketByUser(username));
	}
}
