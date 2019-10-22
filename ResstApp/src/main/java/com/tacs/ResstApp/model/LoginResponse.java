package com.tacs.ResstApp.model;

import lombok.Data;

@Data
public class LoginResponse {
	
	private Long userId;
	private String token;
	private boolean admin;
}
