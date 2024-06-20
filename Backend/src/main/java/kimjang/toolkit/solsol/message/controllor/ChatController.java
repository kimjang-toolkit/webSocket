package kimjang.toolkit.solsol.message.controllor;

import kimjang.toolkit.solsol.message.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

//    public List<ChatLogDto> getChatLogs(ReqChatLosDto reqChatLosDto){
//
//    }

}
