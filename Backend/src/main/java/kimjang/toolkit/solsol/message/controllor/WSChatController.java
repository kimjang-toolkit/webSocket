package kimjang.toolkit.solsol.message.controllor;

import kimjang.toolkit.solsol.message.room.dto.CreateChatRoomDto;
import kimjang.toolkit.solsol.message.dto.SendChatMessageDto;
import kimjang.toolkit.solsol.message.room.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@RestController
public class WSChatController {

   private final ChatRoomService chatRoomService;

   @MessageMapping("/message") // /app/message 로 SendMessageDto를 전송
   @SendTo("/topic/chat") // /topic/chat 을 구독하면 SendMessageDto를 받음
   public SendChatMessageDto sendChatMessage(SendChatMessageDto message){
      log.info(message.toString());
       return message;
   }

   /**
    * Create Chat room
    * and notify to Users that they are invited to chat room
    * @param dto
    * @return
    */
   @PostMapping("/chat-room")
   @SendTo("/notification/{user-id}") // /topic/chat 을 구독하면 SendMessageDto를 받음
   public DeferredResult<ResponseEntity<String>> createChatRoom(@RequestBody CreateChatRoomDto dto){
      DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<>();
      CompletableFuture.runAsync(() -> {
         chatRoomService.createChatRoom(dto);
      }).handle((result, throwable) -> {
         if (throwable != null) {
            deferredResult.setErrorResult("Failed to create chat room and send notifications: " + throwable.getMessage());
         } else {
            deferredResult.setResult(ResponseEntity.ok("Chat room created and notifications sent"));
         }
         return null;
      });;
      return deferredResult;
   }
}
