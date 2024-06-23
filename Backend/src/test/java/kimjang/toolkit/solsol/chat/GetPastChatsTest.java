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

    @Test
    @DisplayName(value = "요청에 따라 채팅 메세지 불러오기")
    public void GET_Pase_Chat(){
        ReqPastChatsDto req = ReqPastChatsDto.builder()
                .roomId(1L)
                .roomExitTime(LocalDateTime.of(2023, 12, 12,12,12,12))
                .page(PageRequest.of(0, 100))
                .build();
        PastChatsDto responseDto = chatService.getPastChats(req);
        System.out.println("메세지 개수 : "+responseDto);
        for(int i=0; i<responseDto.getPastChats().size(); i++){
            System.out.println("메세지 : "+responseDto.getPastChats().get(i));
        }
    }
}
