package com.mjc.school.repository.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;
import java.util.Random;

@Configuration
@EnableTransactionManagement
public class RepositoryConfig {

	private static final String[] ENTITY_PACKAGES = {"com.mjc.school.repository.model"};

	private static final String AUTHORS_FILE_NAME = "authors";
	private static final String CONTENT_FILE_NAME = "content";
	private static final String NEWS_FILE_NAME = "news";
	private static final int AUTHOR_COUNT = 20;
	private static final int NEWS_COUNT = 20;
	private final Random random = new Random();

	@Bean
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("sa");
		dataSource.setPassword("password");
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:newsdb;DB_CLOSE_DELAY=-1");
		return dataSource;
	}

	@Bean
	public JpaTransactionManager jpaTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory =
			new LocalContainerEntityManagerFactoryBean();

		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactory.setPackagesToScan(ENTITY_PACKAGES);
		entityManagerFactory.setJpaProperties(addProperties());

		return entityManagerFactory;
	}

	private Properties addProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.connection.pool_size", "3");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		return properties;
	}

	/*
	@Bean
	public BaseRepository<Author, Long> authorRepository() {
		final BaseRepository<Author, Long> authorRepository = new AuthorRepository();
		initializeAuthorsRepository(authorRepository);
		return authorRepository;
	}

	@Bean
	public BaseRepository<News, Long> newsRepository(
		final IdSequence<Long> idSequence,
		final BaseRepository<Author, Long> authorRepository
	) {
		final BaseRepository<News, Long> newsRepository = new NewsInMemoryRepository(idSequence);
		//initializeNewsRepository(newsRepository, authorRepository);
		return newsRepository;
	}

	private void initializeAuthorsRepository(final BaseRepository<Author, Long> authorRepository) {
		List<String> authors = loadDataFromFile(AUTHORS_FILE_NAME);
		Stream.generate(
				() -> {
					LocalDateTime date = getRandomDate();
					return new Author(null, getRandomElement(authors), date, date);
				})
			.limit(AUTHOR_COUNT)
			.forEach(authorRepository::create);
	}

	private void initializeNewsRepository(
		final BaseRepository<News, Long> newsRepository,
		final BaseRepository<Author, Long> authorRepository
	) {
		final List<String> titles = loadDataFromFile(NEWS_FILE_NAME);
		final List<String> contents = loadDataFromFile(CONTENT_FILE_NAME);
		final List<Author> authors = authorRepository.readAll();

		Stream.generate(
				() -> {
					var date = getRandomDate();
					return new News(
						null,
						getRandomElement(titles),
						getRandomElement(contents),
						date,
						date,
						getRandomElement(authors));
				})
			.limit(NEWS_COUNT)
			.forEach(newsRepository::create);
	}

	private List<String> loadDataFromFile(final String fileName) {
		List<String> lines = Collections.emptyList();

		try(final var reader =
				new BufferedReader(
					new InputStreamReader(
						Objects.requireNonNull(Thread
							.currentThread()
							.getContextClassLoader()
							.getResourceAsStream(fileName)),
						StandardCharsets.UTF_8
					)
				)
		) {
			lines = reader.lines().toList();
			if (lines.isEmpty()) {
				throw new IllegalStateException("File must not be empty");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}

	private <T> T getRandomElement(final List<? extends T> list) {
		return list.get(random.nextInt(list.size()));
	}

	private LocalDateTime getRandomDate() {
		int endDay = 30;
		LocalDate day = LocalDate.now().minusDays(random.nextInt(endDay));
		int hour = random.nextInt(24);
		int minute = random.nextInt(60);
		int second = random.nextInt(60);
		LocalTime time = LocalTime.of(hour, minute, second);
		return LocalDateTime.of(day, time);
	}
	*/
}