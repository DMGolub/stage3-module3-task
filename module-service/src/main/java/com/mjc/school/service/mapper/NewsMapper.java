package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {

	@Mapping(source = "news.author.id", target = "authorId")
	NewsResponseDto modelToDto(News news);

	List<NewsResponseDto> modelListToDtoList(List<News> news);

	@Mapping(target = "author", ignore = true)
	@Mapping(target = "createDate", ignore = true)
	@Mapping(target = "lastUpdateDate", ignore = true)
	News dtoToModel(NewsRequestDto request);
}