package com.chorecycle.restful.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * Configuration properties expected to be bound from a 
 * {@link org.springframework.context.annotation.PropertySource PropertySource} provided by {@code spring-dotenv}, 
 * accessed through application properties of the form: {@code app.env.*}.
 */
@ConfigurationProperties(prefix = "app.env")
@ConstructorBinding
public class EnvironmentProperties {

	/** A url for a postgresql database, with the username and password included. */
	private final String dbUrl;
	
	/**
	 * Constructor for use by the framework, which should bind configuration properties to the parameters.
	 * @param dbUrl - a url for a PostgreSQL database 
	 * <br> the value bound to this parameter comes from the configuration property "app.env.dbUrl"
	 */
	protected EnvironmentProperties(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	/**
	 * @return the dbUrl
	 */
	public String getDbUrl() {
		return this.dbUrl;
	}
}