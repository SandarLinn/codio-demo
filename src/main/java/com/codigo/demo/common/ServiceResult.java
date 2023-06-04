package com.codigo.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author sandar linn
 * @CreatedAt June 3, 2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResult<T> {

	private T result;
	private boolean status = Boolean.FALSE;
	private String code;
	private String message;
	private Throwable throwable;

	public void success(T result) {
		this.result = result;
		this.status = Boolean.TRUE;
		this.code = "200";
		this.message = "success";
		this.throwable = null;
	}

	public void fail(Throwable throwable, String message) {
		this.result = null;
		this.status = Boolean.FALSE;
		this.code = "400";
		this.message = message;
		this.throwable = throwable;
	}

	public void fail(Throwable throwable, String message, String code) {
		this.result = null;
		this.status = Boolean.FALSE;
		this.message = message;
		this.throwable = throwable;
		this.code = code;
	}

}
