package com.mjc.school;

import com.mjc.school.config.ApplicationConfiguration;
import com.mjc.school.utility.Conversation;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		try (var context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class)) {
			Conversation conversation = context.getBean(Conversation.class);
			conversation.run();
		}
	}
}