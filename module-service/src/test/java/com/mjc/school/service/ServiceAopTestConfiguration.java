package com.mjc.school.service;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.impl.AuthorInMemoryRepository;
import com.mjc.school.repository.impl.NewsInMemoryRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
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
	public BaseRepository<Author, Long> authorRepository() {
		return mock(AuthorInMemoryRepository.class);
	}

	@Bean
	@Primary
	public BaseRepository<News, Long> newsRepository() {
		return mock(NewsInMemoryRepository.class);
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
}