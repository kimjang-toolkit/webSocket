package kimjang.toolkit.solsol.chat;


import kimjang.toolkit.solsol.message.dto.PastChatsDto;
import kimjang.toolkit.solsol.message.dto.ReqPastChatsDto;
import kimjang.toolkit.solsol.message.service.ChatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

@SpringBootTest
public class GetPastChatsTest {
    @Autowired
    private ChatService chatService;

    /**
     * 1은 채팅방 나간 이후 아무 것도 없기 때문에 past로 7개, recent로 0개
     * 2는 나간 이후 2개 입력됐기 때문에 past 5개 recent 2개
     * 3은 past 0개 recent 7개
     */

    @Test
    @DisplayName(value = "요청에 따라 채팅 메세지 불러오기")
    public void GET_Pase_Chat(){
        ReqPastChatsDto req = ReqPastChatsDto.builder()
                .roomId(1L)
                .page(PageRequest.of(0, 100))
                .build();
        String timeLine = "recent";
        PastChatsDto responseDto = chatService.getPastChats(req, timeLine);
        System.out.println("메세지 개수 : "+responseDto);
        for(int i=0; i<responseDto.getPastChats().size(); i++){
            System.out.println("메세지 : "+responseDto.getPastChats().get(i));
        }
    }
}
