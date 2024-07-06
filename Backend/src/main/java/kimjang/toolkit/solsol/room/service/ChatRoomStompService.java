package kimjang.toolkit.solsol.room.service;

import kimjang.toolkit.solsol.room.dto.CreateChatRoomDto;
import kimjang.toolkit.solsol.room.dto.CreateRoomReqDto;
import kimjang.toolkit.solsol.room.dto.InviteChatRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static kimjang.toolkit.solsol.room.service.CreateRoomName.withParticipationsName;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomStompService {

    private final SimpMessagingTemplate messagingTemplate;

    public void inviteParticipates(InviteChatRoomDto inviteChatRoomDto) {
        String creatorEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        inviteChatRoomDto.getParticipants()
                .parallelStream()
                .forEach(customerDto -> {
                    // 각 참여자에게 채팅방 초대 메세지 전달
                    CreateRoomReqDto createRoomReqDto = CreateRoomReqDto.builder()
                            .roomId(inviteChatRoomDto.getRoomId())
                            .roomName(inviteChatRoomDto.getRoomName())
                            .user(customerDto)
                            .creator(creatorEmail)
                            .build();
                    messagingTemplate.convertAndSend("/notification/room/" + customerDto.getId(), // 각 고객에게 채팅방 생성을 알림
                            createRoomReqDto);
                });
    }
}
