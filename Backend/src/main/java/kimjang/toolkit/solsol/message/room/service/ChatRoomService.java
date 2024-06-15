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

    public void inviteParticipates(List<CustomerDto> customers, Long roomId){
        customers.parallelStream().forEach(customer -> {
            messagingTemplate.convertAndSend("/notification/room/" + customer.getId(),
                    new CreateRoomReqDto(customer, roomId));
        });
    }
}
