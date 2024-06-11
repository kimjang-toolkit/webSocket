package kimjang.toolkit.solsol.room;

import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
public class ChatRoom {
    private final Long id; // 방 번호
    private final String roomName; // 방 이름
    private Date createDate; // 언제 만들어졌는지
}
