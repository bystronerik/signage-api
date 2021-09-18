FROM gradle:jdk16 AS build

WORKDIR /usr/src/app
COPY build.gradle gradle.properties ./
RUN gradle build -x bootJar --continue
COPY . ./
RUN gradle build -x spotlessJavaCheck test --continue

# Stage - Production
FROM openjdk:13-slim
EXPOSE 8080
RUN mkdir /app
COPY --from=build /usr/src/app/build/libs/*.jar /app/app.jar
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/app/app.jar"]