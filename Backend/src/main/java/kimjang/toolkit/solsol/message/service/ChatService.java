package kimjang.toolkit.solsol.message.service;

import kimjang.toolkit.solsol.user.User;
import kimjang.toolkit.solsol.user.CustomerRepository;
import kimjang.toolkit.solsol.message.ChatMessage;
import kimjang.toolkit.solsol.message.dto.PastChatsDto;
import kimjang.toolkit.solsol.message.dto.ReqPastChatsDto;
import kimjang.toolkit.solsol.message.dto.SendChatMessageDto;
import kimjang.toolkit.solsol.message.repository.ChatRepository;
import kimjang.toolkit.solsol.room.entity.ChatRoom;
import kimjang.toolkit.solsol.room.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final CustomerRepository customerRepository;

    public void saveChat(SendChatMessageDto message) {
        User user = customerRepository.findById(message.getSender().getId())
                .orElseThrow();
        ChatRoom room = chatRoomRepository.findById(message.getRoomId())
                .orElseThrow();
        ChatMessage chatMessage = ChatMessage.toEntity(message, room, user);
        chatRepository.save(chatMessage);
    }

    @Transactional(readOnly = true)
    public PastChatsDto getPastChats(ReqPastChatsDto reqPastChatsDto, String timeLine) {
        if(timeLine.equals("recent")){
            Slice<SendChatMessageDto> pastChats =
                    chatRepository.findRecentChats(reqPastChatsDto.getRoomId(),
                            reqPastChatsDto.getRoomExitTime(),
                            reqPastChatsDto.getPage());
            return PastChatsDto.of(reqPastChatsDto, pastChats);
        } else{
            Slice<SendChatMessageDto> pastChats =
                    chatRepository.findPastChats(reqPastChatsDto.getRoomId(),
                            reqPastChatsDto.getRoomExitTime(),
                            reqPastChatsDto.getPage());
            return PastChatsDto.of(reqPastChatsDto, pastChats);
        }
    }
}
