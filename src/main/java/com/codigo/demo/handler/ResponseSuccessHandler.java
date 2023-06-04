package com.codigo.demo.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.codigo.demo.utils.JsonUtils;

import lombok.Data;

@Data
public class ResponseSuccessHandler {
	private String code = "200";
	private String message = "Success";
	private Object body;

	public ResponseEntity<String> response() {
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("code", this.code);
		responseMap.put("message", this.message);
		responseMap.put("data", this.body);
		return new ResponseEntity<>(JsonUtils.prettyJSON(responseMap), header, HttpStatus.OK);
	}
}
