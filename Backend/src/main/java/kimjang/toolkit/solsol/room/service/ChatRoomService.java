package kimjang.toolkit.solsol.room.service;

import kimjang.toolkit.solsol.customer.User;
import kimjang.toolkit.solsol.customer.CustomerRepository;
import kimjang.toolkit.solsol.customer.dto.UserDto;
import kimjang.toolkit.solsol.message.ChatMessage;
import kimjang.toolkit.solsol.message.repository.ChatRepository;
import kimjang.toolkit.solsol.room.dto.ChatRoomDto;
import kimjang.toolkit.solsol.room.dto.LastChatDto;
import kimjang.toolkit.solsol.room.entity.ChatRoom;
import kimjang.toolkit.solsol.room.entity.ChatRoomCustomerRelationship;
import kimjang.toolkit.solsol.room.dto.CreateChatRoomDto;
import kimjang.toolkit.solsol.room.repository.ChatRoomCustormerRelationshipRepository;
import kimjang.toolkit.solsol.room.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static kimjang.toolkit.solsol.room.service.CreateRoomName.withParticipationsName;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {


    private final CustomerRepository customerRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final ChatRoomCustormerRelationshipRepository relationshipRepository;



    @Transactional
    public Long createChatRoomAndFirstChat(CreateChatRoomDto createChatRoomDto) {
        List<User> users = fetchCustomers(createChatRoomDto.getParticipants()); // 채팅방 참여자들 불러오기
        User maker = validateParticipants(users, createChatRoomDto);

        ChatRoom createdRoom = createChatRoom(users.size());
        saveRelationships(createdRoom, users, createChatRoomDto);
        saveFirstChat(createChatRoomDto.getFirstChat(), maker, createdRoom);
        return createdRoom.getId();
    }

    @Transactional
    public void saveFirstChat(String content, User maker, ChatRoom createdRoom) {

        chatRepository.save(ChatMessage.builder()
                        .createDate(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                        .chatRoom(createdRoom)
                        .user(maker)
                        .content(content)
                .build());
    }

    @Transactional
    public ChatRoom createChatRoom(int memberCnt) {
        try {
            return chatRoomRepository.save(ChatRoom.of(memberCnt));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new RuntimeException("채팅방 생성하지 못했습니다.");
        }
    }

    @Transactional
    public void saveRelationships(ChatRoom chatRoom, List<User> users, CreateChatRoomDto createChatRoomDto) {
        List<ChatRoomCustomerRelationship> relationships = users.stream()
                .map(customer -> ChatRoomCustomerRelationship.builder()
                        .chatRoom(chatRoom)
                        .user(customer)
                        .roomName(withParticipationsName(createChatRoomDto, customer.getId()))
                        .build())
                .toList();
        try{
            relationshipRepository.saveAll(relationships);
        } catch (RuntimeException e){
            log.error(e.getMessage());
            throw new RuntimeException("사용자와 채팅방 사이 관계를 생성하지 못했습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<User> fetchCustomers(List<UserDto> participants) {
        List<Long> customerIds = participants.stream()
                .map(UserDto::getId)
                .toList();

        List<User> users = customerRepository.findByIdIn(customerIds);
        if (users.size() != participants.size()) {
            throw new RuntimeException("존재하지 않는 유저에게 채팅방을 초대했습니다.");
        }
        return users;
    }
    private User validateParticipants(List<User> users, CreateChatRoomDto createChatRoomDto) {
        Long makerId = createChatRoomDto.getMaker().getId();
        User maker= users.stream().filter(c -> c.getId().equals(makerId)).reduce((a, b) -> {
            throw new IllegalStateException("Multiple elements: " + a + ", " + b);
        }).orElseThrow();
        if(users.size() != createChatRoomDto.getParticipants().size()){
            throw new RuntimeException("존재하지 않는 유저에게 채팅방을 초대했습니다.");
        }
        return maker;
    }


    public List<ChatRoomDto> getChatRooms(Long userId) {
        return chatRoomRepository.findChatRoomsByUserId(userId);
//        List<LastChatDto> lastChatDtos = chatRepository.findLastChatsByUserId(userId);
    }
}
