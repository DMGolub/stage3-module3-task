package com.mjc.school.controller;

import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;

public interface AuthorController extends BaseController<AuthorRequestDto, AuthorResponseDto, Long> {

	AuthorResponseDto readByNewsId(Long newsId);
}