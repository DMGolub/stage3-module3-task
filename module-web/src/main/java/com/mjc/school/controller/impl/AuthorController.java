package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController implements BaseController<AuthorRequestDto, AuthorResponseDto, Long> {

	private final BaseService<AuthorRequestDto, AuthorResponseDto, Long> authorService;

	public AuthorController(final BaseService<AuthorRequestDto, AuthorResponseDto, Long> authorService) {
		this.authorService = authorService;
	}

	@Override
	@CommandHandler(operation = 6)
	public List<AuthorResponseDto> readAll() {
		return authorService.readAll();
	}

	@Override
	@CommandHandler(operation = 7)
	public AuthorResponseDto readById(@CommandParam(name = "id") final Long id) {
		return authorService.readById(id);
	}

	@Override
	@CommandHandler(operation = 8)
	public AuthorResponseDto create(@CommandBody final AuthorRequestDto request) {
		return authorService.create(request);
	}

	@Override
	@CommandHandler(operation = 9)
	public AuthorResponseDto update(@CommandBody final AuthorRequestDto request) {
		return authorService.update(request);
	}

	@Override
	@CommandHandler(operation = 10)
	public boolean deleteById(@CommandParam(name = "id") final Long id) {
		return authorService.deleteById(id);
	}
}