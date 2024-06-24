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

    /**
     * 채팅방 나간 시간 이전, 이후에 생성된 채팅을 불러오는 API
     * timeLine이 past라면 채팅방 나간 시간 이전에 생성된 채팅
     * timeLine이 recent라면 채팅방 나간 시간 이후에 생성된 채팅
     * @param roomId
     * @param roomExitTime
     * @param page
     * @param size
     * @param timeLine
     * @return
     */
    @GetMapping("/chat-room/chat/{roomId}")
    public ResponseEntity<PastChatsDto> getChatLogs(@PathVariable Long roomId,
                                                    @RequestParam(defaultValue = "0001-01-01 00:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime roomExitTime,
                                                    @RequestParam(defaultValue = "0") Integer page,
                                                    @RequestParam(defaultValue = "100") Integer size,
                                                    @RequestParam(defaultValue = "recent") String timeLine){
        ReqPastChatsDto reqPastChatsDto = new ReqPastChatsDto(roomId, page, size, roomExitTime);
        PastChatsDto pastChatsDto = chatService.getPastChats(reqPastChatsDto, timeLine);
        return ResponseEntity.ok(pastChatsDto);
    }

}
