package kimjang.toolkit.solsol.message.dto;

import kimjang.toolkit.solsol.customer.dto.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReqPastChatsDto {
    private Long roomId;
    private int page; // 현재 페이지 번호
    private int size; // 페이지에 채팅 개수
}
