package kimjang.toolkit.solsol.message.room;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@RequiredArgsConstructor
public class ChatRoom {
    private final Long id; // 방 번호
    private final String roomName; // 방 이름
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate; // 언제 만들어졌는지
}
