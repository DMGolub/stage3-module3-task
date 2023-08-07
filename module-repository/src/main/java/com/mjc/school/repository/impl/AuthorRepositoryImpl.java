package com.mjc.school.repository.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Override
	public List<Author> readAll() {
		return entityManager
			.createQuery("SELECT a FROM Author AS a", Author.class)
			.getResultList();
	}

	@Override
	public Optional<Author> readById(final Long id) {
		if (id != null) {
			return Optional.ofNullable(entityManager.find(Author.class, id));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Author> readAuthorByNewsId(final Long newsId) {
		if (newsId != null) {
			final String query = "SELECT a FROM Author AS a WHERE a.id = " +
				"(SELECT n.author.id FROM News AS n WHERE n.id = :newsId)";
			final Author author = entityManager.createQuery(query, Author.class)
				.setParameter("newsId", newsId)
				.getSingleResult();
			return Optional.ofNullable(author);
		}
		return Optional.empty();
	}

	@Override
	public Author create(final Author author) {
		if (author != null) {
			final var transactionDefinition = new DefaultTransactionDefinition();
			final var transactionStatus = transactionManager.getTransaction(transactionDefinition);
			try {
				entityManager.persist(author);
				transactionManager.commit(transactionStatus);
				return author;
			} catch (Exception e) {
				transactionManager.rollback(transactionStatus);
				throw e;
			}
		}
		return null;
	}

	@Override
	public Author update(final Author author) {
		final var transactionDefinition = new DefaultTransactionDefinition();
		final var transactionStatus = transactionManager.getTransaction(transactionDefinition);
		if (author != null && existById(author.getId())) {
			try {
				entityManager.merge(author);
				transactionManager.commit(transactionStatus);
				return entityManager.find(Author.class, author.getId());
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
		final Optional<Author> author = readById(id);
		if (author.isPresent()) {
			try {
				entityManager.remove(author.get());
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
		return id != null && entityManager.find(Author.class, id) != null;
	}
}