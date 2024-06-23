package kimjang.toolkit.solsol.room.repository;

import kimjang.toolkit.solsol.room.dto.ChatRoomDto;
import kimjang.toolkit.solsol.room.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query(value = "Select new kimjang.toolkit.solsol.room.dto.ChatRoomDto(" +
            " cr.id, ccr.roomName, " +
            "(Select cm.createDate, cm.content From ChatMessage cm " + // 채팅방의 가장 최근 채팅 1개만 가져오기
            " WHERE cm.chatRoom.id = cr.id " +
            " ORDER BY cm.createDate DESC LIMIT 1), " +
            "ccr.memberCnt ) FROM ChatRoom cr " +
            " join ChatRoomCustomerRelationship ccr " + // 채팅방 - 유저 관계 join
            " on cr.id = ccr.chatRoom.id AND ccr.user.id = :userId ")
    List<ChatRoomDto> findByUserId(@Param("userId") Long userId);
}
