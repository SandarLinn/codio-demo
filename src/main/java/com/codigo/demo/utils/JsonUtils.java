package com.codigo.demo.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {
	public static String toJSON(Object object) {
		ObjectMapper jsonMapper = new ObjectMapper();
		try {
			return jsonMapper.writeValueAsString(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String prettyJSON(Object object) {
		ObjectMapper jsonMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

		try {
			return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
