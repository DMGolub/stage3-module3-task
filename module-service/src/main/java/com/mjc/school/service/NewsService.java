package com.mjc.school.service;

import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;

import java.util.List;

public interface NewsService extends BaseService<NewsRequestDto, NewsResponseDto, Long> {

	List<NewsResponseDto> readNewsByParams(
		String tagName,
		Long tagId,
		String authorName,
		String title,
		String content
	);
}