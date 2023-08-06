package com.mjc.school.repository.impl;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Tag> readAll() {
		return entityManager
			.createQuery("SELECT t FROM Tag AS t", Tag.class)
			.getResultList();
	}

	@Override
	public Optional<Tag> readById(final Long id) {
		if (id != null) {
			return Optional.ofNullable(entityManager.find(Tag.class, id));
		}
		return Optional.empty();
	}

	@Override
	public List<Tag> readTagsByNewsId(final Long newsId) {
		if (newsId != null) {
			final String query = "SELECT n.tags FROM News AS n WHERE n.id = :newsId";
			return entityManager.createQuery(query, Tag.class)
				.setParameter("newsId", newsId)
				.getResultList();
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public Tag create(final Tag tag) {
		if (tag != null) {
			entityManager.persist(tag);
			return tag;
		}
		return null;
	}

	@Override
	@Transactional
	public Tag update(final Tag tag) {
		if (tag != null && existById(tag.getId())) {
			entityManager.merge(tag);
			return tag;
		}
		return null;
	}

	@Override
	@Transactional
	public boolean deleteById(final Long id) {
		final Optional<Tag> tag = readById(id);
		if (tag.isPresent()) {
			entityManager.remove(tag.get());
			return !existById(id);
		}
		return false;
	}

	@Override
	public boolean existById(final Long id) {
		return id != null && entityManager.find(Tag.class, id) != null;
	}
}