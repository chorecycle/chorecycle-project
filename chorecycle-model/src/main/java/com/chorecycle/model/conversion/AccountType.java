package com.chorecycle.model.conversion;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Types of accounts a {@link com.chorecycle.model.User User} may have.
 */
public enum AccountType {
	BASIC	('B', "A basic account.", 4, 6),
	INVALID	('I', "An invalid account.", -1, -1);

	private final char typeCode;	
	private final String typeDesc;
	private final int maxOwnedTeams;
	private final int maxMembers;
	
	private static final Map<Character, AccountType> CODE_MAPPING
		= Collections.unmodifiableMap(new HashMap<>(mapCodes()));
	
	public static final AccountType DEFAULT_TYPE = AccountType.BASIC;

	/** Private constructor to set instance fields. */
	AccountType(char typeCode, String typeDesc, int maxOwnedTeams, int maxMembers) {
		this.typeCode = typeCode;
		this.typeDesc = typeDesc;
		this.maxOwnedTeams = maxOwnedTeams;
		this.maxMembers = maxMembers;
	}
	
	/**
	 * Returns single character that represents the {@code AccountType}.
	 */
	public char getTypeCode() {
		return this.typeCode;
	}

	/**
	 * Returns a brief description of the {@code AccountType}.
	 */
	public String getTypeDesc() {
		return this.typeDesc;
	}

	/**
	 * Returns the maximum number of standard teams that can be owned by an user with this {@code AccountType}.
	 */
	public int getMaxOwnedTeams() {
		return this.maxOwnedTeams;
	}

	/**
	 * Returns the maximum number of members that a standard team can have if owned by an user with this 
	 * {@code AccountType}.
	 */
	public int getMaxMembers() {
		return this.maxMembers;
	}
	
	public boolean isValid() {
		return !(this == INVALID);
	}
	
	/**
	 * Returns a {@link Set}&lt;Character&gt; of all single-character codes that can represent an {@code AccountType}.
	 */
	public static Set<Character> getAllTypeCodes() {
		return new HashSet<Character>(CODE_MAPPING.keySet());
	}
	
	/**
	 * Maps single-character codes to enum constants in order to make lookups more efficient.
	 * @return the completed {@code Map}
	 */
	private static Map<Character, AccountType> mapCodes() {
		AccountType[] typesArray = AccountType.values();
		Map<Character, AccountType> tempMap = new HashMap<>(typesArray.length);
		
		for (AccountType type : typesArray) {
			AccountType prevTypeForCode = tempMap.putIfAbsent(type.getTypeCode(), type);
			if (prevTypeForCode != null) {
				throw new IllegalStateException("Found duplicate code. "
						+ "Each enum constant in AccountType should have a unique code.");
			}
		}
		return tempMap;
	}
	
	/**
	 * Returns the {@code AccountType} that corresponds to the given single-character code. Case-sensitive.
	 * @param typeCode - the single-character code
	 * @return the {@code AccountType} whose single-character code is equal to {@code typeCode}
	 * @throws IllegalArgumentException if there is no {@code AccountType} whose single-character code is equal to 
	 * {@code typeCode}
	 */
	public static AccountType valueOf(char typeCode) throws IllegalArgumentException {
		final String ERR_MSG_FMT_STR = "There is no AccountType whose single-character code is %c.";
		
		AccountType result = CODE_MAPPING.get(typeCode);
		if (result == null) {
			throw new IllegalArgumentException(String.format(ERR_MSG_FMT_STR, typeCode));
		}

		return result;
	}
}