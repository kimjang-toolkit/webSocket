package kimjang.toolkit.solsol.message.controllor;

import kimjang.toolkit.solsol.message.dto.ChatLogDto;
import kimjang.toolkit.solsol.message.dto.ReqChatLosDto;
import kimjang.toolkit.solsol.message.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

//    public List<ChatLogDto> getChatLogs(ReqChatLosDto reqChatLosDto){
//
//    }

}
