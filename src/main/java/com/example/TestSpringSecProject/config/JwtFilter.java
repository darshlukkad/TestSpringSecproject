package com.example.TestSpringSecProject.config;

import java.io.IOException;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.TestSpringSecProject.service.JWTService;
import com.example.TestSpringSecProject.service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter  extends OncePerRequestFilter {

	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private MyUserDetailsService userDetailsService;

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String authHeader = request.getHeader("Authorization");
		String username=null;
		String token=null;
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			token= authHeader.substring(7);
			username=jwtService.extractUsername(token);
		}
		
		if(username !=null & SecurityContextHolder.getContext().getAuthentication() ==null) {
			try {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				if(jwtService.validateToken(token,userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		filterChain.doFilter(request, response);
		
		
	}

}
