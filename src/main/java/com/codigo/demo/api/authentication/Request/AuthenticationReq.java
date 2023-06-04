package com.codigo.demo.api.authentication.Request;

import lombok.Data;

@Data
public class AuthenticationReq {

	private String loginName;
	private String password;
}
