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
import static com.mjc.school.helper.Constant.AUTHOR_ID_MIN_VALUE;
import static com.mjc.school.helper.Constant.AUTHOR_NAME_ENTER;
import static com.mjc.school.helper.Constant.AUTHOR_NAME_MAX_LENGTH;
import static com.mjc.school.helper.Constant.AUTHOR_NAME_MIN_LENGTH;
import static com.mjc.school.helper.Constant.NEWS_CONTENT_ENTER;
import static com.mjc.school.helper.Constant.NEWS_CONTENT_MAX_LENGTH;
import static com.mjc.school.helper.Constant.NEWS_CONTENT_MIN_LENGTH;
import static com.mjc.school.helper.Constant.NEWS_ID;
import static com.mjc.school.helper.Constant.NEWS_ID_ENTER;
import static com.mjc.school.helper.Constant.NEWS_ID_MIN_VALUE;
import static com.mjc.school.helper.Constant.NEWS_TITLE_ENTER;
import static com.mjc.school.helper.Constant.NEWS_TITLE_MAX_LENGTH;
import static com.mjc.school.helper.Constant.NEWS_TITLE_MIN_LENGTH;
import static com.mjc.school.helper.Constant.NUMBER_OPERATION_ENTER;
import static com.mjc.school.helper.Constant.TAG_ID;
import static com.mjc.school.helper.Constant.TAG_ID_ENTER;
import static com.mjc.school.helper.Constant.TAG_ID_MIN_VALUE;
import static com.mjc.school.helper.Constant.TAG_NAME_ENTER;
import static com.mjc.school.helper.Constant.TAG_NAME_MAX_LENGTH;
import static com.mjc.school.helper.Constant.TAG_NAME_MIN_LENGTH;
import static com.mjc.school.helper.Operations.CREATE_AUTHOR;
import static com.mjc.school.helper.Operations.CREATE_NEWS;
import static com.mjc.school.helper.Operations.CREATE_TAG;
import static com.mjc.school.helper.Operations.GET_ALL_AUTHORS;
import static com.mjc.school.helper.Operations.GET_ALL_NEWS;
import static com.mjc.school.helper.Operations.GET_ALL_TAGS;
import static com.mjc.school.helper.Operations.GET_AUTHOR_BY_ID;
import static com.mjc.school.helper.Operations.GET_AUTHOR_BY_NEWS_ID;
import static com.mjc.school.helper.Operations.GET_NEWS_BY_ID;
import static com.mjc.school.helper.Operations.GET_NEWS_BY_PARAMS;
import static com.mjc.school.helper.Operations.GET_TAGS_BY_NEWS_ID;
import static com.mjc.school.helper.Operations.GET_TAG_BY_ID;
import static com.mjc.school.helper.Operations.REMOVE_AUTHOR_BY_ID;
import static com.mjc.school.helper.Operations.REMOVE_NEWS_BY_ID;
import static com.mjc.school.helper.Operations.REMOVE_TAG_BY_ID;
import static com.mjc.school.helper.Operations.UPDATE_AUTHOR;
import static com.mjc.school.helper.Operations.UPDATE_NEWS;
import static com.mjc.school.helper.Operations.UPDATE_TAG;

public class MenuHelper {

	private final ObjectMapper mapper = new ObjectMapper();
	private final Map<String, Function<Scanner, Command>> operations;
	private final PrintStream printStream;

