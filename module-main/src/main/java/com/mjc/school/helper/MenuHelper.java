package com.mjc.school.helper;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import static com.mjc.school.helper.Constant.AUTHOR_ID;
import static com.mjc.school.helper.Constant.AUTHOR_ID_ENTER;
import static com.mjc.school.helper.Constant.AUTHOR_NAME_ENTER;
import static com.mjc.school.helper.Constant.AUTHOR_NAME_MAX_LENGTH;
import static com.mjc.school.helper.Constant.AUTHOR_NAME_MIN_LENGTH;
import static com.mjc.school.helper.Constant.NEWS_CONTENT_ENTER;
import static com.mjc.school.helper.Constant.NEWS_CONTENT_MAX_LENGTH;
import static com.mjc.school.helper.Constant.NEWS_CONTENT_MIN_LENGTH;
import static com.mjc.school.helper.Constant.NEWS_ID;
import static com.mjc.school.helper.Constant.NEWS_ID_ENTER;
import static com.mjc.school.helper.Constant.NEWS_TITLE_ENTER;
import static com.mjc.school.helper.Constant.NEWS_TITLE_MAX_LENGTH;
import static com.mjc.school.helper.Constant.NEWS_TITLE_MIN_LENGTH;
import static com.mjc.school.helper.Constant.NUMBER_OPERATION_ENTER;
import static com.mjc.school.helper.Operations.CREATE_AUTHOR;
import static com.mjc.school.helper.Operations.CREATE_NEWS;
import static com.mjc.school.helper.Operations.GET_ALL_AUTHORS;
import static com.mjc.school.helper.Operations.GET_ALL_NEWS;
import static com.mjc.school.helper.Operations.GET_AUTHOR_BY_ID;
import static com.mjc.school.helper.Operations.GET_NEWS_BY_ID;
import static com.mjc.school.helper.Operations.REMOVE_AUTHOR_BY_ID;
import static com.mjc.school.helper.Operations.REMOVE_NEWS_BY_ID;
import static com.mjc.school.helper.Operations.UPDATE_AUTHOR;
import static com.mjc.school.helper.Operations.UPDATE_NEWS;

public class MenuHelper {

	private final ObjectMapper mapper = new ObjectMapper();
	private final Map<String, Function<Scanner, Command>> operations;
	private final PrintStream printStream;

	public MenuHelper(PrintStream printStream) {
		operations = new HashMap<>();

		operations.put(String.valueOf(GET_ALL_NEWS.getOperationNumber()), this::getNews);
		operations.put(String.valueOf(GET_NEWS_BY_ID.getOperationNumber()), this::getNewsById);
		operations.put(String.valueOf(CREATE_NEWS.getOperationNumber()), this::createNews);
		operations.put(String.valueOf(UPDATE_NEWS.getOperationNumber()), this::updateNews);
		operations.put(String.valueOf(REMOVE_NEWS_BY_ID.getOperationNumber()), this::deleteNews);

		operations.put(String.valueOf(GET_ALL_AUTHORS.getOperationNumber()), this::getAuthors);
		operations.put(String.valueOf(GET_AUTHOR_BY_ID.getOperationNumber()), this::getAuthorById);
		operations.put(String.valueOf(CREATE_AUTHOR.getOperationNumber()), this::createAuthor);
		operations.put(String.valueOf(UPDATE_AUTHOR.getOperationNumber()), this::updateAuthor);
		operations.put(String.valueOf(REMOVE_AUTHOR_BY_ID.getOperationNumber()), this::deleteAuthor);

		this.printStream = printStream;
	}

	public void printMainMenu() {
		printStream.println(NUMBER_OPERATION_ENTER);
		for (Operations operation : Operations.values()) {
			printStream.println(operation.getOperationWithNumber());
		}
	}

	public Command getCommand(String key, Scanner keyboard) {
		return operations.getOrDefault(key, this::getCommandNotFound).apply(keyboard);
	}

	private Command getCommandNotFound(Scanner keyboard) {
		return Command.NOT_FOUND;
	}

	private Command getNews(Scanner keyboard) {
		printStream.println(GET_ALL_NEWS.getOperation());
		return Command.GET_NEWS;
	}

	private Command getNewsById(Scanner keyboard) {
		printStream.println(GET_NEWS_BY_ID.getOperation());
		printStream.println(NEWS_ID_ENTER);
		return new Command(
			GET_NEWS_BY_ID.getOperationNumber(),
			Map.of("id", String.valueOf(getLongWithLowerBound(NEWS_ID, 1L, keyboard))),
			null);
	}

	private Command createNews(Scanner keyboard) {
		Command command = null;
		boolean isValid = false;
		while (!isValid) {
			try {
				printStream.println(CREATE_NEWS.getOperation());
				printStream.println(NEWS_TITLE_ENTER);
				String title = readText(NEWS_TITLE_MIN_LENGTH, NEWS_TITLE_MAX_LENGTH, keyboard);
				printStream.println(NEWS_CONTENT_ENTER);
				String content = readText(NEWS_CONTENT_MIN_LENGTH, NEWS_CONTENT_MAX_LENGTH, keyboard);
				printStream.println(AUTHOR_ID_ENTER);
				long authorId = getLongWithLowerBound(AUTHOR_ID, 1L, keyboard);

				Map<String, String> body =
					Map.of("title", title, "content", content, "authorId", Long.toString(authorId));

				command =
					new Command(CREATE_NEWS.getOperationNumber(), null, mapper.writeValueAsString(body));
				isValid = true;
			} catch (Exception ex) {
				printStream.println(ex.getMessage());
			}
		}

		return command;
	}

