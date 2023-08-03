package com.mjc.school.service.dto;

import java.time.LocalDateTime;

public record NewsResponseDto(
	Long id,
	String title,
	String content,
	LocalDateTime createDate,
	LocalDateTime lastUpdateDate,
	Long authorId
) {
	// Empty
}