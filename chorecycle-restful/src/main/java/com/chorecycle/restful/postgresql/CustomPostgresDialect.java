package com.chorecycle.restful.postgresql;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL10Dialect;

/**
 * A PostgreSQL dialect that includes the {@code Text} datatype, and uses "{@code if exists}" in the 
 * "{@code drop schema}" command.
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
}