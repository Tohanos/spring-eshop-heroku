package com.kravchenko.springeshop.controllers;

import com.kravchenko.springeshop.dto.ProductDTO;
import com.kravchenko.springeshop.service.ProductService;
import com.kravchenko.springeshop.service.SessionObjectHolder;
import com.kravchenko.springeshop.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;
	private final WeatherService weatherService;
	private final SessionObjectHolder sessionObjectHolder;

	public ProductController(ProductService productService, WeatherService weatherService, SessionObjectHolder sessionObjectHolder) {
		this.productService = productService;
		this.weatherService = weatherService;
		this.sessionObjectHolder = sessionObjectHolder;
	}

	@GetMapping
	public String list(Model model, Principal principal) {
		sessionObjectHolder.addClick();
		List<ProductDTO> list = productService.getAll();
		model.addAttribute("products", list);
		if (principal != null) {
			productService.updateUserBucket(principal.getName());
		}
		return "products";
	}

	@GetMapping("/{id}/bucket")
	public String addBucket(@PathVariable Long id, Principal principal) {
		sessionObjectHolder.addClick();
		if (principal == null) {
			return "redirect:/products";
		}
		productService.addToUserBucket(id, principal.getName());
		return "redirect:/products";
	}

	@PostMapping
	public ResponseEntity<Void> addProduct(ProductDTO dto) {
		productService.addProduct(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@MessageMapping("/products")
	public void messageAddProduct(ProductDTO dto) {
		productService.addProduct(dto);
	}

	@MessageMapping("/bucket")
	public void messageAddBucket(@PathVariable Long id, Principal principal) {
		if (principal != null) {
			productService.addToUserBucket(id, principal.getName());
		}
	}

	@MessageMapping("/menu")
	public void messageUpdateMenu(Principal principal) {
		weatherService.sendWeather(weatherService.currentWeather());
		if (principal != null) {
			productService.updateUserBucket(principal.getName());
		}
	}

}
