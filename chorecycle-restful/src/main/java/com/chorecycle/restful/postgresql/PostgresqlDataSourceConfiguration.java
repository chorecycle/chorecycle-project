package com.chorecycle.restful.postgresql;

import java.net.URISyntaxException;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chorecycle.restful.configuration.EnvironmentProperties;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Configures a {@link HikariDataSource} using properties acquired by 
 * <a href="https://github.com/paulschwarz/spring-dotenv">spring-dotenv</a> from an environment variable, or from a 
 * .env file if the expected variable is not in the environment.
 */
@Configuration
@EnableConfigurationProperties(EnvironmentProperties.class)
public class PostgresqlDataSourceConfiguration {
	private final PostgresqlDbStringParser psqlDbParser;

	/**
	 * Autowired constructor for use by the framework.
	 * @param psqlDbParser - Spring should inject an {@link EnvironmentProperties} object auto-configured using 
	 * {@code app.env.*} properties
	 * @throws URISyntaxException as described in the documentation for {@code PostgresqlDbStringParser}'s sole 
	 * {@link PostgresqlDbStringParser#PostgresqlDbStringParser(EnvironmentProperties) constructor}
	 * @throws IllegalStateException as described in the documentation for {@code PostgresqlDbStringParser}'s sole 
	 * {@link PostgresqlDbStringParser#PostgresqlDbStringParser(EnvironmentProperties) constructor}
	 */
	@Autowired
	protected PostgresqlDataSourceConfiguration(EnvironmentProperties envProps)
			throws URISyntaxException, IllegalStateException {
		this.psqlDbParser = new PostgresqlDbStringParser(envProps);
	}

	/**
	 * @return properties that can be used to construct a {@link javax.sql.DataSource DataSource}
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSourceProperties getDataSourceProps() {
		DataSourceProperties props = new DataSourceProperties();
		String psqlUsername = this.psqlDbParser.getUsernameStr();
		String psqlPassword = this.psqlDbParser.getPasswordStr();

		props.setType(PGSimpleDataSource.class);
		props.setUrl(this.psqlDbParser.getJdbcUrlStr());
		props.setUsername(psqlUsername);
		props.setPassword(psqlPassword);
		
		return props;
	}

	/**
	 * @return a {@link HikariDataSource} based on a PostgreSQL database.
	 */
	@Bean(name = "psqlHikariDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariDataSource getHikariDataSource() {
		return this.getDataSourceProps().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
}