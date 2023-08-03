package com.mjc.school.repository.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class News implements BaseEntity<Long> {

	private Long id;
	private String title;
	private String content;
	private LocalDateTime createDate;
	private LocalDateTime lastUpdateDate;
	private Long authorId;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	public News(
		final Long id,
		final String title,
		final String content,
		final LocalDateTime createDate,
		final LocalDateTime lastUpdateDate,
		final Long authorId
	) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.createDate = createDate;
		this.lastUpdateDate = lastUpdateDate;
		this.authorId = authorId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(final LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(final LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(final Long authorId) {
		this.authorId = authorId;
	}

	@Override
	public String toString() {
		return "News{id=" + id +
			", title='" + title + '\'' +
			", content='" + content + '\'' +
			", createDate=" + createDate +
			", lastUpdateDate=" + lastUpdateDate +
			", authorId=" + authorId +
			'}';
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final News news = (News) o;
		return Objects.equals(id, news.id)
			&& Objects.equals(title, news.title)
			&& Objects.equals(content, news.content)
			&& Objects.equals(createDate, news.createDate)
			&& Objects.equals(lastUpdateDate, news.lastUpdateDate)
			&& Objects.equals(authorId, news.authorId);
	}

	@Override
	public int hashCode() {
		return 37;
	}
}