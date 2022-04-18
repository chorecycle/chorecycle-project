# syntax=docker/dockerfile:1
FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.14.1_1-slim as extract-phase
RUN addgroup -S limited && adduser -S spring -G limited
USER spring:limited
WORKDIR app
ARG MODULE=
ARG JAR_FILE=/$MODULE/target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.14.1_1-slim as entry-phase
RUN addgroup -S limited && adduser -S spring -G limited
USER spring:limited
WORKDIR app
COPY --from=extract-phase app/dependencies/ ./
COPY --from=extract-phase app/spring-boot-loader/ ./
COPY --from=extract-phase app/snapshot-dependencies/ ./
COPY --from=extract-phase app/application/ ./
ENV SPRING_DATASOURCE_URL=
ENV SPRING_DATASOURCE_USERNAME=
ENV SPRING_DATASOURCE_PASSWORD=
ENV SPRING_PROFILES_ACTIVE=
ENV PORT=
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]