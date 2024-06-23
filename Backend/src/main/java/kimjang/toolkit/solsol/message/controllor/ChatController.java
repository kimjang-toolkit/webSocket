package kimjang.toolkit.solsol.message.controllor;

import kimjang.toolkit.solsol.message.dto.PastChatsDto;
import kimjang.toolkit.solsol.message.dto.ReqPastChatsDto;
import kimjang.toolkit.solsol.message.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/chat-room/chat/{roomId}")
    public ResponseEntity<PastChatsDto> getChatLogs(@PathVariable Long roomId,
                                                    @RequestParam("roomExitTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime roomExitTime,
                                                    @RequestParam Integer page,
                                                    @RequestParam(defaultValue = "100") Integer size){
        ReqPastChatsDto reqPastChatsDto = new ReqPastChatsDto(roomId, page, size, roomExitTime);
        PastChatsDto pastChatsDto = chatService.getPastChats(reqPastChatsDto);
        return ResponseEntity.ok(pastChatsDto);
    }

}
