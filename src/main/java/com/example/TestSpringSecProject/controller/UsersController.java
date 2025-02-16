package com.example.TestSpringSecProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.TestSpringSecProject.model.Users;
import com.example.TestSpringSecProject.service.UsersService;

@RestController
public class UsersController {

	UsersService service;
	
	public UsersController(@Autowired UsersService serv) {
		this.service=serv;
	}
	
	@PostMapping("/register")
	public Users addUser(@RequestBody Users user) {
		
		return service.register(user);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Users user) {
		
		return service.verify(user);
	}
	
}
