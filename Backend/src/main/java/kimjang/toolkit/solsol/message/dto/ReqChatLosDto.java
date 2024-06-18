package kimjang.toolkit.solsol.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReqChatLosDto {
    private Long roomId;
    private Long page; // 채팅은 최신순 정렬
}
