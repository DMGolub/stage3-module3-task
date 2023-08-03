package com.mjc.school.service.exception;

public class ValidationException extends RuntimeException {

	private final String errorCode;

	public ValidationException(final String message, final String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}