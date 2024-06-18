package kimjang.toolkit.solsol.room.service;

import kimjang.toolkit.solsol.customer.Customer;
import kimjang.toolkit.solsol.customer.CustomerRepository;
import kimjang.toolkit.solsol.customer.dto.CustomerDto;
import kimjang.toolkit.solsol.message.ChatMessage;
import kimjang.toolkit.solsol.message.repository.ChatRepository;
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
        List<Customer> customers = fetchCustomers(createChatRoomDto.getParticipants()); // 채팅방 참여자들 불러오기
        Customer maker = validateParticipants(customers, createChatRoomDto);

        ChatRoom createdRoom = createChatRoom();
        saveRelationships(createdRoom, customers, createChatRoomDto);
        saveFirstChat(createChatRoomDto.getFirstChat(), maker, createdRoom);
        return createdRoom.getId();
    }

    @Transactional
    public void saveFirstChat(String content, Customer maker, ChatRoom createdRoom) {

        chatRepository.save(ChatMessage.builder()
                        .createDate(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                        .chatRoom(createdRoom)
                        .customer(maker)
                        .content(content)
                .build());
    }

    @Transactional
    public ChatRoom createChatRoom() {
        try {
            return chatRoomRepository.save(ChatRoom.of());
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new RuntimeException("채팅방 생성하지 못했습니다.");
        }
    }

    @Transactional
    public void saveRelationships(ChatRoom chatRoom, List<Customer> customers, CreateChatRoomDto createChatRoomDto) {
        List<ChatRoomCustomerRelationship> relationships = customers.stream()
                .map(customer -> ChatRoomCustomerRelationship.builder()
                        .chatRoom(chatRoom)
                        .customer(customer)
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
    public List<Customer> fetchCustomers(List<CustomerDto> participants) {
        List<Long> customerIds = participants.stream()
                .map(CustomerDto::getId)
                .toList();

        List<Customer> customers = customerRepository.findByIdIn(customerIds);
        if (customers.size() != participants.size()) {
            throw new RuntimeException("존재하지 않는 유저에게 채팅방을 초대했습니다.");
        }
        return customers;
    }
    private Customer validateParticipants(List<Customer> customers, CreateChatRoomDto createChatRoomDto) {
        Long makerId = createChatRoomDto.getMaker().getId();
        Customer maker= customers.stream().filter(c -> c.getId().equals(makerId)).reduce((a, b) -> {
            throw new IllegalStateException("Multiple elements: " + a + ", " + b);
        }).orElseThrow();
        if(customers.size() != createChatRoomDto.getParticipants().size()){
            throw new RuntimeException("존재하지 않는 유저에게 채팅방을 초대했습니다.");
        }
        return maker;
    }



}
