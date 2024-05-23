FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/TelegramBot-test1-1.0.jar /app/TelegramBot-test1-1.0.jar
CMD ["java", "-jar", "TelegramBot-test1-1.0.jar"]
