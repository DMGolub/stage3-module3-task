package com.mjc.school.controller.impl;

import com.mjc.school.service.dto.TagRequestDto;
import com.mjc.school.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TagControllerImplTest {

	@Mock
	private TagServiceImpl tagService;
	@InjectMocks
	private TagControllerImpl tagController;

	@Test
	void create_shouldInvokeServiceSaveMethod_whenInvoked() {
		final TagRequestDto request = new TagRequestDto(null, "Tag Name");

		tagController.create(request);
		verify(tagService, times(1)).create(request);
	}

	@Test
	void readAll_shouldInvokeServiceGetAllMethod_whenInvoked() {
		tagController.readAll();
		verify(tagService, times(1)).readAll();
	}

	@Test
	void readById_shouldInvokeServiceGetByIdMethod_whenInvoked() {
		final long id = 1L;

		tagController.readById(id);

		verify(tagService, times(1)).readById(id);
	}

	@Test
	void update_shouldInvokeServiceUpdateMethod_whenInvoked() {
		final TagRequestDto request = new TagRequestDto(15L, "Tag Name");

		tagController.update(request);
		verify(tagService, times(1)).update(request);
	}

	@Test
	void deleteById_shouldInvokeServiceDelete_whenInvoked() {
		final long id = 1L;

		tagController.deleteById(id);

		verify(tagService, times(1)).deleteById(id);
	}
}