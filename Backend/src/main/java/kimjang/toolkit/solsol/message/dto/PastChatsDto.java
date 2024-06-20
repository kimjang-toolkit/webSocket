package kimjang.toolkit.solsol.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PastChatsDto {
    private Long roomId;
    private String roomName;
    private List<SendChatMessageDto> messages;
    private int page; // 현재 페이지 번호
    private int size; // 페이지에 채팅 개수
}