	public MenuHelper(PrintStream printStream) {
		operations = new HashMap<>();

		operations.put(String.valueOf(GET_ALL_NEWS.getOperationNumber()), this::getNews);
		operations.put(String.valueOf(GET_NEWS_BY_ID.getOperationNumber()), this::getNewsById);
		operations.put(String.valueOf(GET_NEWS_BY_PARAMS.getOperationNumber()), this::getNewsByParams);
		operations.put(String.valueOf(CREATE_NEWS.getOperationNumber()), this::createNews);
		operations.put(String.valueOf(UPDATE_NEWS.getOperationNumber()), this::updateNews);
		operations.put(String.valueOf(REMOVE_NEWS_BY_ID.getOperationNumber()), this::deleteNews);

		operations.put(String.valueOf(GET_ALL_AUTHORS.getOperationNumber()), this::getAuthors);
		operations.put(String.valueOf(GET_AUTHOR_BY_ID.getOperationNumber()), this::getAuthorById);
		operations.put(String.valueOf(GET_AUTHOR_BY_NEWS_ID.getOperationNumber()), this::getAuthorByNewsId);
		operations.put(String.valueOf(CREATE_AUTHOR.getOperationNumber()), this::createAuthor);
		operations.put(String.valueOf(UPDATE_AUTHOR.getOperationNumber()), this::updateAuthor);
		operations.put(String.valueOf(REMOVE_AUTHOR_BY_ID.getOperationNumber()), this::deleteAuthor);

		operations.put(String.valueOf(GET_ALL_TAGS.getOperationNumber()), this::getTags);
		operations.put(String.valueOf(GET_TAG_BY_ID.getOperationNumber()), this::getTagById);
		operations.put(String.valueOf(GET_TAGS_BY_NEWS_ID.getOperationNumber()), this::getTagsByNewsId);
		operations.put(String.valueOf(CREATE_TAG.getOperationNumber()), this::createTag);
		operations.put(String.valueOf(UPDATE_TAG.getOperationNumber()), this::updateTag);
		operations.put(String.valueOf(REMOVE_TAG_BY_ID.getOperationNumber()), this::deleteTag);

		this.printStream = printStream;
	}

	public void printMainMenu() {
		printStream.println(NUMBER_OPERATION_ENTER);
		for (Operations operation : Operations.values()) {
			printStream.println(operation.getOperationWithNumber());
		}
	}

	public Command getCommand(final String key, final Scanner keyboard) {
		return operations.getOrDefault(key, this::getCommandNotFound).apply(keyboard);
	}

	private Command getCommandNotFound(final Scanner keyboard) {
		return Command.NOT_FOUND;
	}

	private Command getNews(final Scanner keyboard) {
		printStream.println(GET_ALL_NEWS.getOperation());
		return Command.GET_NEWS;
	}

	private Command getNewsById(final Scanner keyboard) {
		printStream.println(GET_NEWS_BY_ID.getOperation());
		printStream.println(NEWS_ID_ENTER);
		return new Command(
			GET_NEWS_BY_ID.getOperationNumber(),
			Map.of("id", String.valueOf(getLongWithLowerBound(NEWS_ID, NEWS_ID_MIN_VALUE, keyboard))),
			null);
	}

	private Command getNewsByParams(final Scanner keyboard) {
		throw new UnsupportedOperationException("Not implemented yet");		// FIXME
	}

