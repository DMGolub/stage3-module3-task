package com.mjc.school.repository.impl;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.News;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class NewsRepositoryImpl implements NewsRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<News> readAll() {
		return entityManager
			.createQuery("SELECT n FROM News AS n", News.class)
			.getResultList();
	}

	@Override
	public Optional<News> readById(final Long id) {
		if (id != null) {
			return Optional.ofNullable(entityManager.find(News.class, id));
		}
		return Optional.empty();
	}

	@Override
	public List<News> readByParams(Object[] params) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public News create(final News news) {
		if (news != null) {
			entityManager.persist(news);
			return news;
		}
		return null;
	}

	@Override
	public News update(final News news) {
		if (news != null && existById(news.getId())) {
			entityManager.merge(news);
			return news;
		}
		return null;
	}

	@Override
	public boolean deleteById(final Long id) {
		final Optional<News> news = readById(id);
		if (news.isPresent()) {
			entityManager.remove(news.get());
			return !existById(id);
		}
		return false;
	}

	@Override
	public boolean existById(final Long id) {
		return id != null && entityManager.find(News.class, id) != null;
	}
}