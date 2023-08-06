package com.mjc.school.service.util;

import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.dto.TagRequestDto;
import com.mjc.school.service.dto.TagResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public final class Util {

	private Util() {
		// Empty. Hides default public constructor
	}

	public static AuthorRequestDto createTestAuthorRequest(final Long authorId) {
		return new AuthorRequestDto(authorId, "Author News");
	}

	public static Author createTestAuthor(final long id) {
		return new Author(
			id,
			"Author Name",
			LocalDateTime.of(2023, 7, 17, 16, 30, 0),
			LocalDateTime.of(2023, 7, 17, 16, 30, 0)
		);
	}

	public static AuthorResponseDto authorToDTO(final Author author) {
		return new AuthorResponseDto(
			author.getId(),
			author.getName(),
			author.getCreateDate(),
			author.getLastUpdateDate()
		);
	}

	public static Author dtoToAuthor(final AuthorRequestDto authorRequestDto) {
		return new Author(
			authorRequestDto.id(),
			authorRequestDto.name(),
			null,
			null
		);
	}

	public static List<AuthorResponseDto> authorListToAuthorDTOList(final List<Author> authors) {
		return authors.stream()
			.map(Util::authorToDTO)
			.collect(Collectors.toList());
	}


	public static News createTestNews(final Long newsId) {
		return new News(
			newsId,
			"Title",
			"Content",
			LocalDateTime.of(2023, 7, 17, 16, 30, 0),
			LocalDateTime.of(2023, 7, 17, 16, 30, 0),
			Util.createTestAuthor(1L)
		);
	}

	public static NewsRequestDto createTestNewsRequest(final Long newsId) {
		return new NewsRequestDto(newsId, "Title", "Content", 1L);
	}

	public static NewsResponseDto newsToDTO(final News news) {
		return new NewsResponseDto(
			news.getId(),
			news.getTitle(),
			news.getContent(),
			news.getCreateDate(),
			news.getLastUpdateDate(),
			news.getAuthor().getId()
		);
	}

	public static News dtoToNews(final NewsRequestDto newsRequestDto) {
		return new News(
			newsRequestDto.id(),
			newsRequestDto.title(),
			newsRequestDto.content(),
			null,
			null,
			null		// FIXME - this was authorId
		);
	}

	public static List<NewsResponseDto> newsListToNewsDTOList(final List<News> news) {
		return news.stream()
			.map(Util::newsToDTO)
			.collect(Collectors.toList());
	}

	public static TagRequestDto createTestTagRequest(final Long tagId) {
		return new TagRequestDto(tagId, "Tag Name");
	}

	public static Tag createTestTag(final long id) {
		return new Tag(id, "Tag Name");
	}

	public static TagResponseDto tagToDTO(final Tag tag) {
		return new TagResponseDto(tag.getId(), tag.getName());
	}

	public static Tag dtoToTag(final TagRequestDto tagRequestDto) {
		return new Tag(tagRequestDto.id(), tagRequestDto.name());
	}

	public static List<TagResponseDto> tagListToTagDTOList(final List<Tag> Tags) {
		return Tags.stream()
			.map(Util::tagToDTO)
			.collect(Collectors.toList());
	}
}