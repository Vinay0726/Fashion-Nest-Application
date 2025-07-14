#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean install

#
# Package stage
#
FROM eclipse-temurin:17-jdk
COPY --from=build /target/Fashion-Nest-Application-0.0.1-SNAPSHOT.jar Fashion-Nest-Application.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","Fashion-Nest-Application.jar"]