package kimjang.toolkit.solsol.message.room.service;

import kimjang.toolkit.solsol.customer.Customer;
import kimjang.toolkit.solsol.customer.CustomerRepository;
import kimjang.toolkit.solsol.customer.dto.CustomerDto;
import kimjang.toolkit.solsol.message.ChatMessage;
import kimjang.toolkit.solsol.message.repository.ChatRepository;
import kimjang.toolkit.solsol.message.room.ChatRoom;
import kimjang.toolkit.solsol.message.room.ChatRoomCustomerRelationship;
import kimjang.toolkit.solsol.message.room.dto.CreateChatRoomDto;
import kimjang.toolkit.solsol.message.room.dto.CreateRoomReqDto;
import kimjang.toolkit.solsol.message.room.repository.ChatRoomCustormerRelationshipRepository;
import kimjang.toolkit.solsol.message.room.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final SimpMessagingTemplate messagingTemplate;
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
                        .roomName(createRoomName(createChatRoomDto, customer.getId()))
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

//    @Transactional
//    public Long createChatRoomAndFirstChat(CreateChatRoomDto createChatRoomDto){
//        // 생성한 채팅방 저장
//        // 유저와 유저-채팅방 그리고 채팅방을 연관지어서 저장
//        List<Long> customerIds = createChatRoomDto.getParticipants()
//                .stream()
//                .map(CustomerDto::getId)
//                .toList();
//
//        List<Customer> customers = customerRepository.findByIdIn(customerIds); // select 1회
//        if(customers.size() != createChatRoomDto.getParticipants().size()){
//            throw new RuntimeException("존재하지 않는 유저에게 채팅방을 초대했습니다.");
//        }
//        List<Customer> maker = customers.stream().filter(c -> c.getId().equals(createChatRoomDto.getMaker().getId())).toList();
//        if(maker.isEmpty()){
//            throw new RuntimeException("존재하지 않는 유저가 채팅방을 초대했습니다.");
//        }
//        // 룸 만들기
//        ChatRoom createdRoom = ChatRoom.builder().createDate(LocalDateTime.now(ZoneId.of("Asia/Seoul"))).build();
//        ChatRoom finalCreatedRoom = createdRoom;
//        List<ChatRoomCustomerRelationship> relationships = customers.stream()
//                .map(customer -> {
//                    return ChatRoomCustomerRelationship.builder()
//                            .chatRoom(finalCreatedRoom)
//                            .customer(customer)
//                            .roomName(createRoomName(createChatRoomDto, customer.getId())).build();
//
//                }).toList();
//        if(relationships.size() != createChatRoomDto.getParticipants().size()){
//            throw new RuntimeException("모든 사람들에 대한 채팅방 관계를 생성하지 않았습니다.");
//        }
//        try{
//            relationshipRepository.saveAll(relationships); // insert N회
//            createdRoom = chatRoomRepository.save(createdRoom); // insert 1회
//            chatRepository.save(ChatMessage.toEntity(
//                    createChatRoomDto.getFirstChat(),
//                    createdRoom,
//                    maker.get(0)));
//        } catch (RuntimeException e){
//            log.error(e.getMessage());
//            throw new RuntimeException("채팅방 생성하지 못했습니다.");
//        }
//        return createdRoom.getId();
//    }

    public void inviteParticipates(CreateChatRoomDto createChatRoomDto, Long roomId){

        createChatRoomDto.getParticipants()
                .parallelStream().forEach(customerDto -> {
                    CreateRoomReqDto createRoomReqDto = CreateRoomReqDto.builder()
                            .roomId(roomId)
                            .roomName(createRoomName(createChatRoomDto, customerDto.getId()))
                            .firstChat(createChatRoomDto.getFirstChat())
                            .customer(customerDto)
                            .build();
            messagingTemplate.convertAndSend("/notification/room/" + customerDto.getId(), // 각 고객에게 채팅방 생성을 알림
                    createRoomReqDto);
        });
    }

    public String createRoomName(CreateChatRoomDto createChatRoomDto, Long customerId){
        List<CustomerDto> participants = createChatRoomDto.getParticipants();
        // 3명 이상일 경우 본인을 제외한 나머지 사람들의 이름 나열, 단 10글자 이후론 ...으로 처리
        if(participants.size() >= 3 && createChatRoomDto.getRoomName() != null && !createChatRoomDto.getRoomName().isEmpty()){
            // 채팅방 만든 사람이 방 이름을 설정할 수 있다.
            return createChatRoomDto.getRoomName();
        } else{ // 상대 이름
            StringBuilder sb = new StringBuilder();
            for(CustomerDto c : participants){
                if(!c.getId().equals(customerId)){
                    sb.append(c.getName()).append(" ");
                }
                if(sb.length() > 10){
                    sb = new StringBuilder(sb.substring(0, 10));
                    sb.append("...");
                    return sb.toString();
                }
            }
            sb = new StringBuilder(sb.substring(0, sb.length()-1));
            return sb.toString();
        }

    }
}
