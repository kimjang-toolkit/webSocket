## WebSocket with SpringBoot

### How to use

빌드 파일 실행
`sudo java -jar -Dserver.port=80 Backend/target/solsol-0.1.1.jar`
`sudo nohup java -Dserver.port=80 -jar webSocket/Backend/target/solsol-0.1.1.jar &`
Simple chatting room 연결 [localhost:8080](http://localhost:8080) 
Swagger-ui 연결 : `http://{server host}/swagger-ui/index.html`

API DOC : `http://{server host}/api-docs`

테스트 없이 빌드 : `mvn install -DskipTests -P prod`

## Concept

### What is WebSocket

WebSocket은 클라이언트와 서버가 지속적으로 연결을 유지하면서 통신할 수 있는 저수준 프로토콜입니다. <br/>
매 통신 마다 3-way hand shack하는 HTTP와 달리 연결을 유지하며 낮은 지연 시간으로 양방향 통신을 지원합니다. <br/>
간단한 메세지만 주고 받을 때는 WebSocket으로 충분하지만, 에러 처리나 로그인, 헬스 체크 같은 고수준 요구사항을 만족 못합니다.

### What is STOMP

메세지 지향적 통신규약인 STOMP 프로토콜을 이용해서 WebSocket 위에서 비동기적으로 메세지를 주고 받을 수 있습니다. <br/>
클라이언트는 topic과 subscribe id를 통해 메세지 큐에 있는 메세지를 공유 받을 수 있습니다. <br/>
Spring 서버는 메세지 브로커 역할을 하며 토픽을 구독한 클라이언트(로부터, 에게) 메세지를 받거나 전달합니다. <br/>
더 나아가 메세지 브로커를 위해 **RabbitMQ**, **ActiveMQ** 같은 브로커를 따로 사용할 수 있습니다.

아래 uri를 통해 서버에 websocket 등록
**webSocket 등록을 위한 uri** : [{server-url}/gs-guide-websocket](/gs-guide-websocket)

### Why use message broker

WebSocket은 단순히 양방향 통신인데 왜 메세지 브로커를 사용하는 것인가?
그 이유는 다수의 클라이언트와 서버가 동시에 통신하려면 시스템의 수평적 확장이 필수적이고, 클라이언트가 증가하더라도 브로커가 메세지를 중계함으로써 시스템의 부하를 줄일 수 있습니다. <br/>
또한 메세지 브로커를 사용해 비동기 통신을 가능하게 하고 클라이언트는 메세지를 보내고 다른 작업을 수행할 수 있습니다.  

### STOMP 프로토콜 메세지 규칙

- 연결
```
CONNECT
accept-version:1.0,1.1,2.0
host:localhost:8080/gs-guide-websocket

^@
```

- **Subscribe**

- 실시간 채팅 구독
```
SUBSCRIBE
destination:/sub/chat/{roomId}

^@
```

- 응답 포멧

이때 createDate 형식은 `yyyy-mm-ddThh:mm` 
```json
{
  "roomId": 1,
  "content": "호식이 두마리 치킨 크크크 치킨은 최애!",
  "createDate": "2023-12-12 20:00",
  "customer": {
    "id" : 2,
    "name": "효승이"
  }
}
```

- 채팅방 생성 알림
```
SUBSCRIBE
destination:/notification/room/{user-id}

^@
```

- 응답 포멧

```json
{
  "roomId": 1,
  "roomName" : "찬솔이와 두근두근 데이또",
  "firstChat" : "효승이 자니...?",
  "customer": {
    "id" : 2,
    "name": "효승이"
  },
  "maker" : {
    "id" : 1,
    "name" : "오찬솔"
  }
}
```

- **Publish**

- 실시간 채팅 메세지 보내기
```
SEND
destination:/pub/chat/{roomId}

{
  "roomId": 1,
  "content": "호식이 두마리 치킨 크크크 치킨은 회애!",
  "customer": {
    "id" : 2,
    "name": "효승이"
  }
}

^@
```

### HTTP(S) API 규칙

- **채팅방 생성**

POST `/chat-room`

- 참여 인원이 2명 이하일 때
    - 기본 : 대화 상대 이름이 방 이름
    - 개인 설정 : 개인 화면에 적용
- 참여 인원이 3명 이상일 때
    - 기본 : 대화 참여자 이름 나열, 단 10글자 이후론 `...` 으로 표현
    - 개인 설정 : 개인 화면에 적용

아래 예시에서 효승이 화면에서 채팅방 이름은`오찬솔`이라고 뜰 것이다.
오찬솔 화면에서는 `효승이와 두근두근 데이또`라고 뜰 것이다.

```json
{
  "participants": [
    {
      "id": 1,
      "name": "오찬솔"
    },
    {
      "id" : 2,
      "name": "효승이"
    }
  ],
  "roomName" : "효승이와 두근두근 데이또",
  "firstChat" : "효승이 자니...?",
  "maker" : {
    "id" : 1,
    "name" : "오찬솔"
  }
}

```



### Contributor

- **Name:** Chansol Oh
- **Email:** [oohsol@naver.com](mailto:oohsol@naver.com)
- **GitHub Profile:** [haxr369](https://github.com/haxr369?tab=followers)