package com.codigo.demo.utils;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class PromoCodeUtils {
	private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String NUMERIC = "0123456789";

	public static String generatePromoCode() {

		int alphaLenth = 5;
		int numericLength = 6;
		SecureRandom random = new SecureRandom();
		StringBuilder builder = new StringBuilder(alphaLenth + numericLength);

		for (int i = 0; i < alphaLenth; i++) {
			builder.append(ALPHA.charAt(random.nextInt(ALPHA.length())));
		}

		for (int i = 0; i < numericLength; i++) {
			builder.append(NUMERIC.charAt(random.nextInt(NUMERIC.length())));
		}

		return builder.toString();
	}
}
