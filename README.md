# Chore Cycle
An application for tracking and collaborating on chores.

### Environment Setup

The **chorecycle-restful** module requires an environment variable called `database.url` containing the url of the PostgreSQL database that it should use. The url should be formatted as follows:

`jdbc:postgresql://username:password@server:port/path`

If the url does not have a path, it should end with a slash after the port.

This url should be kept secret, so it's important that .gitignore is configured to **NOT** include it in version control.

### Spring Profiles

Besides the default Spring profile, there is also a profile called `dev`. The `dev` profile should be used for development.