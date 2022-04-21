# syntax=docker/dockerfile:1

FROM maven:3.8.5-eclipse-temurin-11-alpine as package-phase
WORKDIR app
COPY chorecycle-model/ chorecycle-model/
COPY chorecycle-restful/ chorecycle-restful/
COPY chorecycle-webui/ chorecycle-webui/
COPY pom.xml ./
RUN mvn package -Dmaven.test.skip

FROM maven:3.8.5-eclipse-temurin-11-alpine as extract-phase
RUN addgroup -S limited && adduser -S spring -G limited
USER spring:limited
WORKDIR app
ARG MODULE=
ARG JAR_FILE=app/$MODULE/target/*.jar
COPY --from=package-phase ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM maven:3.8.5-eclipse-temurin-11-alpine as entry-phase
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