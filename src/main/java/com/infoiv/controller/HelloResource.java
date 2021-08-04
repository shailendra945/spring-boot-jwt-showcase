package com.infoiv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infoiv.domain.AuthanticationRequest;
import com.infoiv.domain.AuthanticationResponse;
import com.infoiv.utils.JwtUtil;

@RestController
public class HelloResource {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private UserDetailsService userDetService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@RequestMapping("/hello")
	public String hellow() {
		return "hello world";
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthanticationToken(@RequestBody AuthanticationRequest authRequest ){
		try {
			this.authManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							authRequest.getUsername(), authRequest.getPassword()));
		} catch (BadCredentialsException bce) {
			System.err.println(bce);
			throw new RuntimeException("Incorrect Username or Password.");
		}
		final UserDetails user = this.userDetService.loadUserByUsername(authRequest.getUsername());
		return ResponseEntity.ok(new AuthanticationResponse(jwtUtil.generateToken(user)));
		
	}
}
