package com.example.TestSpringSecProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.TestSpringSecProject.model.MyUserDetails;
import com.example.TestSpringSecProject.model.Users;
import com.example.TestSpringSecProject.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	UserRepo repo;
	
	@Autowired
	public MyUserDetailsService(UserRepo usr) {
		repo=usr;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user1= repo.findByUsername(username);
		if(user1==null)
			throw new UsernameNotFoundException("User not found");

		return new MyUserDetails(user1);
	}

}
