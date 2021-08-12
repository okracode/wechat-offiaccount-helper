FROM openjdk:8-jdk-alpine
ADD wx-subscription-web-1.0.0.jar .
ENTRYPOINT ["java", "-jar", "wx-subscription-web-1.0.0.jar"]