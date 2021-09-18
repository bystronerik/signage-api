FROM gradle:jdk16 AS build

WORKDIR /usr/src/app
RUN mkdir -p src/main/java
COPY build.gradle gradle.properties ./
RUN gradle build
COPY . ./
RUN gradle build -x spotlessJavaCheck test --continue

# Stage - Production
FROM openjdk:8-jre-slim
EXPOSE 8080
RUN mkdir /app
COPY --from=build /usr/src/app/build/libs/*.jar /app/app.jar
CMD ["java", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "-Dspring.profiles.active=prod", "/app/app.jar"]