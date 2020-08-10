FROM gradle:6.5.1-jdk11 as build
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle :ms-gateway:build

FROM openjdk:11
WORKDIR /app
COPY --from=build /app/ms-gateway/build/libs/*.jar /app/ms-gateway.jar
ENTRYPOINT ["java","-jar","ms-gateway.jar"]