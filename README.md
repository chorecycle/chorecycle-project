# Chore Cycle
An application for tracking and collaborating on chores.

### Environment Setup

There are various files named `template.env` in the project. These are to facilitate using [Docker Compose](https://docs.docker.com/compose/) for development. (Do not rely on the production environments having support for 
Docker Compose.)

For each `template.env` file, make a copy of it in the same folder, and name the copy `.env` (with nothing before 
the dot). In each `.env` file, fill in the appropriate values for the environment variables.

The `.env` files will contain sensitive information, so make sure that `.gitignore` is configured to **NOT** include 
those files in version control.

The production environments will also need the appropriate environment variables to be set somehow.

---

One of the aforementioned environment variables is `spring.datasource.url`, which is required by the 
**chorecycle-restful** module. It contains the url of the PostgreSQL database that the Restful service should use. The 
url should be formatted as follows:

`jdbc:postgresql://server:port/path`

### Spring Profiles

Besides the default Spring profile, there is also a profile called `dev`, which should be used for development.