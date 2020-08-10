FROM gradle:6.5.1-jdk11 as build
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle :ms-users:build

FROM openjdk:11
WORKDIR /app
COPY --from=build /app/ms-users/build/libs/*.jar /app/ms-users.jar
ENTRYPOINT ["java","-jar","ms-users.jar"]