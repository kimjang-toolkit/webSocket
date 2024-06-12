package kimjang.toolkit.solsol.message.controllor;

import kimjang.toolkit.solsol.message.dto.SendChatMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class messageController {

   @MessageMapping("/message") // /app/message 로 SendMessageDto를 전송
   @SendTo("/topic/chat") // /topic/chat 을 구독하면 SendMessageDto를 받음
   public SendChatMessageDto sendChatMessage(SendChatMessageDto message){
      log.info(message.toString());
       return message;
   }
}
