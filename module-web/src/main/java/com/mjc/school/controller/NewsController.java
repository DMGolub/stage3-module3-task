package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.query.NewsQueryParams;

import java.util.List;

public interface NewsController extends BaseController<NewsRequestDto, NewsResponseDto, Long> {

	List<NewsResponseDto> readNewsByParams(NewsQueryParams newsQueryParams);
}