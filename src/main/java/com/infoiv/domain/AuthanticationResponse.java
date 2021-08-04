package com.infoiv.domain;

public class AuthanticationResponse {
	
	private final String jwt;

	public AuthanticationResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}
	
	
	

}
