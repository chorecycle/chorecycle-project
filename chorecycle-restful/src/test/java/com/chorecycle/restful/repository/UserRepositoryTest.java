package com.chorecycle.restful.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.chorecycle.model.User;
import com.chorecycle.model.conversion.AccountType;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider;

/**
 * Tests basic database functioning for the {@link User} entity.
 */
@DataJpaTest
@AutoConfigureEmbeddedDatabase(provider = DatabaseProvider.ZONKY)
@TestPropertySource("/default-test.properties")
@DisplayName("The UserRepository")
class UserRepositoryTest {
	private UserRepository userRepo;

	/**
	 * Autowired constructor for use by the framework.
	 * @param userRepo - Spring should inject an auto-configured {@link UserRepository}
	 */
	@Autowired
	public UserRepositoryTest(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Test
	@DisplayName("can save users to the db and retrieve them as expected")
	void testSave() {
		String displayName = "Test User";
		String testEmail = "test@test.com";
		User testUser = new User(displayName, testEmail);
		User savedUser = userRepo.save(testUser);
		
		System.out.println("ID: " + savedUser.getId());
		assertTrue(savedUser.getId() > 0, "Retrieved ID was not higher than zero.");
		assertTrue(savedUser.getAccountType() == AccountType.DEFAULT_TYPE,
				"Retrieved account type was not the default.");
		assertTrue(savedUser.getDisplayName().equals(displayName),
				"Retrieved display name was not \"" + displayName + "\".");
		assertTrue(savedUser.getEmail().equals(testEmail),
				"Retrieved email was not \"" + testEmail + "\".");
		assertTrue(savedUser.isEnabled(), "Retrieved User was not enabled.");
	}
}