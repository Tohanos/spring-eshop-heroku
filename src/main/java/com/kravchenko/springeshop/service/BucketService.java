package com.kravchenko.springeshop.service;

import com.kravchenko.springeshop.domain.Bucket;
import com.kravchenko.springeshop.domain.User;
import com.kravchenko.springeshop.dto.BucketDTO;

import java.util.List;

public interface BucketService {
	Bucket createBucket(User user, List<Long> productIds);

	void addProducts(Bucket bucket, List<Long> productIds);

	void deleteProduct(Bucket bucket, Long productId);

	BucketDTO getBucketByUser(String name);

	void commitBucketToOrder(String username);
}
