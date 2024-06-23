package kimjang.toolkit.solsol.message.repository;

import kimjang.toolkit.solsol.message.ChatMessage;
import kimjang.toolkit.solsol.message.dto.GetChatProj;
import kimjang.toolkit.solsol.message.dto.SendChatMessageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatRoom_Id(Long id);

//    Slice<GetChatProj> findByChatRoom_Id(Long id, Pageable pageable);

    /**
     * 채팅 방 퇴장 시간 이후에 발생한 모든 채팅 불러오기
     *
     * @param roomId
     * @Param roomExitTime : 사용자가 최근 방에서 나간 시간
     * @param pageable
     * @return
     */
    @Query(value="SELECT " +
            " new kimjang.toolkit.solsol.message.dto.SendChatMessageDto(" +
            " cr.id, cm.content, cm.createDate, u.id, u.name )" +
            " FROM ChatMessage cm join ChatRoom cr ON cr.id = :roomId" +
            " join User u ON u.id = cm.user.id" +
            " WHERE cm.createDate >= :roomExitTime" +
            " ORDER BY cm.createDate ASC")
    Slice<SendChatMessageDto> findPastChats(@Param("roomId") Long roomId, @Param("roomExitTime") LocalDateTime roomExitTime, Pageable pageable);
}
