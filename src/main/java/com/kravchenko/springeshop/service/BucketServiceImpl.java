package com.kravchenko.springeshop.service;

import com.kravchenko.springeshop.dao.BucketRepository;
import com.kravchenko.springeshop.dao.ProductRepository;
import com.kravchenko.springeshop.domain.*;
import com.kravchenko.springeshop.dto.BucketDTO;
import com.kravchenko.springeshop.dto.BucketDetailDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService {
	private final BucketRepository bucketRepository;
	private final ProductRepository productRepository;
	private final UserService userService;
	private final OrderService orderService;

	public BucketServiceImpl(BucketRepository bucketRepository,
							 ProductRepository productRepository,
							 UserService userService,
							 OrderService orderService) {
		this.bucketRepository = bucketRepository;
		this.productRepository = productRepository;
		this.userService = userService;
		this.orderService = orderService;
	}

	@Override
	@Transactional
	public Bucket createBucket(User user, List<Long> productIds) {
		Bucket bucket = new Bucket();
		bucket.setUser(user);
		List<Product> productList = getCollectRefProductsByIds(productIds);
		bucket.setProducts(productList);
		return bucketRepository.save(bucket);
	}

	private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
		return productIds.stream()
				// getOne вытаскивает ссылку на объект, findById - вытаскивает сам объект
				.map(productRepository::getOne)
				.collect(Collectors.toList());
	}

	@Override
	public void addProducts(Bucket bucket, List<Long> productIds) {
		List<Product> products = bucket.getProducts();
		List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
		newProductList.addAll(getCollectRefProductsByIds(productIds));
		bucket.setProducts(newProductList);
		bucketRepository.save(bucket);
	}

	@Override
	public void deleteProduct(Bucket bucket, Long productId) {
		List<Product> products = bucket.getProducts();
		products.remove(products.stream()
				.filter(product -> productId.equals(product.getId()))
				.findAny()
				.orElse(null));
		bucket.setProducts(products);
		bucketRepository.save(bucket);
	}

	@Override
	public BucketDTO getBucketByUser(String name) {
		User user = userService.findByName(name);
		if (user == null || user.getBucket() == null) {
			return new BucketDTO();
		}

		BucketDTO bucketDTO = new BucketDTO();
		Map<Long, BucketDetailDTO> mapByProductId = new HashMap<>();

		List<Product> products = user.getBucket().getProducts();
		for (Product product : products) {
			BucketDetailDTO detail = mapByProductId.get(product.getId());
			if (detail == null) {
				mapByProductId.put(product.getId(), new BucketDetailDTO(product));
			} else {
				detail.setAmount(detail.getAmount().add(new BigDecimal(1.0)));
				detail.setSum(detail.getSum() + Double.valueOf(product.getPrice().toString()));
			}
		}

		bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
		bucketDTO.aggregate();

		return bucketDTO;
	}

	@Override
	@Transactional
	public void commitBucketToOrder(String username) {
		User user = userService.findByName(username);
		if(user == null){
			throw new RuntimeException("User is not found");
		}
		Bucket bucket = user.getBucket();
		if(bucket == null || bucket.getProducts().isEmpty()){
			return;
		}

		Order order = new Order();
		order.setStatus(OrderStatus.NEW);
		order.setUser(user);

		Map<Product, Long> productWithAmount = bucket.getProducts().stream()
				.collect(Collectors.groupingBy(product -> product, Collectors.counting()));

		List<OrderDetails> orderDetails = productWithAmount.entrySet().stream()
				.map(pair -> new OrderDetails(order, pair.getKey(), pair.getValue()))
				.collect(Collectors.toList());

		BigDecimal total = new BigDecimal(orderDetails.stream()
				.map(detail -> detail.getPrice().multiply(detail.getAmount()))
				.mapToDouble(BigDecimal::doubleValue).sum());

		order.setDetails(orderDetails);
		order.setSum(total);
		order.setAddress("none");

		orderService.saveOrder(order);
		bucket.getProducts().clear();
		bucketRepository.save(bucket);
	}
}