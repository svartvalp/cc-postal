FROM gradle:6.5.1-jdk11 as build
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle :ms-departure:build

FROM openjdk:11
WORKDIR /app
COPY --from=build /app/ms-departure/build/libs/*.jar /app/ms-departure.jar
ENTRYPOINT ["java","-jar","ms-departure.jar"]