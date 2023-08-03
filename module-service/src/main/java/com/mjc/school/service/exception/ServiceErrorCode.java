package com.mjc.school.service.exception;

public enum ServiceErrorCode {
	CONSTRAINT_VIOLATION(Constants.ERROR_000001, "Validation failed: %s"),
	ENTITY_NOT_FOUND_BY_ID(Constants.ERROR_000002, "Can not find %s by id: %s");

	private final String errorCode;
	private final String errorMessage;

	ServiceErrorCode(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getCode() {
		return errorCode;
	}

	public String getMessage() {
		return errorMessage;
	}

	private static class Constants {
		private static final String ERROR_000001 = "000001";
		private static final String ERROR_000002 = "000002";
	}
}