package kimjang.toolkit.solsol.message.controllor;

import kimjang.toolkit.solsol.message.dto.PastChatsDto;
import kimjang.toolkit.solsol.message.dto.ReqPastChatsDto;
import kimjang.toolkit.solsol.message.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/chat-room/chat/{roomId}")
    public ResponseEntity<PastChatsDto> getChatLogs(@PathVariable Long roomId,
                                                    @RequestParam Integer page,
                                                    @RequestParam Integer size){
        ReqPastChatsDto reqPastChatsDto = new ReqPastChatsDto(roomId, page, size);
        PastChatsDto pastChatsDto = chatService.getPastChats(reqPastChatsDto);
        return ResponseEntity.ok(pastChatsDto);
    }

}
