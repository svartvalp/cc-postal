FROM gradle:6.5.1-jdk11 as build
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle :ms-navigation:build

FROM openjdk:11
WORKDIR /app
COPY --from=build /app/ms-navigation/build/libs/*.jar /app/ms-navigation.jar
ENTRYPOINT ["java","-jar","ms-navigation.jar"]