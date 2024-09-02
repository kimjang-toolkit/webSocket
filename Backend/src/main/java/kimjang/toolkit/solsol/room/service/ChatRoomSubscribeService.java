package kimjang.toolkit.solsol.room.service;

import kimjang.toolkit.solsol.room.entity.ChatRoomCustomerRelationship;
import kimjang.toolkit.solsol.room.repository.ChatRoomCustomerRelationshipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomSubscribeService {
    private final ChatRoomCustomerRelationshipRepository relationshipRepository;

    @Transactional
    public void updateUnsubscribeTime(String email, Long roomId){
        ChatRoomCustomerRelationship relationship = relationshipRepository.findByChatRoom_IdAndUser_Email(roomId, email)
                .orElseThrow(() -> new IllegalStateException(roomId+"는 "+email+"이 구독하고 있지 않는 채팅방입니다."));
        relationship.unsubscribeRoom();
        log.info("email : {} 가 roomId : {} 를 구독 취소",email,roomId);
    }
}
