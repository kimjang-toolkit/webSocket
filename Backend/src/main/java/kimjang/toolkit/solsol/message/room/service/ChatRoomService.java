package kimjang.toolkit.solsol.message.room.service;

import kimjang.toolkit.solsol.customer.Customer;
import kimjang.toolkit.solsol.customer.CustomerRepository;
import kimjang.toolkit.solsol.customer.dto.CustomerDto;
import kimjang.toolkit.solsol.message.dto.GetChatProj;
import kimjang.toolkit.solsol.message.room.ChatRoom;
import kimjang.toolkit.solsol.message.room.ChatRoomCustomerRelationship;
import kimjang.toolkit.solsol.message.room.dto.CreateChatRoomDto;
import kimjang.toolkit.solsol.message.room.dto.CreateRoomReqDto;
import kimjang.toolkit.solsol.message.room.repository.ChatRoomCustormerRelationshipRepository;
import kimjang.toolkit.solsol.message.room.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final SimpMessagingTemplate messagingTemplate;
    private final CustomerRepository customerRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomCustormerRelationshipRepository relationshipRepository;

    @Transactional
    public Long createChatRoom(CreateChatRoomDto createChatRoomDto){
        // 생성한 채팅방 저장
        // 유저와 유저-채팅방 그리고 채팅방을 연관지어서 저장
        List<Long> customerIds = createChatRoomDto.getParticipants()
                .stream()
                .map(CustomerDto::getId)
                .toList();
        List<Customer> customers = customerRepository.findByIdIn(customerIds);
        // 룸 만들기
        ChatRoom createdRoom = ChatRoom.builder().createDate(LocalDateTime.now(ZoneId.of("Asia/Seoul"))).build();
        ChatRoom finalCreatedRoom = createdRoom;
        List<ChatRoomCustomerRelationship> relationships = customers.stream()
                .map(customer -> {
                    return ChatRoomCustomerRelationship.builder()
                            .chatRoom(finalCreatedRoom)
                            .customer(customer)
                            .roomName(createRoomName(createChatRoomDto, customer.getId())).build();

                }).toList();
        relationshipRepository.saveAll(relationships);
        createdRoom = chatRoomRepository.save(createdRoom);
        return createdRoom.getId();
//        inviteParticipates(createChatRoomDto, createdRoom.getId());
    }

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
