package com.mjc.school.repository.impl;

import com.mjc.school.repository.IdSequence;
import com.mjc.school.repository.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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
class AuthorInMemoryRepositoryTest {

	private AuthorInMemoryRepository repository;

	@BeforeEach
	void init() {
		final IdSequence<Long> longIdSequence = new InMemoryIdSequence<>(1L, prev -> prev + 1);
		repository = new AuthorInMemoryRepository(longIdSequence);
	}

	@Nested
	class TestReadAll {

		@Test
		void readAll_shouldReturnEmptyList_whenStorageIsEmpty() {
			assertEquals(Collections.emptyList(), repository.readAll());
		}

		@Test
		void readAll_shouldReturnTwoEntities_whenThereAreTwoEntitiesInTheStorage() {
			final List<Author> storage = Arrays.asList(
				createTestAuthor(1L),
				createTestAuthor(2L)
			);
			storage.forEach(repository::create);

			assertEquals(storage, repository.readAll());
		}
	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class TestReadById {

		@Test
		void readById_shouldReturnEmptyOptional_whenThereIsNoEntityWithGivenId() {
			final List<Author> storage = Arrays.asList(
				createTestAuthor(1L),
				createTestAuthor(2L)
			);
			storage.forEach(repository::create);

			assertEquals(Optional.empty(), repository.readById(3L));
		}

		@Test
		void readById_shouldReturnEntity_whenEntityWithGivenIdExists() {
			Author expected = createTestAuthor(2L);
			repository.create(expected);

			assertEquals(Optional.of(expected), repository.readById(expected.getId()));
		}
	}

	@Nested
	class TestCreate {

		@Test
		void create_shouldSaveEntityAndReturnEntityWithId1_whenStorageIsEmpty() {
			Author author = createTestAuthor(null);
			Author expected = createTestAuthor(1L);

			assertEquals(expected, repository.create(author));
			assertEquals(1, repository.readAll().size());
		}

		@Test
		void create_shouldSaveEntityAndReturnEntityWithId3_whenStorageContainsTwoEntities() {
			final List<Author> storage = List.of(
				createTestAuthor(1L),
				createTestAuthor(2L)
			);
			storage.forEach(repository::create);

			Author author = createTestAuthor(null);
			Author expected = createTestAuthor(3L);

			assertEquals(expected, repository.create(author));
			assertEquals(3, repository.readAll().size());
		}
	}

	@Nested
	class TestUpdate {

		@Test
		void update_shouldThrowNoSuchElementException_whenThereIsNoEntityWithGivenId() {
			final List<Author> storage = List.of(
				createTestAuthor(1L),
				createTestAuthor(2L)
			);
			storage.forEach(repository::create);

			Author updated = createTestAuthor(99L);

			assertThrows(NoSuchElementException.class, () -> repository.update(updated));
		}

		@Test
		void update_shouldReturnUpdatedEntity_whenEntityIsValid() {
			final List<Author> storage = List.of(
				createTestAuthor(1L),
				createTestAuthor(2L)
			);
			storage.forEach(repository::create);

			final Author updated = createTestAuthor(2L);
			updated.setName("Updated name");
			List<Author> expected = Arrays.asList(createTestAuthor(1L), updated);

			assertEquals(updated, repository.update(updated));
			assertEquals(expected, repository.readAll());
		}
	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class TestDeleteById {

		@Test
		void delete_shouldReturnFalse_whenIdIsNull() {
			final List<Author> storage = Arrays.asList(
				createTestAuthor(1L),
				createTestAuthor(2L)
			);
			storage.forEach(repository::create);

			assertFalse(repository.deleteById(null));
			assertEquals(2, repository.readAll().size());
		}

		@Test
		void delete_shouldReturnFalse_whenStorageIsEmpty() {
			assertFalse(repository.deleteById(99L));
			assertTrue(repository.readAll().isEmpty());
		}

		@Test
		void delete_shouldReturnFalse_whenThereIsNoEntityWithGivenId() {
			final List<Author> storage = Arrays.asList(
				createTestAuthor(1L),
				createTestAuthor(2L)
			);
			storage.forEach(repository::create);

			assertFalse(repository.deleteById(99L));
			assertEquals(2, repository.readAll().size());
		}

		@Test
		void delete_shouldReturnTrue_whenEntityWithGivenIdDeleted() {
			final List<Author> storage = Arrays.asList(
				createTestAuthor(1L),
				createTestAuthor(2L)
			);
			storage.forEach(repository::create);

			assertTrue(repository.deleteById(2L));
			assertEquals(1, repository.readAll().size());
		}

		@Test
		void delete_shouldDeleteEntity_whenItIsSingleEntityInTheStorage() {
			repository.create(createTestAuthor(null));

			assertTrue(repository.deleteById(1L));
			assertTrue(repository.readAll().isEmpty());
		}
	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class TestExistById {

		@Test
		void existById_shouldReturnFalse_whenIdIsNull() {
			assertFalse(repository.existById(null));
		}

		@Test
		void existById_shouldReturnFalse_whenStorageIsEmpty() {
			final long id = 99L;
			assertFalse(repository.existById(id));
		}

		@Test
		void existById_shouldReturnFalse_whenThereIsNoEntityWithGivenId() {
			final List<Author> storage = Arrays.asList(
				createTestAuthor(1L),
				createTestAuthor(2L)
			);
			storage.forEach(repository::create);

			assertFalse(repository.existById(99L));
		}

		@Test
		void existById_shouldReturnTrue_whenEntityWithGivenIdExists() {
			final List<Author> storage = Arrays.asList(
				createTestAuthor(1L),
				createTestAuthor(2L)
			);
			storage.forEach(repository::create);

			assertTrue(repository.existById(2L));
		}
	}

	private Author createTestAuthor(Long authorId) {
		return new Author(
			authorId,
			"Author Name",
			LocalDateTime.of(2023, 7, 17, 16, 30, 0),
			LocalDateTime.of(2023, 7, 17, 16, 30, 0)
		);
	}
}