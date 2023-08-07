package com.mjc.school.controller;

import com.mjc.school.service.dto.TagRequestDto;
import com.mjc.school.service.dto.TagResponseDto;

import java.util.List;

public interface TagController extends BaseController<TagRequestDto, TagResponseDto, Long> {

	List<TagResponseDto> readTagsByNewsId(Long newsId);
}