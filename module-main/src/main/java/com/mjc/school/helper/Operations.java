package com.mjc.school.helper;

import static com.mjc.school.helper.Constant.OPERATION;

public enum Operations {
	EXIT(0, "Exit."),
	GET_ALL_NEWS(1, "Get all news."),
	GET_NEWS_BY_ID(2, "Get news by id."),
	CREATE_NEWS(3, "Create news."),
	UPDATE_NEWS(4, "Update news."),
	REMOVE_NEWS_BY_ID(5, "Remove news by id."),

	GET_ALL_AUTHORS(6, "Get all authors."),
	GET_AUTHOR_BY_ID(7, "Get author by id."),
	CREATE_AUTHOR(8, "Create author."),
	UPDATE_AUTHOR(9, "Update author."),
	REMOVE_AUTHOR_BY_ID(10, "Remove author by id.");

	private final int operationNumber;
	private final String operation;

	Operations(int operationNumber, String operation) {
		this.operationNumber = operationNumber;
		this.operation = operation;
	}

	public String getOperation() {
		return OPERATION + operation;
	}

	public String getOperationWithNumber() {
		return operationNumber + " - " + operation;
	}

	public int getOperationNumber() {
		return operationNumber;
	}
}