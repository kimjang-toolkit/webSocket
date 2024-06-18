package kimjang.toolkit.solsol.message.service;

import kimjang.toolkit.solsol.customer.Customer;
import kimjang.toolkit.solsol.customer.CustomerRepository;
import kimjang.toolkit.solsol.message.ChatMessage;
import kimjang.toolkit.solsol.message.dto.SendChatMessageDto;
import kimjang.toolkit.solsol.message.repository.ChatRepository;
import kimjang.toolkit.solsol.room.entity.ChatRoom;
import kimjang.toolkit.solsol.room.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final CustomerRepository customerRepository;

    public void saveChat(SendChatMessageDto message) {
        Customer customer = customerRepository.findById(message.getCustomer().getId())
                .orElseThrow();
        ChatRoom room = chatRoomRepository.findById(message.getRoomId())
                .orElseThrow();
        ChatMessage chatMessage = ChatMessage.toEntity(message, room, customer);
        chatRepository.save(chatMessage);
    }



}
