package kimjang.toolkit.solsol.chat;


import kimjang.toolkit.solsol.message.dto.PastChatsDto;
import kimjang.toolkit.solsol.message.dto.ReqPastChatsDto;
import kimjang.toolkit.solsol.message.service.ChatService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class GetPastChatsTest {
    @Autowired
    private ChatService chatService;

    /**
     * 1은 채팅방 나간 이후 아무 것도 없기 때문에 past로 7개, recent로 1개
     * 2는 나간 이후 2개 입력됐기 때문에 past 3개 recent 5개
     * 3은 past 0개 recent 8개
     */

    @Test
    @DisplayName(value = "요청에 따라 채팅 메세지 불러오기")
    public void GET_Pase_Chat_1(){
        Authentication auth = new UsernamePasswordAuthenticationToken("solsol@naver.com", null,
                List.of(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(auth);

        ReqPastChatsDto req = ReqPastChatsDto.builder()
                .roomId(1L)
                .page(PageRequest.of(0, 100))
                .build();

        PastChatsDto responseRecentDto = chatService.getPastChats(req, "recent");
        Assertions.assertEquals(1, responseRecentDto.getPastChats().size());

        PastChatsDto responsePastDto = chatService.getPastChats(req, "past");
        Assertions.assertEquals(7, responsePastDto.getPastChats().size());
    }

    @Test
    @DisplayName(value = "요청에 따라 채팅 메세지 불러오기")
    public void GET_Pase_Chat_2(){
        Authentication auth = new UsernamePasswordAuthenticationToken("se@naver.com", null,
                List.of(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(auth);

        ReqPastChatsDto req = ReqPastChatsDto.builder()
                .roomId(1L)
                .page(PageRequest.of(0, 100))
                .build();
        PastChatsDto responseRecentDto = chatService.getPastChats(req, "recent");
        Assertions.assertEquals(5, responseRecentDto.getPastChats().size());

        PastChatsDto responsePastDto = chatService.getPastChats(req, "past");
        Assertions.assertEquals(3, responsePastDto.getPastChats().size());
    }

    @Test
    @DisplayName(value = "요청에 따라 채팅 메세지 불러오기")
    public void GET_Pase_Chat_3(){
        Authentication auth = new UsernamePasswordAuthenticationToken("gang@naver.com", null,
                List.of(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(auth);

        ReqPastChatsDto req = ReqPastChatsDto.builder()
                .roomId(1L)
                .page(PageRequest.of(0, 100))
                .build();
        PastChatsDto responseRecentDto = chatService.getPastChats(req, "recent");
        Assertions.assertEquals(8, responseRecentDto.getPastChats().size());

        PastChatsDto responsePastDto = chatService.getPastChats(req, "past");
        Assertions.assertEquals(0, responsePastDto.getPastChats().size());
    }
}
