package com.chorecycle.restful.postgresql;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL10Dialect;

/**
 * A PostgreSQL dialect that includes the {@code Text} datatype, uses "{@code if exists}" in the "{@code drop schema}" 
 * command, and uses "{@code if not exists}" in the "{@code create schema}" command.
 */
public class CustomPostgresDialect extends PostgreSQL10Dialect {
	
	public CustomPostgresDialect() {
		super();
		registerColumnType(Types.VARCHAR, "text");
	}

	/**
	 * Get the SQL command used to drop the named schema. Overridden to use "if exists" in the "drop schema" command.
	 * @param schemaName - the name of the schema to be dropped
	 * @return the drop commands
	 */
	@Override
	public String[] getDropSchemaCommand(String schemaName) {
		return new String[] { "drop schema if exists " + schemaName };
	}

	/**
	 * Get the SQL command used to create the named schema. Overridden to use "if not exists" in the "create schema" 
	 * command.
	 * @param schemaName - the name of the schema to be created
	 * @return the creation commands
	 */
	@Override
	public String[] getCreateSchemaCommand(String schemaName) {
		return new String[] { "create schema if not exists " + schemaName };
	}
	
	
}