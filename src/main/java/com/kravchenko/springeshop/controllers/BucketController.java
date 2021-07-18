package com.kravchenko.springeshop.controllers;

import com.kravchenko.springeshop.dto.BucketDTO;
import com.kravchenko.springeshop.service.BucketService;
import com.kravchenko.springeshop.service.ProductService;
import com.kravchenko.springeshop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class BucketController {

	private final BucketService bucketService;
	private final UserService userService;
	private final ProductService productService;

	public BucketController(BucketService bucketService, UserService userService, ProductService productService) {
		this.bucketService = bucketService;
		this.userService = userService;
		this.productService = productService;
	}

	@GetMapping("/bucket")
	public String aboutBucket(Model model, Principal principal){
		if(principal == null) {
			model.addAttribute("bucket", new BucketDTO());
		}
		else {
			BucketDTO bucketDto = bucketService.getBucketByUser(principal.getName());
			model.addAttribute("bucket", bucketDto);
		}

		return "bucket";
	}

	@PostMapping("/bucket")
	public String commitBucket(Principal principal){
		if(principal != null) {
			bucketService.commitBucketToOrder(principal.getName());
		}
		return "redirect:/bucket";
	}

	@GetMapping("/bucket/{id}/delete")
	public String pageDeleteFromBucket(@PathVariable Long id, Principal principal) {
		if(principal != null) {
			bucketService.deleteProduct(userService.findByName(principal.getName()).getBucket(), id);
			productService.updateUserBucket(principal.getName());
		}
		return "redirect:/bucket";
	}

}

