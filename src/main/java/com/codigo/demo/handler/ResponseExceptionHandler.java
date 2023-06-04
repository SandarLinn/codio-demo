package com.codigo.demo.handler;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.secure.spi.IntegrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.codigo.demo.exception.ApiException;
import com.codigo.demo.utils.JsonUtils;

@RestController
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ ApiException.class, IntegrationException.class})
	public ResponseEntity<?> handleThrowableBusinessException(final ApiException ex) {
		HttpStatus status = ex.getStatus() == null ? HttpStatus.BAD_REQUEST : ex.getStatus();
		return new ResponseEntity<>(json(ex), status);
	}

	private String json(ApiException ex) {
		final Map<String, Object> result = new LinkedHashMap<>();
		result.put("code", ex.getCode());
		result.put("title", ex.getTitle());
		result.put("message", ex.getMessage());
		return JsonUtils.prettyJSON(result);
	}

}