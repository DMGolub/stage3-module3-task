package com.mjc.school.repository;

import com.mjc.school.repository.model.News;

import java.util.List;

public interface NewsRepository extends BaseRepository<News, Long> {

	public List<News> readByParams(Object[] params);
}