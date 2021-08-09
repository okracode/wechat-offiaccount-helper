FROM openjdk:8-jdk-alpine
ADD dist dist
ENTRYPOINT ["sh", "dist/bin/wechat-intfc.sh"]