package kimjang.toolkit.solsol.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatLogDto {
    private Long roomId;
    private String roomName;
    private List<SendChatMessageDto> messages;
    private Long page; // 채팅은 최신순 정렬
}
