package com.mjc.school.service.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.validator.annotation.Min;
import com.mjc.school.service.validator.annotation.NotNull;
import com.mjc.school.service.validator.annotation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.mjc.school.service.constants.Constants.ID_VALUE_MIN;
import static com.mjc.school.service.exception.ServiceErrorCode.ENTITY_NOT_FOUND_BY_ID;

@Service
public class NewsServiceImpl implements NewsService {

	private static final String NEWS_ENTITY_NAME = "news";
	private static final String AUTHOR_ENTITY_NAME = "author";

	private final AuthorRepository authorRepository;
	private final NewsRepository newsRepository;
	private final NewsMapper mapper;

	public NewsServiceImpl(
		final AuthorRepository authorRepository,
		final NewsRepository newsRepository,
		final NewsMapper mapper
	) {
		this.authorRepository = authorRepository;
		this.newsRepository = newsRepository;
		this.mapper = mapper;
	}

	@Override
	public NewsResponseDto create(@NotNull @Valid final NewsRequestDto request) throws EntityNotFoundException {
		final News news = mapper.dtoToModel(request);
		news.setAuthor(getAuthor(request.authorId()));
		final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		news.setCreateDate(now);
		news.setLastUpdateDate(now);
		return mapper.modelToDto(newsRepository.create(news));
	}

	@Override
	public NewsResponseDto readById(@NotNull @Min(ID_VALUE_MIN) final Long id) throws EntityNotFoundException {
		Optional<News> news = newsRepository.readById(id);
		if (news.isPresent()) {
			return mapper.modelToDto(news.get());
		} else {
			throw new EntityNotFoundException(
				String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), NEWS_ENTITY_NAME, id),
				ENTITY_NOT_FOUND_BY_ID.getCode()
			);
		}
	}

	@Override
	public List<NewsResponseDto> readNewsByParams(final Object[] params) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public List<NewsResponseDto> readAll() {
		return mapper.modelListToDtoList(newsRepository.readAll());
	}

	@Override
	public NewsResponseDto update(@NotNull @Valid final NewsRequestDto request) throws EntityNotFoundException {
		final Long id = request.id();
		if (id != null && newsRepository.existById(id)) {
			final News news = mapper.dtoToModel(request);
			news.setAuthor(getAuthor(request.authorId()));
			news.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
			return mapper.modelToDto(newsRepository.update(news));
		} else {
			throw new EntityNotFoundException(
				String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), NEWS_ENTITY_NAME, id),
				ENTITY_NOT_FOUND_BY_ID.getCode()
			);
		}
	}

	@Override
	public boolean deleteById(@NotNull @Min(ID_VALUE_MIN) final Long id) throws EntityNotFoundException {
		if (newsRepository.existById(id)) {
			return newsRepository.deleteById(id);
		}
		throw new EntityNotFoundException(
			String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), NEWS_ENTITY_NAME, id),
			ENTITY_NOT_FOUND_BY_ID.getCode()
		);
	}

	private Author getAuthor(final Long authorId) throws EntityNotFoundException {
		if (authorId != null) {
			final Optional<Author> author = authorRepository.readById(authorId);
			if (author.isPresent()) {
				return author.get();
			}
		}
		throw new EntityNotFoundException(
			String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), AUTHOR_ENTITY_NAME, authorId),
			ENTITY_NOT_FOUND_BY_ID.getCode()
		);
	}
}