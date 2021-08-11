FROM openjdk:8-jdk-alpine
ADD wx-subscription-web/dist wx-subscription-web/dist
ENTRYPOINT ["sh", "wx-subscription-web/dist/bin/wechat-intfc.sh", "start"]