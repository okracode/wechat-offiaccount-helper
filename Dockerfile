FROM openjdk:8-jdk-alpine
ADD wx-subscription.jar .
ENTRYPOINT ["java", "-jar", "wx-subscription.jar"]