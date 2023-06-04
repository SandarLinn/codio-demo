package com.codigo.demo.api.authentication.Request;

import lombok.Data;

@Data
public class UserRequest {

	private String name;
	private String password;
	private String loginName;
	private String phone;
	private String email;

}
