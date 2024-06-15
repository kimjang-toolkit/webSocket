#!/bin/bash

PORT=80

# 80 포트를 사용 중인 프로세스의 PID 찾기
PID=$(lsof -t -i:$PORT)

if [ -z "$PID" ]; then
  echo "포트 $PORT를 사용 중인 프로세스가 없습니다."
else
  # 프로세스 종료
  echo "포트 $PORT를 사용 중인 프로세스를 종료합니다. (PID: $PID)"
  kill -9 "$PID"
fi
