package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class AbstractRepository<T extends BaseEntity<K>, K> implements BaseRepository<T, K> {

	@PersistenceContext
	protected EntityManager entityManager;
	@Autowired
	protected PlatformTransactionManager transactionManager;
	private final Class<T> entityClass;

	protected AbstractRepository() {
		final ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		entityClass = (Class<T>) type.getActualTypeArguments()[0];
	}

	@Override
	public List<T> readAll() {
		TypedQuery<T> query = entityManager.createQuery("SELECT e FROM "
			+ entityClass.getSimpleName() + " e", entityClass);
		return query.getResultList();
	}

	@Override
	public Optional<T> readById(final K id) {
		if (id != null) {
			return Optional.ofNullable(entityManager.find(entityClass, id));
		}
		return Optional.empty();
	}

	@Override
	public T create(final T entity) {
		if (entity != null) {
			final var transactionDefinition = new DefaultTransactionDefinition();
			final var transactionStatus = transactionManager.getTransaction(transactionDefinition);
			try {
				entityManager.persist(entity);
				transactionManager.commit(transactionStatus);
				return entity;
			} catch (Exception e) {
				transactionManager.rollback(transactionStatus);
				throw e;
			}
		}
		return null;
	}

	@Override
	public T update(final T entity) {
		final var transactionDefinition = new DefaultTransactionDefinition();
		final var transactionStatus = transactionManager.getTransaction(transactionDefinition);
		if (entity != null && existById(entity.getId())) {
			try {
				entityManager.merge(entity);
				transactionManager.commit(transactionStatus);
				return entityManager.find(entityClass, entity.getId());
			} catch (Exception e) {
				transactionManager.rollback(transactionStatus);
				throw e;
			}
		}
		transactionManager.rollback(transactionStatus);
		return null;
	}

	@Override
	public boolean deleteById(final K id) {
		final var transactionDefinition = new DefaultTransactionDefinition();
		final var transactionStatus = transactionManager.getTransaction(transactionDefinition);
		final Optional<T> entity = readById(id);
		if (entity.isPresent()) {
			try {
				entityManager.remove(entity.get());
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
	public boolean existById(final K id) {
		return id != null && entityManager.find(entityClass, id) != null;
	}
}