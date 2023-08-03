package com.mjc.school.config;

import com.mjc.school.helper.MenuHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.mjc.school")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ApplicationConfiguration {

	@Bean
	public MenuHelper menuHelper() {
		return new MenuHelper(System.out);
	}
}