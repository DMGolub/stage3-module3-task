package com.mjc.school.repository.model;

import com.mjc.school.repository.action.OnDelete;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.mjc.school.repository.action.Operation.SET_NULL;

public class Author implements BaseEntity<Long> {

	private Long id;
	private String name;
	private LocalDateTime createDate;
	private LocalDateTime lastUpdateDate;
	@OnDelete(operation = SET_NULL, fieldName = "authorId")
	private List<News> news;

	public Author(
		final Long id,
		final String name,
		final LocalDateTime createDate,
		final LocalDateTime lastUpdateDate
	) {
		this.id = id;
		this.name = name;
		this.createDate = createDate;
		this.lastUpdateDate = lastUpdateDate;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
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

	public List<News> getNews() {
		return news;
	}

	public void setNews(final List<News> news) {
		this.news = news;
	}

	@Override
	public String toString() {
		return "Author{id=" + id +
			", name='" + name + '\'' +
			", createDate=" + createDate +
			", lastUpdateDate=" + lastUpdateDate +
			", news=" + news +
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
		final Author that = (Author) o;

		return Objects.equals(id, that.id)
			&& Objects.equals(name, that.name)
			&& Objects.equals(createDate, that.createDate)
			&& Objects.equals(lastUpdateDate, that.lastUpdateDate)
			&& Objects.equals(news, that.news);
	}

	@Override
	public int hashCode() {
		return 17;
	}
}