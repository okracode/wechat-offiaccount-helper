FROM openjdk:8-jdk-alpine
ADD wx-subscription.jar .
ADD config/ config/
ENTRYPOINT ["java", "-jar", "wx-subscription.jar"]