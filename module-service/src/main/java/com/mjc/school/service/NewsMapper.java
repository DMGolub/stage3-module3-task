package com.mjc.school.service;

import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {

	NewsResponseDto modelToDto(News news);

	List<NewsResponseDto> modelListToDtoList(List<News> news);

	@Mappings({
		@Mapping(target = "createDate", ignore = true),
		@Mapping(target = "lastUpdateDate", ignore = true)
	})
	News dtoToModel(NewsRequestDto request);
}