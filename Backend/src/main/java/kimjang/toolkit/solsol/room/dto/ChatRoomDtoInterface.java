package kimjang.toolkit.solsol.room.dto;

import java.time.LocalDateTime;

public class ChatRoomDtoInterface {
    Long getRoomId;
    String getRoomName;
    LocalDateTime getLastChatTime;
    String getLastContent;
    Integer getMemberCnt;
}
