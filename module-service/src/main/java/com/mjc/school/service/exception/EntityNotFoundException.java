package com.mjc.school.service.exception;

public final class EntityNotFoundException extends RuntimeException {

	private final String errorCode;

	public EntityNotFoundException(final String message, final String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}