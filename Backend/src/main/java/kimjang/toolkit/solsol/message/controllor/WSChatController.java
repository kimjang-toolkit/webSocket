package kimjang.toolkit.solsol.message.controllor;

import kimjang.toolkit.solsol.room.dto.CreateChatRoomDto;
import kimjang.toolkit.solsol.message.dto.SendChatMessageDto;
import kimjang.toolkit.solsol.room.service.ChatRoomService;
import kimjang.toolkit.solsol.room.service.ChatRoomStompService;
import kimjang.toolkit.solsol.message.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
   private final ChatRoomStompService chatRoomStompService;
   private final ChatService chatService;

   @MessageMapping("/chat/{roomId}") // /pub/chat 로 SendMessageDto를 전송
   @SendTo("/sub/chat/{roomId}") // /sub/chat 을 구독하면 SendMessageDto를 받음
   public SendChatMessageDto sendChatMessage(@DestinationVariable String roomId, @Payload SendChatMessageDto message){
      log.info("방 번호 : "+roomId+"  "+message.toString());
      // 채팅 방에 채팅 저장하기
      chatService.saveChat(message);
      // 저장한 채팅 분배하기
       return message;
   }

   /**
    * Create Chat room
    * and notify to Users that they are invited to chat room
    * @param dto
    * @return
    */
   @PostMapping("/chat-room")
   @SendTo("/notification/room/{user-id}") // /notification/room/chat 을 구독하면 SendMessageDto를 받음
   public DeferredResult<ResponseEntity<String>> createChatRoom(@RequestBody CreateChatRoomDto dto){
      Long roomId = chatRoomService.createChatRoomAndFirstChat(dto);
      DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<>();
      CompletableFuture.runAsync(() -> {
         chatRoomStompService.inviteParticipates(dto,roomId);
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
