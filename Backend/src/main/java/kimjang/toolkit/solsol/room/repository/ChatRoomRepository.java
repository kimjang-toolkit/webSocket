package kimjang.toolkit.solsol.room.repository;

import kimjang.toolkit.solsol.room.dto.ChatRoomDto;
import kimjang.toolkit.solsol.room.dto.ChatRoomDtoInterface;
import kimjang.toolkit.solsol.room.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

//    @Query(value = "SELECT " +
//            " cr.room_id, ccr.room_name ," +
//            " (SELECT cm.create_date, cm.content " + // 각 채팅방의 최근 채팅 1개 가져오기
//            "  FROM chat_message cm " +
//            "  WHERE cm.chat_room = cr.room_id " +
//            "  ORDER BY cm.create_date DESC " +
//            "  LIMIT 1), " +
//            " cr.room_id " +
//            "FROM chat_room cr " +
//            "JOIN chat_room_customer_relationship ccr " +
//            "ON cr.room_id = ccr.chat_room_id " +
//            "WHERE ccr.customer_id = :userId", nativeQuery = true)
//    Collection<ChatRoomDtoInterface> findChatRoomsByUserId(@Param("userId") Long userId);
}
