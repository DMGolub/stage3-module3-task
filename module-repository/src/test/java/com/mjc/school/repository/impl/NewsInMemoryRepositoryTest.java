package com.mjc.school.repository.impl;

import com.mjc.school.repository.IdSequence;
import com.mjc.school.repository.model.News;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class NewsInMemoryRepositoryTest {

	private NewsInMemoryRepository repository;

	@BeforeEach
	void initSequence() {
		final IdSequence<Long> longIdSequence = new InMemoryIdSequence<>(1L, prev -> prev + 1);
		repository = new NewsInMemoryRepository(longIdSequence);
	}

	@Nested
	class TestReadAll {

		@Test
		void readAll_shouldReturnEmptyList_whenStorageIsEmpty() {
			assertEquals(Collections.emptyList(), repository.readAll());
		}

		@Test
		void readAll_shouldReturnTwoEntities_whenThereAreTwoEntitiesInTheStorage() {
			final List<News> storage = Arrays.asList(
				createTestNews(1L),
				createTestNews(2L)
			);
			storage.forEach(repository::create);

			assertEquals(storage, repository.readAll());
		}
	}

	@Nested
	class TestReadById {

		@Test
		void readById_shouldReturnEmptyOptional_whenThereIsNoEntityWithGivenId() {
			final List<News> storage = Arrays.asList(
				createTestNews(1L),
				createTestNews(2L)
			);
			storage.forEach(repository::create);

			assertEquals(Optional.empty(), repository.readById(3L));
		}

		@Test
		void readById_shouldReturnEntity_whenEntityWithGivenIdExists() {
			final long id = 2L;
			final News expected = createTestNews(id);
			final List<News> storage = Arrays.asList(createTestNews(1L), expected);
			storage.forEach(repository::create);

			assertEquals(Optional.of(expected), repository.readById(id));
		}
	}

	@Nested
	class TestCreate {

		@Test
		void create_shouldSaveEntityAndReturnEntityWithId1_whenStorageIsEmpty() {
			News news = createTestNews(null);
			News expected = createTestNews(1L);

			assertEquals(expected, repository.create(news));
			assertEquals(1, repository.readAll().size());
		}

		@Test
		void create_shouldSaveEntityAndReturnEntityWithId3_whenStorageContainsTwoEntities() {
			final List<News> storage = List.of(
				createTestNews(1L),
				createTestNews(2L)
			);
			storage.forEach(repository::create);

			News news = createTestNews(null);
			News expected = createTestNews(3L);

			assertEquals(expected, repository.create(news));
			assertEquals(3, repository.readAll().size());
		}
	}

		@Nested
		class TestUpdate {

			@Test
			void update_shouldThrowNoSuchElementException_whenThereIsNoEntityWithGivenId() {
				final List<News> storage = List.of(
					createTestNews(1L),
					createTestNews(2L)
				);
				storage.forEach(repository::create);

				News updated = createTestNews(99L);

				assertThrows(NoSuchElementException.class, () -> repository.update(updated));
			}

			@Test
			void update_shouldReturnUpdatedEntity_whenEntityIsValid() {
				final List<News> storage = List.of(
					createTestNews(1L),
					createTestNews(2L)
				);
				storage.forEach(repository::create);

				final News updated = createTestNews(2L);
				updated.setTitle("Updated title");
				updated.setContent("Updated content");
				updated.setAuthorId(2L);
				List<News> expected = Arrays.asList(createTestNews(1L), updated);

				assertEquals(updated, repository.update(updated));
				assertEquals(expected, repository.readAll());
			}
		}

	@Nested
	class TestDeleteById {

		@Test
		void deleteById_shouldReturnFalse_whenIdIsNull() {
			final List<News> storage = Arrays.asList(
				createTestNews(1L),
				createTestNews(2L)
			);
			storage.forEach(repository::create);

			assertFalse(repository.deleteById(null));
			assertEquals(2, repository.readAll().size());
		}

		@Test
		void deleteById_shouldReturnFalse_whenStorageIsEmpty() {
			assertFalse(repository.deleteById(99L));
		}

		@Test
		void deleteById_shouldReturnFalse_whenThereIsNoEntityWithGivenId() {
			final List<News> storage = Arrays.asList(
				createTestNews(1L),
				createTestNews(2L)
			);
			storage.forEach(repository::create);

			assertFalse(repository.deleteById(99L));
			assertEquals(2, repository.readAll().size());
		}

		@Test
		void deleteById_shouldReturnTrue_whenEntityWithGivenIdDeleted() {
			final long id = 3L;
			final List<News> storage = new ArrayList<>();
			storage.add(createTestNews(1L));
			storage.add(createTestNews(2L));
			storage.add(createTestNews(id));
			storage.add(createTestNews(4L));
			storage.add(createTestNews(5L));
			storage.forEach(repository::create);

			assertTrue(repository.deleteById(id));
			assertEquals(4, repository.readAll().size());
		}

		@Test
		void deleteById_shouldDeleteEntity_whenItIsSingleEntityInTheStorage() {
			repository.create(createTestNews(null));

			assertTrue(repository.deleteById(1L));
			assertTrue(repository.readAll().isEmpty());
		}
	}

	@Nested
	class TestExistById {

		@Test
		void existById_shouldReturnFalse_whenStorageIsEmpty() {
			assertFalse(repository.existById(99L));
		}

		@Test
		void existById_shouldReturnFalse_whenThereIsNoEntityWithGivenId() {
			final List<News> storage = Arrays.asList(
				createTestNews(1L),
				createTestNews(2L)
			);
			storage.forEach(repository::create);

			assertFalse(repository.existById(99L));
		}

		@Test
		void existById_shouldReturnTrue_whenEntityWithGivenIdExists() {
			final List<News> storage = Arrays.asList(
				createTestNews(1L),
				createTestNews(2L)
			);
			storage.forEach(repository::create);

			assertTrue(repository.existById(2L));
		}
	}

	private News createTestNews(Long newsId) {
		return new News(
			newsId,
			"Title",
			"Content",
			LocalDateTime.of(2023, 7, 17, 16, 30, 0),
			LocalDateTime.of(2023, 7, 17, 16, 30, 0),
			1L
		);
	}
}