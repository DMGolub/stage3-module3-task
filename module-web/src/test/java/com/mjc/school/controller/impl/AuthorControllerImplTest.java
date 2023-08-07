package com.mjc.school.controller.impl;

import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorControllerImplTest {

	@Mock
	private AuthorServiceImpl authorService;
	@InjectMocks
	private AuthorControllerImpl authorController;

	@Test
	void create_shouldInvokeServiceSaveMethod_whenInvoked() {
		final AuthorRequestDto request = new AuthorRequestDto(null, "Author Name");

		authorController.create(request);
		verify(authorService, times(1)).create(request);
	}

	@Test
	void readAll_shouldInvokeServiceGetAllMethod_whenInvoked() {
		authorController.readAll();
		verify(authorService, times(1)).readAll();
	}

	@Test
	void readById_shouldInvokeServiceGetByIdMethod_whenInvoked() {
		final long id = 1L;

		authorController.readById(id);

		verify(authorService, times(1)).readById(id);
	}

	@Test
	void update_shouldInvokeServiceUpdateMethod_whenInvoked() {
		final AuthorRequestDto request = new AuthorRequestDto(15L, "Author Name");

		authorController.update(request);
		verify(authorService, times(1)).update(request);
	}

	@Test
	void deleteById_shouldInvokeServiceDelete_whenInvoked() {
		final long id = 1L;

		authorController.deleteById(id);

		verify(authorService, times(1)).deleteById(id);
	}
}