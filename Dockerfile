FROM openjdk:8-jdk-alpine
ADD wechat-offiaccount-helper.jar .
ADD config/ config/
ENTRYPOINT ["java", "-jar", "wechat-offiaccount-helper.jar"]