#!/bin/bash

echo "빌드 버전 2.0.1" >> /home/ec2-user/server/deploy.log

BUILD_JAR=$(ls /home/ec2-user/server/*.jar)
JAR_NAME=$(basename $BUILD_JAR)
echo ">>> build 파일명: $JAR_NAME" >> /home/ec2-user/server/deploy.log

echo ">>> build 파일 복사" >> /home/ec2-user/server/deploy.log

DEPLOY_JAR=/home/ec2-user/server/$JAR_NAME

echo ">>> DEPLOY_JAR 배포"    >> /home/ec2-user/server/deploy.log
nohup java -Dserver.port=80 -jar -Dspring.profiles.active=prod /home/ec2-user/server/app.jar >> /home/ec2-user/deploy.log 2>/home/ec2-user/server/deploy_err.log &

# 백그라운드 실행 확인 메시지
echo "Spring Boot 애플리케이션이 백그라운드에서 실행 중입니다."