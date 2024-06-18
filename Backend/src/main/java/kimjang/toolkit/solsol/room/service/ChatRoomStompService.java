package kimjang.toolkit.solsol.room.service;

import kimjang.toolkit.solsol.room.dto.CreateChatRoomDto;
import kimjang.toolkit.solsol.room.dto.CreateRoomReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static kimjang.toolkit.solsol.room.service.CreateRoomName.withParticipationsName;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomStompService {

    private final SimpMessagingTemplate messagingTemplate;

    public void inviteParticipates(CreateChatRoomDto createChatRoomDto, Long roomId) {

        createChatRoomDto.getParticipants()
                .parallelStream().forEach(customerDto -> {
                    CreateRoomReqDto createRoomReqDto = CreateRoomReqDto.builder()
                            .roomId(roomId)
                            .roomName(withParticipationsName(createChatRoomDto, customerDto.getId()))
                            .firstChat(createChatRoomDto.getFirstChat())
                            .customer(customerDto)
                            .build();
                    messagingTemplate.convertAndSend("/notification/room/" + customerDto.getId(), // 각 고객에게 채팅방 생성을 알림
                            createRoomReqDto);
                });
    }
}
