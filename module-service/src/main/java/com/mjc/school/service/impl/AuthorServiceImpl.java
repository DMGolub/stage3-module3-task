package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.AuthorRepositoryImpl;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.mjc.school.service.constants.Constants.ID_VALUE_MIN;
import static com.mjc.school.service.exception.ServiceErrorCode.ENTITY_NOT_FOUND_BY_ID;

@Service
public class AuthorServiceImpl implements AuthorService {

	private static final String AUTHOR_ENTITY_NAME = "author";

	private final AuthorRepositoryImpl authorRepository;
	private final AuthorMapper mapper;

	public AuthorServiceImpl(final AuthorRepositoryImpl authorRepository, final AuthorMapper mapper) {
		this.authorRepository = authorRepository;
		this.mapper = mapper;
	}

	@Override
	public AuthorResponseDto create(@NotNull @Valid final AuthorRequestDto request) {
		final Author author = mapper.dtoToModel(request);
		final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		author.setCreateDate(now);
		author.setLastUpdateDate(now);
		return mapper.modelToDto(authorRepository.create(author));
	}

	@Override
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
	public AuthorResponseDto readAuthorByNewsId(@NotNull @Min(ID_VALUE_MIN) final Long newsId) {
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

	@Override
	public List<AuthorResponseDto> readAll() {
		return mapper.modelListToDtoList(authorRepository.readAll());
	}

	@Override
	public AuthorResponseDto update(@NotNull @Valid final AuthorRequestDto request)
			throws EntityNotFoundException {
		final Long id = request.id();
		if (id != null && authorRepository.existById(id)) {
			final Author author = mapper.dtoToModel(request);
			author.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
			return mapper.modelToDto(authorRepository.update(author));
		} else {
			throw new EntityNotFoundException(
				String.format(ENTITY_NOT_FOUND_BY_ID.getMessage(), AUTHOR_ENTITY_NAME, id),
				ENTITY_NOT_FOUND_BY_ID.getCode()
			);
		}
	}

	@Override
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