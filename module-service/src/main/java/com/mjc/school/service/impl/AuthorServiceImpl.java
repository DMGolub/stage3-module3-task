package com.mjc.school.service.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.validator.annotation.Min;
import com.mjc.school.service.validator.annotation.NotNull;
import com.mjc.school.service.validator.annotation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.mjc.school.service.constants.Constants.ID_VALUE_MIN;
import static com.mjc.school.service.exception.ServiceErrorCode.ENTITY_NOT_FOUND_BY_ID;

@Service
public class AuthorServiceImpl implements AuthorService {

	private static final String AUTHOR_ENTITY_NAME = "author";
	private static final String NEWS_ENTITY_NAME = "news";

	private final AuthorRepository authorRepository;
	private final NewsRepository newsRepository;
	private final AuthorMapper mapper;

	public AuthorServiceImpl(
		final AuthorRepository authorRepository,
		final NewsRepository newsRepository,
		final AuthorMapper mapper
	) {
		this.authorRepository = authorRepository;
		this.newsRepository = newsRepository;
		this.mapper = mapper;
	}

	@Override
	@Transactional
	public AuthorResponseDto create(@NotNull @Valid final AuthorRequestDto request) {
		return mapper.modelToDto(authorRepository.create(mapper.dtoToModel(request)));
	}

	@Override
	@Transactional(readOnly = true)
	public AuthorResponseDto readById(@NotNull @Min(ID_VALUE_MIN) final Long id)
			throws EntityNotFoundException {
		final Optional<Author> author = authorRepository.readById(id);
		if (author.isPresent()) {
			return mapper.modelToDto(author.get());
		} else {
			throw new EntityNotFoundException(
				String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), AUTHOR_ENTITY_NAME, id),
				ENTITY_NOT_FOUND_BY_ID.getCode()
			);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public AuthorResponseDto readAuthorByNewsId(@NotNull @Min(ID_VALUE_MIN) final Long newsId) {
		if (newsRepository.existById(newsId)) {
			final Optional<Author> author = authorRepository.readAuthorByNewsId(newsId);
			if (author.isPresent()) {
				return mapper.modelToDto(author.get());
			} else {
				throw new EntityNotFoundException(
					String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), AUTHOR_ENTITY_NAME, newsId),
					ENTITY_NOT_FOUND_BY_ID.getCode()
				);
			}
		}
		throw new EntityNotFoundException(
			String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), NEWS_ENTITY_NAME, newsId),
			ENTITY_NOT_FOUND_BY_ID.getCode()
		);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AuthorResponseDto> readAll() {
		return mapper.modelListToDtoList(authorRepository.readAll());
	}

	@Override
	@Transactional
	public AuthorResponseDto update(@NotNull @Valid final AuthorRequestDto request)
			throws EntityNotFoundException {
		final Long id = request.id();
		if (id != null) {
			final Optional<Author> author = authorRepository.readById(id);
			if (author.isPresent()) {
				final Author updated = author.get();
				updated.setName(request.name());
				return mapper.modelToDto(authorRepository.update(updated));
			}
		}
		throw new EntityNotFoundException(
			String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), AUTHOR_ENTITY_NAME, id),
			ENTITY_NOT_FOUND_BY_ID.getCode()
		);
	}

	@Override
	@Transactional
	public boolean deleteById(@NotNull @Min(ID_VALUE_MIN) final Long id) throws EntityNotFoundException {
		if (authorRepository.existById(id)) {
			return authorRepository.deleteById(id);
		}
		throw new EntityNotFoundException(
			String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), AUTHOR_ENTITY_NAME, id),
			ENTITY_NOT_FOUND_BY_ID.getCode()
		);
	}
}