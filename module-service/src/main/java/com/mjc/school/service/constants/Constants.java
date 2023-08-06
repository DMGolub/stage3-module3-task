package com.mjc.school.service.constants;

public final class Constants {

	public static final long ID_VALUE_MIN = 1L;
	public static final long ID_VALUE_MAX = Long.MAX_VALUE;
	public static final int AUTHOR_NAME_LENGTH_MIN = 3;
	public static final int AUTHOR_NAME_LENGTH_MAX = 15;
	public static final int NEWS_TITLE_LENGTH_MIN = 5;
	public static final int NEWS_TITLE_LENGTH_MAX = 30;
	public static final int NEWS_CONTENT_LENGTH_MIN = 5;
	public static final int NEWS_CONTENT_LENGTH_MAX = 255;
	public static final int TAG_NAME_LENGTH_MIN = 3;
	public static final int TAG_NAME_LENGTH_MAX = 15;

	private Constants() {
		// Empty. Hides default public constructor
	}
}