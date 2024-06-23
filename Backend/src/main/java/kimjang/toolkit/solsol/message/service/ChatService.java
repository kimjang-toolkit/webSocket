package kimjang.toolkit.solsol.message.service;

import kimjang.toolkit.solsol.customer.Customer;
import kimjang.toolkit.solsol.customer.CustomerRepository;
import kimjang.toolkit.solsol.message.ChatMessage;
import kimjang.toolkit.solsol.message.dto.PastChatsDto;
import kimjang.toolkit.solsol.message.dto.ReqPastChatsDto;
import kimjang.toolkit.solsol.message.dto.SendChatMessageDto;
import kimjang.toolkit.solsol.message.repository.ChatRepository;
import kimjang.toolkit.solsol.room.entity.ChatRoom;
import kimjang.toolkit.solsol.room.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final CustomerRepository customerRepository;

    public void saveChat(SendChatMessageDto message) {
        Customer customer = customerRepository.findById(message.getSender().getId())
                .orElseThrow();
        ChatRoom room = chatRoomRepository.findById(message.getRoomId())
                .orElseThrow();
        ChatMessage chatMessage = ChatMessage.toEntity(message, room, customer);
        chatRepository.save(chatMessage);
    }


    public PastChatsDto getPastChats(ReqPastChatsDto reqPastChatsDto) {
        List<SendChatMessageDto> pastChats = chatRepository.findPastChats(reqPastChatsDto.getRoomId(),
                reqPastChatsDto.getPageable());
        return PastChatsDto.builder()
                .roomId(reqPastChatsDto.getRoomId())
                .pastChats(pastChats)
                .page(reqPastChatsDto.getPageable().getPageNumber())
                .size(reqPastChatsDto.getPageable().getPageSize())
                .build();
    }
}
