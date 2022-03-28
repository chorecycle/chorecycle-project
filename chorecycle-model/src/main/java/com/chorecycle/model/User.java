package com.chorecycle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.chorecycle.model.conversion.AccountType;

/**
 * Data entity to represent a user account.
 */
@Entity
@Table(name = "cc_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private final long id;
	
	@Column(name = "account_type", nullable = false)
	private final AccountType accountType;

	@Column(name = "display_name", nullable = true)
	private final String displayName;
	
	@Column(nullable = false, unique = true)
	private final String email;
	
	@Column(name = "is_enabled", nullable = false)
	private final boolean isEnabled;
	
	/** Autowired constructor for use by framework. */
	@Autowired
	public User(long id, AccountType accountType, String displayName, String email, boolean isEnabled) {		
		this.id = id;
		this.accountType = accountType;
		this.displayName = displayName;
		this.email = email;
		this.isEnabled = isEnabled;
	}
	
	/**
	 * Constructor that initializes the {@code User} in an enabled state with a temporary id of -1 and the default 
	 * account type. 
	 * enabled state.
	 * @param displayName - the (non-unique) display name for the {@code User}
	 * @param email - the email address for the {@code User}
	 */
	public User(String displayName, String email) {
		this(-1, AccountType.DEFAULT_TYPE, displayName, email, true);
	}
	
	/**
	 * Constructor that initializes the {@code User} in an enabled state with a temporary id of -1, the default account 
	 * type, and no display name.
	 * @param displayName - the (non-unique) display name for the {@code User}
	 * @param email - the email address for the {@code User}
	 */
	public User(String email) {
		this("", email);
	}
	
	/** Default constructor, to satisfy JPA spec; ideally never used. */
	protected User() {
		this(0, AccountType.INVALID, "", "", false);
	}

	/**
	 * @return the user's id
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @return the type of account the user has
	 */
	public AccountType getAccountType() {
		return this.accountType;
	}

	/**
	 * @return the user's display name
	 */
	public String getDisplayName() {
		return this.displayName;
	}

	/**
	 * @return the user's email address
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @return true if the user's account is enabled, or false if it isn't
	 */
	public boolean isEnabled() {
		return this.isEnabled;
	}
}