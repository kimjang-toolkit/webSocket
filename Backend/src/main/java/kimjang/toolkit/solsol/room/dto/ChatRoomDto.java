package kimjang.toolkit.solsol.room.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
    private Long roomId;
    private String roomName;
    private LocalDateTime lastChatTime;
    private String lastContent;
    private int memberCnt;
}
