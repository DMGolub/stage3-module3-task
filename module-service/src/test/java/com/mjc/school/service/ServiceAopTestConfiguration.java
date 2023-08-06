package com.mjc.school.service;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.TagRepository;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.mapper.TagMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan(basePackages = "com.mjc.school.service")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ServiceAopTestConfiguration {

	@Bean
	@Primary
	public AuthorRepository authorRepository() {
		return mock(AuthorRepository.class);
	}

	@Bean
	@Primary
	public NewsRepository newsRepository() {
		return mock(NewsRepository.class);
	}

	@Bean
	@Primary
	public TagRepository tagRepository() {
		return mock(TagRepository.class);
	}

	@Bean
	@Primary
	public AuthorMapper authorMapper() {
		return mock(AuthorMapper.class);
	}

	@Bean
	@Primary
	public NewsMapper newsMapper() {
		return mock(NewsMapper.class);
	}

	@Bean
	@Primary
	public TagMapper tagMapper() {
		return mock(TagMapper.class);
	}
}