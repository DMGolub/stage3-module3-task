package com.mjc.school.helper;

import java.util.Map;

import static com.mjc.school.helper.Operations.GET_ALL_AUTHORS;
import static com.mjc.school.helper.Operations.GET_ALL_NEWS;
import static com.mjc.school.helper.Operations.GET_ALL_TAGS;

public record Command(
	int operation,
	Map<String, String> params,
	String body,
	String queryParams
) {
	public static final Command NOT_FOUND = new Command(-1, null, null, null);
	public static final Command GET_AUTHORS = new Command(GET_ALL_AUTHORS.getOperationNumber(), null, null, null);
	public static final Command GET_NEWS = new Command(GET_ALL_NEWS.getOperationNumber(), null, null, null);
	public static final Command GET_TAGS = new Command(GET_ALL_TAGS.getOperationNumber(), null, null, null);

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private int operation;
		private Map<String, String> params = null;
		private String body = null;
		private String queryParams = null;

		public Builder operation(final int operation) {
			this.operation = operation;
			return this;
		}

		public Builder params(final Map<String, String> params) {
			this.params = params;
			return this;
		}

		public Builder body(final String body) {
			this.body = body;
			return this;
		}

		public Builder queryParams(final String queryParams) {
			this.queryParams = queryParams;
			return this;
		}

		public Command build() {
			return new Command(operation, params, body, queryParams);
		}
	}
}