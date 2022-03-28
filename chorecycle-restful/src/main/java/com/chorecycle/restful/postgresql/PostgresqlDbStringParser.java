package com.chorecycle.restful.postgresql;

import java.net.URI;
import java.net.URISyntaxException;

import com.chorecycle.restful.configuration.EnvironmentProperties;

/**
 * For parsing a PostgreSQL database url string into a form that can be used to create a 
 * {@link javax.sql.DataSource DataSource}.
 */
public class PostgresqlDbStringParser {
	/** Used in parsing a database url string, or constructing a JDBC url string. The value is ":".*/
	public static final String COLON_DELIM = ":";
	/** Used in parsing a database url string, or constructing a JDBC url string. The value is "//".*/
	public static final String DOUBLE_SLASH = "//";
	/** Used in constructing a JDBC url string. The value is "jdbc".*/
	public static final String JDBC_PREFIX = "jdbc";
	/** Used in parsing a database url string, or constructing a JDBC url string. The value is "postgresql".*/
	public static final String PSQL_PREFIX = "postgresql";
	/** Used in parsing a database url string. The value is "postgres".*/
	public static final String PSQL_SHORT = "postgres";
	
	private final String jdbcUrlStr;
	private final String usernameStr;
	private final String passwordStr;

	/**
	 * Sole constructor. <br>
	 * {@code envProps.getDbUrl()} should return a string in one of the following formats: <br><br>
	 * 
	 * {@code postgres://username:password@server:port/path} <br>
	 * {@code jdbc:postgresql://username:password@server:port/} <br><br>
	 * 
	 * There must be a slash after the port even if there is no path.
	 * 
	 * @param envProps - should be auto-configured using {@code app.env.*} properties
	 * @throws URISyntaxException if the string returned by {@code envProps.getDbUrl()} cannot be parsed as a URI 
	 * reference
	 * @throws IllegalStateException if the string returned by {@code envProps.getDbUrl()} isn't formatted specifically 
	 * as a PostgreSQL URI.
	 */
	protected PostgresqlDbStringParser(EnvironmentProperties envProps)
			throws URISyntaxException, IllegalStateException {
		URI psqlDbURI = parseDbURI(envProps.getDbUrl());
		this.jdbcUrlStr = getJdbcUrlStr(psqlDbURI);
		String[] dbUserInfo = getDbUserInfo(psqlDbURI);
		this.usernameStr = dbUserInfo[0];
		this.passwordStr = dbUserInfo[1];
	}

	/**
	 * Used by the constructor to parse a string into a PostgreSQL {@code URI}. <br>
	 * @param dbURL - see the documentation for this class's sole 
	 * {@link #PostgresqlDbStringParser(EnvironmentProperties) constructor} for information on this string's format 
	 * requirements
	 * @throws URISyntaxException as described in the documentation for this class's sole 
	 * {@link #PostgresqlDbStringParser(EnvironmentProperties) constructor} 
	 * @throws IllegalStateException as described in the documentation for this class's sole 
	 * {@link #PostgresqlDbStringParser(EnvironmentProperties) constructor}
	 */
	private static URI parseDbURI(String dbUrl) throws URISyntaxException, IllegalStateException {
		String[] urlParts = dbUrl.split(String.format("%s%s", COLON_DELIM, DOUBLE_SLASH));
		String rawUrlPrefix = urlParts[0];
		String rawUrlEnd = urlParts[1];

		URI dataBaseURI = null;

		if (rawUrlPrefix.contains(PSQL_SHORT)) {
			dataBaseURI = new URI(String.format("%s%s%s%s", PSQL_PREFIX, COLON_DELIM, DOUBLE_SLASH, rawUrlEnd));
		} else {
			throw new IllegalStateException("Not a valid postgresql database url.");
		}
		return dataBaseURI;
	}

	/**
	 * Converts a PostgreSQL {@code URI} to a JDBC url {@code String}.
	 */
	private static String getJdbcUrlStr(URI psqlDbURI) {
		StringBuilder urlStrBuilder = new StringBuilder(psqlDbURI.toString().length());
		urlStrBuilder.append(JDBC_PREFIX);
		urlStrBuilder.append(COLON_DELIM);
		urlStrBuilder.append(PSQL_PREFIX);
		urlStrBuilder.append(COLON_DELIM);
		urlStrBuilder.append(DOUBLE_SLASH);
		urlStrBuilder.append(psqlDbURI.getHost());
		urlStrBuilder.append(COLON_DELIM);
		urlStrBuilder.append(psqlDbURI.getPort());
		urlStrBuilder.append(psqlDbURI.getPath());

		return urlStrBuilder.toString();
	}

	/**
	 * Extracts just the username and password portion of the specified {@code URI}.
	 * @param psqlDbURI - a {@code URI} for a PostgreSQL database
	 * @return a {@code String} array with two elements: the first element contains the username for logging into the 
	 * database, and the second element contains the password
	 */
	private static String[] getDbUserInfo(URI psqlDbURI) {
		return psqlDbURI.getUserInfo().split(COLON_DELIM);
	}

	/**
	 * @return the JDBC url {@code String}
	 */
	public String getJdbcUrlStr() {
		return this.jdbcUrlStr;
	}

	/**
	 * @return the username {@code String}
	 */
	public String getUsernameStr() {
		return this.usernameStr;
	}

	/**
	 * @return the password {@code String}
	 */
	public String getPasswordStr() {
		return this.passwordStr;
	}
}