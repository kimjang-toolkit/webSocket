# webSoket
웹소켓을 적용한 채팅 구현

## 2024-06-13 채팅방 리스트 구현 방법 논의
### V1 필요한 데이터

- 첫 화면 들어오면 →  user info API 요청 → id값과 닉네임값이 담긴 데이터 넘겨주기
- 채팅방 1
    - 채팅 n : 시간 순으로 정렬된 채팅 메세지,
        - **user name**
        - **시간** → string 1640  → 16:40
            - yyyy-mm-dd : 화면에 표기할 때 사용
            - hh:mm 메시지 표기에 사용
            - 백→ 프: `yyyy-mm-dd hh:mm:ss` 형식으로 백이 메세지를 받은 시간 보냄
        - **메시지**
    - 경우1: 브라우저에 이제 들어옴 -> HTTP API로 초기화
        - 브라우저를 꺼놨을 동안
        - 사용자에게 온 채팅방의 가장 최근의 메세지를 보여줌 with 안읽은 메세지 수
    - 경우2: 채팅방리스트를 보고있는 상황 -> WebSocket으로 실시간 채팅방 현황 구독
        - 실시간으로 채팅이 오고있으면 그 채팅방에 카운터가 올라가는게 보여야함.
    - ChatRoom List에 들오면
        - HTTPs 요청으로 서버에서 채팅방리스트 가져오기 ( 최근 메시지, 안읽은 메시지 수, 시간 등)
        - 웹소켓 연결, 관련된 토픽을 구독해서 새로운 메시지를 받기
        - 프로트가 백에게 토픽을 구독해
            - 토픽은 사용자에게 온 메세지를 전달

### ChatRoom List에 들어갔을 때 HTTP API

```json
{
  "chatRooms": [
    {
      "roomId": "1",
      "partnerId": "user123",
      "latestMessage": "안녕하세요?",
      "latestMessageTime": "2024-06-13T14:30:00",
      "unreadMessages": 2,
      "roomName": "상대방 닉네임"
    },
    {
      "roomId": "2",
      "partnerId": "user456",
      "latestMessage": "오늘 저녁에 뭐 먹을까요?",
      "latestMessageTime": "2024-06-13T15:45:00",
      "unreadMessages": 0,
      "roomName": "상대방 닉네임"
    }
  ]
}
```


### Contributors

**Frontend Developer:**

- **Name:** Seunghyo Jo
- **Email:** [seunghyo7742@soongsil.ac.kr](mailto:seunghyo7742@soongsil.ac.kr)
- **GitHub Profile** [Joseunghyo7742](https://github.com/Joseunghyo7742)

**Backend Developer:**

- **Name:** Chansol Oh
- **Email:** [oohsol@naver.com](mailto:oohsol@naver.com)
- **GitHub Profile:** [haxr369](https://github.com/haxr369?tab=followers)
