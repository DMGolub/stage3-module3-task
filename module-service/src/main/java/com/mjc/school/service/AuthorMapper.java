package com.mjc.school.service;

import com.mjc.school.repository.model.Author;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

	AuthorResponseDto modelToDto(Author author);

	List<AuthorResponseDto> modelListToDtoList(List<Author> authors);

	@Mappings({
		@Mapping(target = "createDate", ignore = true),
		@Mapping(target = "lastUpdateDate", ignore = true),
		@Mapping(target = "news", ignore = true)
	})
	Author dtoToModel(AuthorRequestDto request);
}