package com.chorecycle.model.conversion;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Allows an {@link AccountType} enum constant to be persisted as a single character.
 */
@Converter(autoApply = true)
public class AccountTypeConverter implements AttributeConverter<AccountType, Character> {

	@Override
	public Character convertToDatabaseColumn(AccountType attribute) {
		return attribute.getTypeCode();
	}

	@Override
	public AccountType convertToEntityAttribute(Character dbData) {
		return AccountType.valueOf(dbData);
	}
}