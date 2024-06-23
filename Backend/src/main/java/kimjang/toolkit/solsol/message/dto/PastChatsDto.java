package kimjang.toolkit.solsol.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PastChatsDto {
    private Long roomId;
    private List<SendChatMessageDto> pastChats;
    private int page; // 현재 페이지 번호
    private int size; // 페이지에 채팅 개수

    public static PastChatsDto of(ReqPastChatsDto reqPastChatsDto, List<SendChatMessageDto> pastChats){
        return PastChatsDto.builder()
                .roomId(reqPastChatsDto.getRoomId())
                .pastChats(pastChats)
                .page(reqPastChatsDto.getPage().getPageNumber())
                .size(reqPastChatsDto.getPage().getPageSize())
                .build();
    }
}
