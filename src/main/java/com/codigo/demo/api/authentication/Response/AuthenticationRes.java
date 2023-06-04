package com.codigo.demo.api.authentication.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationRes {
	private String email;
	private String accessToken;
}
