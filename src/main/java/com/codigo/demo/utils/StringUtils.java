package com.codigo.demo.utils;

public class StringUtils {
	public static boolean isNullOrEmpty(String inputString) {
		if (inputString != null && !inputString.isEmpty())
			return false;
		else
			return true;
	}
}
