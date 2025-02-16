package com.example.TestSpringSecProject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class TestController {

	@GetMapping("/")
	public String greet(HttpServletRequest request) {
		return "hello world "+request.getCookies();
		
	}
	
}
