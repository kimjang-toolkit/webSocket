package kimjang.toolkit.solsol.room.repository;

import kimjang.toolkit.solsol.room.dto.ChatRoomDto;
import kimjang.toolkit.solsol.room.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT new kimjang.toolkit.solsol.room.dto.ChatRoomDto(" +
            " cr.id, ccr.roomName, cr.memberCnt, cm.createDate, cm.content) " +
            "FROM ChatRoom cr " +
            "JOIN ChatRoomCustomerRelationship ccr " +
            "ON cr.id = ccr.chatRoom.id AND ccr.user.id = :userId" +
            " JOIN ChatMessage  cm " +
            " ON cm.chatRoom.id = cr.id " +
            " WHERE cm.createDate = (select MAX(ccm.createDate) from ChatMessage ccm" +
                    " Where ccm.chatRoom.id = cm.chatRoom.id) ")
    List<ChatRoomDto> findChatRoomsByUserId(@Param("userId") Long userId);
}
