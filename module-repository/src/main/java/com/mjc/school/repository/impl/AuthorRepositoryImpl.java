package com.mjc.school.repository.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.model.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Author> readAll() {
		return entityManager
			.createQuery("SELECT a FROM Author AS a", Author.class)
			.getResultList();
	}

	@Override
	public Optional<Author> readById(final Long id) {
		if (id != null) {
			Author author = entityManager.find(Author.class, id);
			return Optional.ofNullable(author);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Author> readAuthorByNewsId(final Long newsId) {
		if (newsId != null) {
			final String query = "SELECT a FROM Author AS a WHERE a.id = " +
				"(SELECT n.author.id FROM News AS n WHERE n.id = :newsId)";
			Author author = entityManager.createQuery(query, Author.class)
				.setParameter("newsId", newsId)
				.getSingleResult();
			return Optional.ofNullable(author);
		}
		return Optional.empty();
	}

	@Override
	@Transactional
	public Author create(final Author author) {
		if (author != null) {
			entityManager.persist(author);
			return author;
		}
		return null;
	}

	@Override
	@Transactional
	public Author update(final Author author) {
		if (author != null && existById(author.getId())) {
			entityManager.merge(author);
			return author;
		}
		return null;
	}

	@Override
	@Transactional
	public boolean deleteById(final Long id) {
		final Optional<Author> author = readById(id);
		if (author.isPresent()) {
			entityManager.remove(author.get());
			return !existById(id);
		}
		return false;
	}

	@Override
	public boolean existById(final Long id) {
		return id != null && entityManager.find(Author.class, id) != null;
	}
}