	private Command createNews(final Scanner keyboard) {
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
				long authorId = getLongWithLowerBound(AUTHOR_ID, NEWS_ID_MIN_VALUE, keyboard);

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

	private Command updateNews(final Scanner keyboard) {
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
				long authorId = getLongWithLowerBound(AUTHOR_ID, AUTHOR_ID_MIN_VALUE, keyboard);

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

	private Command deleteNews(final Scanner keyboard) {
		printStream.println(REMOVE_NEWS_BY_ID.getOperation());
		printStream.println(NEWS_ID_ENTER);
		return new Command(
			REMOVE_NEWS_BY_ID.getOperationNumber(),
			Map.of("id", Long.toString(getLongWithLowerBound(NEWS_ID, NEWS_ID_MIN_VALUE, keyboard))),
			null);
	}

	private Command getAuthors(final Scanner keyboard) {
		printStream.println(GET_ALL_AUTHORS.getOperation());
		return Command.GET_AUTHORS;
	}

	private Command getAuthorById(final Scanner keyboard) {
		printStream.println(GET_AUTHOR_BY_ID.getOperation());
		printStream.println(AUTHOR_ID_ENTER);
		return new Command(
			GET_AUTHOR_BY_ID.getOperationNumber(),
			Map.of("id", String.valueOf(getLongWithLowerBound(AUTHOR_ID, AUTHOR_ID_MIN_VALUE, keyboard))),
			null);
	}

	private Command getAuthorByNewsId(final Scanner keyboard) {
		printStream.println(GET_AUTHOR_BY_NEWS_ID.getOperation());
		printStream.println(NEWS_ID_ENTER);
		return new Command(
			GET_AUTHOR_BY_NEWS_ID.getOperationNumber(),
			Map.of("id", String.valueOf(getLongWithLowerBound(NEWS_ID, NEWS_ID_MIN_VALUE, keyboard))),
			null);
	}

	private Command createAuthor(final Scanner keyboard) {
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

	private Command updateAuthor(final Scanner keyboard) {
		Command command = null;
		boolean isValid = false;
		while (!isValid) {
			try {
				printStream.println(UPDATE_AUTHOR.getOperation());
				printStream.println(AUTHOR_ID_ENTER);
				long authorId = getLongWithLowerBound(AUTHOR_ID, AUTHOR_ID_MIN_VALUE, keyboard);
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

	private Command deleteAuthor(final Scanner keyboard) {
		printStream.println(REMOVE_AUTHOR_BY_ID.getOperation());
		printStream.println(AUTHOR_ID_ENTER);
		return new Command(
			REMOVE_AUTHOR_BY_ID.getOperationNumber(),
			Map.of("id", Long.toString(getLongWithLowerBound(AUTHOR_ID, AUTHOR_ID_MIN_VALUE, keyboard))),
			null);
	}

	private Command getTags(final Scanner keyboard) {
		printStream.println(GET_ALL_TAGS.getOperation());
		return Command.GET_TAGS;
	}

	private Command getTagById(final Scanner keyboard) {
		printStream.println(GET_TAG_BY_ID.getOperation());
		printStream.println(TAG_ID_ENTER);
		return new Command(
			GET_TAG_BY_ID.getOperationNumber(),
			Map.of("id", String.valueOf(getLongWithLowerBound(TAG_ID, TAG_ID_MIN_VALUE, keyboard))),
			null);
	}

	private Command getTagsByNewsId(final Scanner keyboard) {
		printStream.println(GET_TAGS_BY_NEWS_ID.getOperation());
		printStream.println(NEWS_ID_ENTER);
		return new Command(
			GET_TAGS_BY_NEWS_ID.getOperationNumber(),
			Map.of("id", String.valueOf(getLongWithLowerBound(NEWS_ID, NEWS_ID_MIN_VALUE, keyboard))),
			null);
	}

	private Command createTag(final Scanner keyboard) {
		Command command = null;
		boolean isValid = false;
		while (!isValid) {
			try {
				printStream.println(CREATE_TAG.getOperation());
				printStream.println(TAG_NAME_ENTER);
				String name = readText(TAG_NAME_MIN_LENGTH, TAG_NAME_MAX_LENGTH, keyboard);

				Map<String, String> body = Map.of("name", name);
				command =
					new Command(CREATE_TAG.getOperationNumber(), null, mapper.writeValueAsString(body));
				isValid = true;
			} catch (Exception ex) {
				printStream.println(ex.getMessage());
			}
		}

		return command;
	}

	private Command updateTag(final Scanner keyboard) {
		Command command = null;
		boolean isValid = false;
		while (!isValid) {
			try {
				printStream.println(UPDATE_TAG.getOperation());
				printStream.println(TAG_ID_ENTER);
				long authorId = getLongWithLowerBound(TAG_ID, TAG_ID_MIN_VALUE, keyboard);
				printStream.println(TAG_NAME_ENTER);
				String name = keyboard.nextLine();

				Map<String, String> body = Map.of("id", Long.toString(authorId), "name", name);
				command =
					new Command(UPDATE_TAG.getOperationNumber(), null, mapper.writeValueAsString(body));
				isValid = true;
			} catch (Exception ex) {
				printStream.println(ex.getMessage());
			}
		}

		return command;
	}

	private Command deleteTag(final Scanner keyboard) {
		printStream.println(REMOVE_TAG_BY_ID.getOperation());
		printStream.println(TAG_ID_ENTER);
		return new Command(
			REMOVE_TAG_BY_ID.getOperationNumber(),
			Map.of("id", Long.toString(getLongWithLowerBound(TAG_ID, TAG_ID_MIN_VALUE, keyboard))),
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