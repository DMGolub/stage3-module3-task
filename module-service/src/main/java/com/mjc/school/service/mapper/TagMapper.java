package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.dto.TagRequestDto;
import com.mjc.school.service.dto.TagResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

	TagResponseDto modelToDto(Tag tag);

	List<TagResponseDto> modelListToDtoList(List<Tag> tags);

	Tag dtoToModel(TagRequestDto tagRequestDto);
}