package kimjang.toolkit.solsol.room.repository;

import kimjang.toolkit.solsol.room.dto.ChatRoomDto;
import kimjang.toolkit.solsol.room.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {


    @Query("SELECT new kimjang.toolkit.solsol.room.dto.ChatRoomDto(" +
            " cr.id, ccr.roomName, cr.memberCnt, ccr.roomExitTime, cm.content, " +
            " (SELECT COUNT(cm2.id) FROM ChatMessage cm2 " + // 읽지 않은 매세지 개수
                        // 채팅방 id가 같은 메세지만 가져옴, roomExitTime 이후에 생성된 메세지만 가져옴
            "   WHERE cm2.chatRoom.id = cr.id AND cm2.createDate > ccr.roomExitTime)" +
            ") " +
            "FROM ChatRoom cr " + // 채팅방
            "JOIN ChatRoomCustomerRelationship ccr " + // 채팅방-유저 연관 테이블
            // 파라미터로 주어진 유저 아이디에 맞는 채팅방 연관 관계를 가져오고 , 해당 채팅방도 가져온다.
            "   ON cr.id = ccr.chatRoom.id AND ccr.user.id = :userId " +
            // 최근 채팅을 가져오기 위해 join
            "JOIN ChatMessage cm " +
            "   ON cm.chatRoom.id = cr.id  " +
            "WHERE cm.createDate = (SELECT MAX(ccm.createDate) FROM ChatMessage ccm " +
            "                       WHERE ccm.chatRoom.id = cm.chatRoom.id)")
    List<ChatRoomDto> findChatRoomsByUserId(@Param("userId") Long userId);
}
