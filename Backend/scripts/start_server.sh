#!/bin/bash

# Spring Boot 애플리케이션을 백그라운드로 실행
java -Dserver.port=80 -jar -Dspring.profiles.active=prod /home/ec2-user/server/app.jar > /dev/null 2> /dev/null < /dev/null &

# 백그라운드 실행 확인 메시지
echo "Spring Boot 애플리케이션이 백그라운드에서 실행 중입니다."