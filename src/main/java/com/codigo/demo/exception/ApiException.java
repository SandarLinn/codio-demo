package com.codigo.demo.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String code;
	private String title;
	private String message;
	private Throwable cause;
	private HttpStatus status;

	public ApiException(String title, String code, String message) {
		super(message);
		this.title = title;
		this.message = message;
		this.code = code;
	}

	public ApiException(String title, String code, String message, Throwable cause, HttpStatus status) {
		super();
		this.code = code;
		this.title = title;
		this.message = message;
		this.cause = cause;
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
