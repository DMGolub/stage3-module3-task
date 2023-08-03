package com.mjc.school.repository.aspect;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.action.OnDelete;
import com.mjc.school.repository.model.BaseEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.mjc.school.repository.action.Operation.REMOVE;
import static com.mjc.school.repository.action.Operation.SET_NULL;

@Aspect
@Component
@SuppressWarnings({"unchecked", "rawtypes"})
public class OnDeleteActionAspect {

	private final Map<String, BaseRepository> proxyRepositories;

	@Autowired
	public OnDeleteActionAspect(ListableBeanFactory listableBeanFactory) {
		proxyRepositories = listableBeanFactory.getBeansOfType(BaseRepository.class);
	}

	@Before("execution(public * com.mjc.school.repository.BaseRepository.deleteById(..))")
	public void processChildEntitiesBeforeDeletion(JoinPoint joinPoint) throws Exception {
		Type parentEntityType = getParentEntityType(joinPoint);
		Field[] parentEntityTypeFields = ((Class<?>) parentEntityType).getDeclaredFields();
		List<Field> annotatedParentEntityFields = getParentEntityFieldsWithOnDeleteAnnotation(parentEntityTypeFields);

		for (Field annotatedField : annotatedParentEntityFields) {
			OnDelete annotation = annotatedField.getAnnotation(OnDelete.class);

			Type childEntityType = getChildEntityType(annotatedField);
			Field childEntityForeignKeyField = ((Class<?>) childEntityType).getDeclaredField(annotation.fieldName());

			BaseRepository childProxyRepo = getChildProxyRepo(childEntityType);
			processOnDeleteForChildEntities(joinPoint, annotation, childProxyRepo, childEntityForeignKeyField);
		}
	}

	private Type getParentEntityType(JoinPoint joinPoint) {
		return ((ParameterizedType) joinPoint.getTarget()
			.getClass().getGenericSuperclass())
			.getActualTypeArguments()[0];
	}

	private List<Field> getParentEntityFieldsWithOnDeleteAnnotation(Field[] parentEntityFields) {
		return Stream.of(parentEntityFields)
			.filter(f -> f.isAnnotationPresent(OnDelete.class))
			.toList();
	}

	private Type getChildEntityType(Field field) {
		return field.getGenericType() instanceof ParameterizedType pt
			? pt.getActualTypeArguments()[0]
			: field.getType();
	}

	private BaseRepository getChildProxyRepo(Type childEntityType) throws Exception {
		List<BaseRepository> childProxyRepos = new ArrayList<>();

		for (var proxyRepo : proxyRepositories.values()) {
			var originalRepo = getOriginalRepo(proxyRepo);
			var repoEntityType = getOriginalRepoEntityType(originalRepo);
			if (childEntityType.equals(repoEntityType)) {
				childProxyRepos.add(proxyRepo);
			}
		}

		if (childProxyRepos.size() != 1) {
			throw new RuntimeException(String.format("0 or more than 1 BaseRepository for '%s'", childEntityType));
		}

		return childProxyRepos.get(0);
	}

	private BaseRepository getOriginalRepo(BaseRepository proxiedRepo) throws Exception {
		return AopUtils.isJdkDynamicProxy(proxiedRepo)
			? (BaseRepository) ((Advised) proxiedRepo).getTargetSource().getTarget()
			: proxiedRepo;
	}

	private Type getOriginalRepoEntityType(BaseRepository originalRepo) {
		return ((ParameterizedType) originalRepo
			.getClass().getSuperclass().getGenericSuperclass())
			.getActualTypeArguments()[0];
	}

	private void processOnDeleteForChildEntities(JoinPoint joinPoint, OnDelete annotation,
		BaseRepository childProxyRepo, Field childEntityForeignKeyField) throws IllegalAccessException {

		List allChildEntities = childProxyRepo.readAll();

		for (Object childEntity : allChildEntities) {
			if (childEntityForeignKeyField.trySetAccessible()
				&& childEntityForeignKeyField.canAccess(childEntity)
				&& childEntityForeignKeyField.get(childEntity).equals(joinPoint.getArgs()[0])) {

				if (annotation.operation() == SET_NULL) {
					childEntityForeignKeyField.set(childEntity, null);
				} else if (annotation.operation() == REMOVE) {
					childProxyRepo.deleteById(((BaseEntity<?>) childEntity).getId());
				}
			}
		}
	}
}