	private Command updateNews(Scanner keyboard) {
		Command command = null;
		boolean isValid = false;
		while (!isValid) {
			try {
				printStream.println(UPDATE_NEWS.getOperation());
				printStream.println(NEWS_ID_ENTER);
				long newsId = getLongWithLowerBound(NEWS_ID, 1L, keyboard);
				printStream.println(NEWS_TITLE_ENTER);
				String title = readText(NEWS_TITLE_MIN_LENGTH, NEWS_TITLE_MAX_LENGTH, keyboard);
				printStream.println(NEWS_CONTENT_ENTER);
				String content = readText(NEWS_CONTENT_MIN_LENGTH, NEWS_CONTENT_MAX_LENGTH, keyboard);
				printStream.println(AUTHOR_ID_ENTER);
				long authorId = getLongWithLowerBound(AUTHOR_ID, 1L, keyboard);

				Map<String, String> body =
					Map.of(
						"id",
						Long.toString(newsId),
						"title",
						title,
						"content",
						content,
						"authorId",
						Long.toString(authorId));

				command =
					new Command(UPDATE_NEWS.getOperationNumber(), null, mapper.writeValueAsString(body));
				isValid = true;
			} catch (Exception ex) {
				printStream.println(ex.getMessage());
			}
		}

		return command;
	}

	private Command deleteNews(Scanner keyboard) {
		printStream.println(REMOVE_NEWS_BY_ID.getOperation());
		printStream.println(NEWS_ID_ENTER);
		return new Command(
			REMOVE_NEWS_BY_ID.getOperationNumber(),
			Map.of("id", Long.toString(getLongWithLowerBound(NEWS_ID, 1L, keyboard))),
			null);
	}

	private Command getAuthors(Scanner keyboard) {
		printStream.println(GET_ALL_AUTHORS.getOperation());
		return Command.GET_AUTHORS;
	}

	private Command getAuthorById(Scanner keyboard) {
		printStream.println(GET_AUTHOR_BY_ID.getOperation());
		printStream.println(AUTHOR_ID_ENTER);
		return new Command(
			GET_AUTHOR_BY_ID.getOperationNumber(),
			Map.of("id", String.valueOf(getLongWithLowerBound(AUTHOR_ID, 1L, keyboard))),
			null);
	}

	private Command createAuthor(Scanner keyboard) {
		Command command = null;
		boolean isValid = false;
		while (!isValid) {
			try {
				printStream.println(CREATE_AUTHOR.getOperation());
				printStream.println(AUTHOR_NAME_ENTER);
				String name = readText(AUTHOR_NAME_MIN_LENGTH, AUTHOR_NAME_MAX_LENGTH, keyboard);

				Map<String, String> body = Map.of("name", name);
				command =
					new Command(CREATE_AUTHOR.getOperationNumber(), null, mapper.writeValueAsString(body));
				isValid = true;
			} catch (Exception ex) {
				printStream.println(ex.getMessage());
			}
		}

		return command;
	}

	private Command updateAuthor(Scanner keyboard) {
		Command command = null;
		boolean isValid = false;
		while (!isValid) {
			try {
				printStream.println(UPDATE_AUTHOR.getOperation());
				printStream.println(AUTHOR_ID_ENTER);
				long authorId = getLongWithLowerBound(AUTHOR_ID, 1L, keyboard);
				printStream.println(AUTHOR_NAME_ENTER);
				String name = keyboard.nextLine();

				Map<String, String> body = Map.of("id", Long.toString(authorId), "name", name);
				command =
					new Command(UPDATE_AUTHOR.getOperationNumber(), null, mapper.writeValueAsString(body));
				isValid = true;
			} catch (Exception ex) {
				printStream.println(ex.getMessage());
			}
		}

		return command;
	}

	private Command deleteAuthor(Scanner keyboard) {
		printStream.println(REMOVE_AUTHOR_BY_ID.getOperation());
		printStream.println(AUTHOR_ID_ENTER);
		return new Command(
			REMOVE_AUTHOR_BY_ID.getOperationNumber(),
			Map.of("id", Long.toString(getLongWithLowerBound(AUTHOR_ID, 1L, keyboard))),
			null);
	}

	public String readText(final int minLength,	final int maxLength, final Scanner keyboard) {
		String result = null;
		do {
			printStream.print(">");
			String tmp = keyboard.nextLine();
			if (tmp.length() < minLength || tmp.length() > maxLength) {
				printStream.printf("Text length must be between %d and %d. Please try again:", minLength, maxLength);
				printStream.print(">");
				continue;
			}
			result = tmp;
		} while (result == null);
		return result;
	}

	public long getLongWithLowerBound(final String message,	final long lowerBound, final Scanner keyboard) {
		printStream.println(message);
		Long result = null;
		do {
			printStream.print(">");
			try {
				result = keyboard.nextLong();
				if (result < lowerBound) {
					printStream.printf("Number must be %d or greater. Please try again:%n", lowerBound);
					result = null;
				}
			} catch (InputMismatchException e) {
				printStream.println("Input is not a number. Please try again:");
			}
			keyboard.nextLine();
		} while (result == null);
		return result;
	}
}