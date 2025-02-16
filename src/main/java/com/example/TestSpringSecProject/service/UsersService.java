package com.example.TestSpringSecProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.TestSpringSecProject.model.Users;
import com.example.TestSpringSecProject.repo.UserRepo;

@Service
public class UsersService {
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Autowired
	JWTService jwtService;
	
	@Autowired
	AuthenticationProvider authProvider;
	
	UserRepo repo;
	public UsersService(@Autowired UserRepo r) {
		this.repo=r;
	}
	
	public Users register(Users user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return repo.save(user);
	}

	public String verify(Users user) {
		Authentication authentication = 
				authProvider.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if(authentication.isAuthenticated())
			return jwtService.generateToken(user.getUsername());
		else
			return "Failed";
	}
}
