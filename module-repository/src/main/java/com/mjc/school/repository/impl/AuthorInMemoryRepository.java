package com.mjc.school.repository.impl;

import com.mjc.school.repository.IdSequence;
import com.mjc.school.repository.model.Author;

public class AuthorInMemoryRepository extends AbstractInMemoryRepository<Author, Long> {

	public AuthorInMemoryRepository(final IdSequence<Long> idSequence) {
		super(idSequence);
	}

	@Override
	void update(final Author prevState, final Author nextState) {
		prevState.setName(nextState.getName());
		prevState.setLastUpdateDate(nextState.getLastUpdateDate());
	}
}