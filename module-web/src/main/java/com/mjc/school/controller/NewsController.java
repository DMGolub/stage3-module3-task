package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;

import java.util.List;

public interface NewsController extends BaseController<NewsRequestDto, NewsResponseDto, Long> {

	List<NewsResponseDto> readNewsByParams(
		final String tagName,
		final Long tagId,
		final String authorName,
		final String title,
		final String content
	);
}