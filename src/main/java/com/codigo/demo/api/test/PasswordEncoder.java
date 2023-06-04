package com.codigo.demo.api.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

	public static void main(String args[]) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		var encode = passwordEncoder.encode("sandar");
		System.out.println(encode);

	}

}
