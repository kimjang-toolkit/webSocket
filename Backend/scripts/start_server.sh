#!/bin/bash

# Find the latest jar file
BUILD_JAR=$(ls /home/ec2-user/server/solsol-*.jar | head -n 1)
JAR_NAME=$(basename $BUILD_JAR)

# Extract version number from jar file name
VERSION=$(echo $JAR_NAME | sed -n 's/solsol-\([0-9]*\.[0-9]*\.[0-9]*\)\.jar/\1/p')

echo "빌드 버전 $VERSION" >> /home/ec2-user/server/deploy.log
echo ">>> build 파일명: $JAR_NAME" >> /home/ec2-user/server/deploy.log

echo ">>> build 파일 복사" >> /home/ec2-user/server/deploy.log

DEPLOY_JAR=/home/ec2-user/server/$JAR_NAME

echo ">>> DEPLOY_JAR 배포" >> /home/ec2-user/server/deploy.log
nohup java -Dserver.port=80 -jar -Dspring.profiles.active=prod $DEPLOY_JAR >> /home/ec2-user/server/deploy.log 2>/home/ec2-user/server/deploy_err.log &

# 백그라운드 실행 확인 메시지
echo "Spring Boot 애플리케이션이 백그라운드에서 실행 중입니다." >> /home/ec2-user/server/deploy.log