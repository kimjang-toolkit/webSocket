package kimjang.toolkit.solsol.message.room.service;

import kimjang.toolkit.solsol.customer.dto.CustomerDto;
import kimjang.toolkit.solsol.message.room.dto.CreateChatRoomDto;
import kimjang.toolkit.solsol.message.room.dto.CreateRoomReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final SimpMessagingTemplate messagingTemplate;

    public void createChatRoom(CreateChatRoomDto dto) {
        CreateRoomReqDto createRoomReqDto = new CreateRoomReqDto();
        // 채팅 방 생성
        // 사람들 초대

    }

    public void inviteParticipates(CreateChatRoomDto createChatRoomDto){
        createChatRoomDto.getParticipants()
                .parallelStream().forEach(customerDto -> {
                    CreateRoomReqDto createRoomReqDto = CreateRoomReqDto.builder()
                            .roomId(createChatRoomDto.getRoomId())
                            .roomName(createRoomName(createChatRoomDto, customerDto))
                            .firstChat(createChatRoomDto.getFirstChat())
                            .customer(customerDto)
                            .build();
            messagingTemplate.convertAndSend("/notification/room/" + customerDto.getId(), // 각 고객에게 채팅방 생성을 알림
                    createRoomReqDto);
        });
    }

    public String createRoomName(CreateChatRoomDto createChatRoomDto, CustomerDto customer){
        List<CustomerDto> participants = createChatRoomDto.getParticipants();
        // 3명 이상일 경우 본인을 제외한 나머지 사람들의 이름 나열, 단 10글자 이후론 ...으로 처리
        if(participants.size() >= 3 && createChatRoomDto.getRoomName() != null && !createChatRoomDto.getRoomName().isEmpty()){
            // 채팅방 만든 사람이 방 이름을 설정할 수 있다.
            return createChatRoomDto.getRoomName();
        } else{ // 상대 이름
            StringBuilder sb = new StringBuilder();
            Long customerId =customer.getId();
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
