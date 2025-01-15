package com.account.validation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.util.List;

public class UniqueValidator implements ConstraintValidator<Unique, String> {

	@PersistenceContext
	private EntityManager entityManager;

	private String fieldName;
	private Class<?> entityClass;

	@Override
	public void initialize(Unique constraintAnnotation) {
		fieldName = constraintAnnotation.fieldName();
		entityClass = constraintAnnotation.entityClass();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			List<?> resultList = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e." + fieldName + " = :value")
					.setParameter("value", value)
					.getResultList();

			if (resultList.isEmpty()) {
				return true;  // Value is unique
			} else {
				// Check if the entity being validated is the same as the one being updated
				Object updatedEntity = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e." + fieldName + " = :value")
						.setParameter("value", value)
						.setMaxResults(1)
						.getSingleResult();

				return updatedEntity != null;
			}
		} catch (Exception e) {
			throw new ValidationException("Error validating uniqueness", e);
		}
	}
}
