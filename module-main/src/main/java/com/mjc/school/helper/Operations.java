package com.mjc.school.helper;

import static com.mjc.school.helper.Constant.OPERATION;

public enum Operations {
	EXIT(0, "Exit."),
	// news commands
	GET_ALL_NEWS(1, "Get all news."),
	GET_NEWS_BY_ID(2, "Get news by id."),
	GET_NEWS_BY_PARAMS(3, "Get news by params"),
	CREATE_NEWS(4, "Create news."),
	UPDATE_NEWS(5, "Update news."),
	REMOVE_NEWS_BY_ID(6, "Remove news by id."),
	// author commands
	GET_ALL_AUTHORS(7, "Get all authors."),
	GET_AUTHOR_BY_ID(8, "Get author by id."),
	GET_AUTHOR_BY_NEWS_ID(9, "Get author by news id"),
	CREATE_AUTHOR(10, "Create author."),
	UPDATE_AUTHOR(11, "Update author."),
	REMOVE_AUTHOR_BY_ID(12, "Remove author by id."),
	// tag commands
	GET_ALL_TAGS(13, "Get all tags"),
	GET_TAG_BY_ID(14, "Get tag by id"),
	GET_TAGS_BY_NEWS_ID(15, "Get tags by news id"),
	CREATE_TAG(16, "Create tag"),
	UPDATE_TAG(17, "Update tag"),
	REMOVE_TAG_BY_ID(18, "Remove tag by id");

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