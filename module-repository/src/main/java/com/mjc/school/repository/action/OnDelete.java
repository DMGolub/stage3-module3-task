package com.mjc.school.repository.action;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Determines behaviour on author deletion.
 * Possible values:
 * <b>SET_NULL</b> - set authorId field for corresponding news to null,
 * <b>remove</b> - remove corresponding news
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnDelete {
	Operation operation();

	String fieldName();
}