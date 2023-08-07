package com.mjc.school.repository.impl;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class NewsRepositoryImpl implements NewsRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private PlatformTransactionManager transactionManager;

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
	public List<News> readByParams(
		final String tagName,
		final Long tagId,
		final String authorName,
		final String title,
		final String content
	) {
		final String queryString = formQueryString(tagName, tagId, authorName, title, content);
		final var query = entityManager.createQuery(queryString, News.class);
		parameterizeQuery(tagName, tagId, authorName, title, content, query);
		return query.getResultList();
	}

	@Override
	public News create(final News news) {
		if (news != null) {
			final var transactionDefinition = new DefaultTransactionDefinition();
			final var transactionStatus = transactionManager.getTransaction(transactionDefinition);
			try {
				entityManager.persist(news);
				transactionManager.commit(transactionStatus);
				return news;
			} catch (Exception e) {
				transactionManager.rollback(transactionStatus);
				throw e;
			}
		}
		return null;
	}

	@Override
	public News update(final News news) {
		final var transactionDefinition = new DefaultTransactionDefinition();
		final var transactionStatus = transactionManager.getTransaction(transactionDefinition);
		if (news != null && existById(news.getId())) {
			try {
				entityManager.merge(news);
				transactionManager.commit(transactionStatus);
				return entityManager.find(News.class, news.getId());
			} catch (Exception e) {
				transactionManager.rollback(transactionStatus);
				throw e;
			}
		}
		transactionManager.rollback(transactionStatus);
		return null;
	}

	@Override
	public boolean deleteById(final Long id) {
		final var transactionDefinition = new DefaultTransactionDefinition();
		final var transactionStatus = transactionManager.getTransaction(transactionDefinition);
		final Optional<News> news = readById(id);
		if (news.isPresent()) {
			try {
				entityManager.remove(news.get());
				transactionManager.commit(transactionStatus);
				return !existById(id);
			} catch (Exception e) {
				transactionManager.rollback(transactionStatus);
				throw e;
			}
		}
		transactionManager.rollback(transactionStatus);
		return false;
	}

	@Override
	public boolean existById(final Long id) {
		return id != null && entityManager.find(News.class, id) != null;
	}

	private String formQueryString(
		final String tagName,
		final Long tagId,
		final String authorName,
		final String title,
		final String content
	) {
		final StringBuilder queryString =
			new StringBuilder("SELECT DISTINCT n FROM News AS n LEFT JOIN n.author AS a LEFT JOIN n.tags AS t");

		if (tagName != null && !tagName.isEmpty()) {
			queryString.append(" WHERE t.name = :tagName");
		} else {
			queryString.append(" WHERE 1 = 1");
		}
		if (tagId != null) {
			queryString.append(" AND t.id = :tagId");
		}
		if (authorName != null && !authorName.isEmpty()) {
			queryString.append(" AND a.name = :authorName");
		}
		if (title != null && !title.isEmpty()) {
			queryString.append(" AND n.title LIKE :title");
		}
		if (content != null && !content.isEmpty()) {
			queryString.append(" AND n.content LIKE :content");
		}
		return queryString.toString();
	}

	private void parameterizeQuery(
		final String tagName,
		final Long tagId,
		final String authorName,
		final String title,
		final String content,
		final Query query
	) {
		if (tagName != null && !tagName.isEmpty()) {
			query.setParameter("tagName", tagName);
		}
		if (tagId != null) {
			query.setParameter("tagId", tagId);
		}
		if (authorName != null && !authorName.isEmpty()) {
			query.setParameter("authorName", authorName);
		}
		if (title != null && !title.isEmpty()) {
			query.setParameter("title", "%" + title + "%");
		}
		if (content != null && !content.isEmpty()) {
			query.setParameter("content", "%" + content + "%");
		}
	}
}