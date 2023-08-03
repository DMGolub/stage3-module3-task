package com.mjc.school.repository.impl;

import com.mjc.school.repository.IdSequence;
import com.mjc.school.repository.model.News;

public class NewsInMemoryRepository extends AbstractInMemoryRepository<News, Long> {

	public NewsInMemoryRepository(final IdSequence<Long> idSequence) {
		super(idSequence);
	}

	@Override
	void update(final News prevState, final News nextState) {
		prevState.setTitle(nextState.getTitle());
		prevState.setContent(nextState.getContent());
		prevState.setLastUpdateDate(nextState.getLastUpdateDate());
		prevState.setAuthorId(nextState.getAuthorId());
	}
}