package com.mjc.school.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.controller.annotation.CommandQueryParams;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class CommandSender {

	private final Map<String, Object> controllers;
	private final ObjectMapper mapper;
	private final Map<Class<?>, Function<String, Object>> stringToObjectMappers;

	public CommandSender(final ListableBeanFactory listableBeanFactory) {
		controllers = listableBeanFactory.getBeansWithAnnotation(Controller.class);
		Objects.requireNonNull(controllers);

		mapper = new ObjectMapper();

		stringToObjectMappers = new HashMap<>();
		stringToObjectMappers.put(Long.class, Long::valueOf);
		stringToObjectMappers.put(String.class, String::valueOf);
	}

	public Object send(final Command command) throws Exception {
		if (command == null) {
			throw new NullPointerException("Command is null");
		}

		CommandHandlerWithController commandHandlerWithController =
			getCommandHandlerWithController(command.operation());

		Method commandHandler = commandHandlerWithController.method;
		Object controller = commandHandlerWithController.controller;

		try {
			return commandHandler.invoke(controller, getMethodArgs(command, commandHandler));
		} catch (InvocationTargetException ex) {
			if (ex.getTargetException() != null) {
				throw (Exception) ex.getTargetException();
			} else {
				throw ex;
			}
		}
	}

	private CommandHandlerWithController getCommandHandlerWithController(int operation) {
		List<CommandHandlerWithController> commandHandlerWithControllers = new ArrayList<>();

		for (Object controller : controllers.values()) {
			List<CommandHandlerWithController> controllerCommandHandlers =
				Stream.of(controller.getClass().getDeclaredMethods())
					.filter(method -> !method.isBridge())
					.filter(method -> method.isAnnotationPresent(CommandHandler.class))
					.filter(method -> method.getAnnotation(CommandHandler.class).operation() == operation)
					.map(method -> new CommandHandlerWithController(controller, method)).toList();

			commandHandlerWithControllers.addAll(controllerCommandHandlers);
		}

		if (commandHandlerWithControllers.size() != 1) {
			throw new RuntimeException(String.format("0 or more than 1 handlers for operation: '%d'", operation));
		}

		return commandHandlerWithControllers.get(0);
	}

	private Object[] getMethodArgs(Command command, Method method) throws JsonProcessingException {
		List<Object> args = new ArrayList<>();
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < parameterAnnotations.length; i++) {
			for (Annotation parameterAnnotation : parameterAnnotations[i]) {
				if (parameterAnnotation instanceof CommandBody) {
					Parameter parameter = method.getParameters()[i];
					args.add(mapper.readValue(command.body(), parameter.getType()));
				} else if (parameterAnnotation instanceof CommandParam an && command.params() != null) {
					Parameter parameter = method.getParameters()[i];
					String value = command.params().get(an.name());
					args.add(
						value != null
						? stringToObjectMappers.get(parameter.getType()).apply(value)
						: null
					);
				} else if (parameterAnnotation instanceof CommandQueryParams) {
					Parameter parameter = method.getParameters()[i];
					args.add(mapper.readValue(command.queryParams(), parameter.getType()));
				}
			}
		}

		return args.toArray();
	}

	private record CommandHandlerWithController(Object controller, Method method) {
		// Empty
	}
}