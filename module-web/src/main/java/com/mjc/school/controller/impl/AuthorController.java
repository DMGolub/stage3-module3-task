package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController implements BaseController<AuthorRequestDto, AuthorResponseDto, Long> {

	private final AuthorService authorService;

	public AuthorController(final AuthorService authorService) {
		this.authorService = authorService;
	}

	@Override
	@CommandHandler(operation = 7)
	public List<AuthorResponseDto> readAll() {
		return authorService.readAll();
	}

	@Override
	@CommandHandler(operation = 8)
	public AuthorResponseDto readById(@CommandParam(name = "id") final Long id) {
		return authorService.readById(id);
	}

	@CommandHandler(operation = 9)
	public AuthorResponseDto readByNewsId(@CommandParam(name = "id") final Long newsId) {
		return authorService.readAuthorByNewsId(newsId);
	}

	@Override
	@CommandHandler(operation = 10)
	public AuthorResponseDto create(@CommandBody final AuthorRequestDto request) {
		return authorService.create(request);
	}

	@Override
	@CommandHandler(operation = 11)
	public AuthorResponseDto update(@CommandBody final AuthorRequestDto request) {
		return authorService.update(request);
	}

	@Override
	@CommandHandler(operation = 12)
	public boolean deleteById(@CommandParam(name = "id") final Long id) {
		return authorService.deleteById(id);
	}
}