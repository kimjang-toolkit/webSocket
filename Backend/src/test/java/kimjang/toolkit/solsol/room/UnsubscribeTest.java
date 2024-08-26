package kimjang.toolkit.solsol.room;

import kimjang.toolkit.solsol.message.dto.PastChatsDto;
import kimjang.toolkit.solsol.message.dto.ReqPastChatsDto;
import kimjang.toolkit.solsol.room.service.ChatRoomSubscribeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
public class UnsubscribeTest {
    @Autowired
    ChatRoomSubscribeService chatRoomSubscribeService;

    @Test
    @DisplayName(value = "채팅방 소켓 구독 종료 시간 작성 테스트")
    public void GET_Pase_Chat(){
        /**
         * 함수를 호출하면 exitTime이 현재 시간으로 업데이트 되어야한다.
         *
         */
    }
}
