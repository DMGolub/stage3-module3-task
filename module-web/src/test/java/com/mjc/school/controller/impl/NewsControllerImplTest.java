package com.mjc.school.controller.impl;

import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.impl.NewsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NewsControllerImplTest {

	@Mock
	private NewsServiceImpl newsService;
	@InjectMocks
	private NewsControllerImpl newsController;

	@Test
	void create_shouldInvokeServiceSaveMethod_whenInvoked() {
		final NewsRequestDto request = new NewsRequestDto(
			null,
			"Title",
			"Content",
			1L,
			null
		);

		newsController.create(request);
		verify(newsService, times(1)).create(request);
	}

	@Test
	void readAll_shouldInvokeServiceGetAllMethod_whenInvoked() {
		newsController.readAll();
		verify(newsService, times(1)).readAll();
	}

	@Test
	void readById_shouldInvokeServiceGetByIdMethod_whenInvoked() {
		final long id = 1L;

		newsController.readById(id);

		verify(newsService, times(1)).readById(id);
	}

	@Test
	void update_shouldInvokeServiceUpdateMethod_whenInvoked() {
		final NewsRequestDto request = new NewsRequestDto(
			15L,
			"Title",
			"Content",
			1L,
			null
		);

		newsController.update(request);
		verify(newsService, times(1)).update(request);
	}

	@Test
	void deleteById_shouldInvokeServiceDelete_whenInvoked() {
		final long id = 1L;

		newsController.deleteById(id);

		verify(newsService, times(1)).deleteById(id);
	}
}